package xyz.vivekc.popularmovies.ui.detailsscreen.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.DetailsFragmentBinding;
import xyz.vivekc.popularmovies.ui.detailsscreen.viewmodel.DetailsViewModel;

public class DetailsFragment extends Fragment {

    private DetailsViewModel viewModel;
    DetailsFragmentBinding binding;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        binding.setDetailsViewModel(viewModel);
    }
}
