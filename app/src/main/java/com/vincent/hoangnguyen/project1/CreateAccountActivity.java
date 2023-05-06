package com.vincent.hoangnguyen.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    EditText email_edt, password_edt, confirmPassword_edt,phoneNumber_edt;
    Button createAccount_btn;
    CountryCodePicker countryCodePicker;
    ProgressBar progressBar;
    TextView resendTv_btn;
    CountDownTimer mCountDownResendOTP;
    String verificationCode;

    PhoneAuthProvider.ForceResendingToken resendingToken;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        // ánh xạ đến các edittext
        email_edt = findViewById(R.id.create_email_edt);
        password_edt = findViewById(R.id.create_password_edt);
        confirmPassword_edt = findViewById(R.id.create_confirmPassword_edt);
        createAccount_btn = findViewById(R.id.create_button);
        phoneNumber_edt = findViewById(R.id.create_phoneNumber_edt);
        progressBar = findViewById(R.id.create_progressBar);
        countryCodePicker = findViewById(R.id.countryCode);
        countryCodePicker.registerCarrierNumberEditText(phoneNumber_edt);

        // set onclick cho nút tạo tài khoản
        createAccount_btn.setOnClickListener(v -> {
            // lấy thông tin tài khoản do người dùng nhập
            String email = email_edt.getText().toString().trim();
            String password = password_edt.getText().toString().trim();
            String confirmPassword = confirmPassword_edt.getText().toString().trim();

            // xác thực thông tin người dùng
            if(!countryCodePicker.isValidFullNumber()){
                phoneNumber_edt.setError("Hãy xem xét lại số điện thoại của bạn");
                return;
            }
            String phoneNumber = countryCodePicker.getFullNumberWithPlus();
            Log.d("PhoneNumber",phoneNumber);
            boolean isValidated = validateData(email,password,confirmPassword,phoneNumber);
            if(!isValidated){
                return;
            }
            createAccountInFireBase(email,password,phoneNumber);
        });

    }

    private void createAccountInFireBase(String email, String password, String phoneNumber) {
        changInProgress(true);


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changInProgress(false);
                if(task.isSuccessful()){
                    Utility.showToast(CreateAccountActivity.this, "Tạo tài khoản thành công!");
                    firebaseAuth.signOut();  // signOut account  người dùng phải verify email mới đăng nhập lại đc
                    sendOtp(phoneNumber,false);

                }
                else {
                    Utility.showToast(CreateAccountActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage());
                }
            }
        });


    }

    void sendOtp(String phoneNumber, boolean isResend){
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(CreateAccountActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                verifyOTP();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Utility.showToast(getApplicationContext(),e.getLocalizedMessage());
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode  = s;
                                Log.d("OTP",s);
                                resendingToken = forceResendingToken;
                                Utility.showToast(getApplicationContext(),"Gửi OTP thành công!");
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    private void verifyOTP() {
        Dialog dialog = new Dialog(CreateAccountActivity.this);
        dialog.setContentView(R.layout.dialog_smscode);
        dialog.setCancelable(false); // ngăn việc người dùng ấn ra khỏi dialog và thoát dialog
        resendTv_btn = dialog.findViewById(R.id.smsDialog_resend_otp);
        // khai báo CountDownTimer để có thể đếm ngược thời gian
        mCountDownResendOTP = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendTv_btn.setText("Gửi lại OTP sau "+ millisUntilFinished / 1000 + "s" );
            }
            @Override
            public void onFinish() {
                resendTv_btn.setText("Gửi lại OTP");
                resendTv_btn.setTypeface(Typeface.DEFAULT_BOLD);
                resendTv_btn.setEnabled(true);
            }
        };
        // bắt đầu đếm ngược
        mCountDownResendOTP.start();
        // set onclick resend lại otp
        resendTv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendTv_btn.setEnabled(false);
                Utility.showToast(CreateAccountActivity.this,"hello");
            }
        });
        Button ok = dialog.findViewById(R.id.smsDialog_ok_btn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Otp_edt = dialog.findViewById(R.id.OtpCode_edt);
                String OTP = Otp_edt.getText().toString().trim();
                Log.d("TEST_ByHoang",verificationCode);
                Log.d("TEST_ByHoang",OTP);
                if(OTP.equals(verificationCode)){
                    Utility.showToast(getApplicationContext(),"Tài khoản đã được xác thực giờ bạn có thể đăng nhập");
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validateData(String emai, String password, String confirmPass,String phoneNumber){
        if( !Patterns.EMAIL_ADDRESS.matcher(emai).matches()){
                email_edt.setError("Cần nhập đúng email của bạn");
                return false;
            }
        if(password.length() <6){
            password_edt.setError("Mật khẩu cần tối thiểu 6 kí tự");
            return false;
        }
        if(!confirmPass.equals(password)){
            confirmPassword_edt.setError("Mật khẩu không khớp nhau");
            return false;
        }
       /* if(phoneNumber.length() <9 ){
            phoneNumber_edt.setError("Hãy xem xét lại số điện thoại của bạn");
            return false;
        }*/
            return true;
    }


    private void changInProgress(boolean inProgress){
        if(inProgress){
            createAccount_btn.setVisibility(View.GONE);  // đang trong tiến trình tạo account thì button biến mất
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
            createAccount_btn.setVisibility(View.VISIBLE);
        }
    }
    public void startLogin(View view) {
        startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
    }
}
