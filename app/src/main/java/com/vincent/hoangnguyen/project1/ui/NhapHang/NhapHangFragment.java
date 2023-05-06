package com.vincent.hoangnguyen.project1.ui.NhapHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vincent.hoangnguyen.project1.R;
import com.vincent.hoangnguyen.project1.databinding.FragmentBanhangBinding;
import com.vincent.hoangnguyen.project1.databinding.FragmentNhaphangBinding;

public class NhapHangFragment extends Fragment {
    private FragmentNhaphangBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NhapHangViewModel nhapHangViewModel =new ViewModelProvider(this).get(NhapHangViewModel.class);
        binding = FragmentNhaphangBinding.inflate(inflater, container,false);
        View root =binding.getRoot();

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
