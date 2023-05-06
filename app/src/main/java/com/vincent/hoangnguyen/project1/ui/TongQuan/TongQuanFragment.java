package com.vincent.hoangnguyen.project1.ui.TongQuan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vincent.hoangnguyen.project1.databinding.FragmentTongquanBinding;

public class TongQuanFragment extends Fragment {

private FragmentTongquanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        TongQuanViewModel tongQuanViewModel =
                new ViewModelProvider(this).get(TongQuanViewModel.class);

    binding = FragmentTongquanBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}