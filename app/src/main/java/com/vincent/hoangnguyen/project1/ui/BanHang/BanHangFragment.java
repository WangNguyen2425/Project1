package com.vincent.hoangnguyen.project1.ui.BanHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.vincent.hoangnguyen.project1.databinding.FragmentBanhangBinding;

public class BanHangFragment extends Fragment {

private FragmentBanhangBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        BanHangViewModel banHangViewModel =
                new ViewModelProvider(this).get(BanHangViewModel.class);

    binding = FragmentBanhangBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        banHangViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}