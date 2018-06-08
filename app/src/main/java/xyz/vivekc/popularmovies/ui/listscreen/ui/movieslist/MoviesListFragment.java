package xyz.vivekc.popularmovies.ui.listscreen.ui.movieslist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.MoviesListFragmentBinding;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;


public class MoviesListFragment extends Fragment {

    private MoviesListViewModel mViewModel;
    private MoviesListFragmentBinding binding;
    private MovieListingAdapter adapter;


    public enum CurrentMode {
        POPULAR, TOPRATED, FAVOURITES
    }

    CurrentMode mode = CurrentMode.POPULAR;

    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        binding.setViewModel(mViewModel);

        setUpAdapter();
        fetchData();
        setupFilter();
    }

    private void setUpAdapter() {
        binding.moviesListingGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieListingAdapter(getActivity());
        binding.moviesListingGrid.setAdapter(adapter);
    }

    private void fetchData() {
        LiveData<ApiResponse<ListResponse>> movies = null;

        switch (mode) {
            case POPULAR: {
                movies = mViewModel.getPopularMovies(1);
                break;
            }
            case TOPRATED: {
                movies = mViewModel.getTopRatedMovies(1);
                break;
            }
            default: {
                break;
            }
        }

        if (movies != null) {
            movies.observe(this, new Observer<ApiResponse<ListResponse>>() {
                @Override
                public void onChanged(@Nullable ApiResponse<ListResponse> apiResponse) {
                    if (apiResponse != null) {
                        if (apiResponse.currentState == ApiResponse.State.SUCCESS) {
                            //success
                            adapter.setMovieItems(apiResponse.data.results);
                        }
                    }
                }
            });
        }
    }

    private void setupFilter() {
        binding.filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = binding.filterUi.getVisibility();
                if (visibility == View.VISIBLE) {
                    //make it gone now
                    binding.filterUi.setVisibility(View.GONE);
                } else {
                    binding.filterUi.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.popularFilmsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = CurrentMode.POPULAR;
                binding.movieDatabaseSubtitle.setText(R.string.popular_movies_subtitle);
                binding.popularFilmsFilter.setBackgroundResource(R.drawable.bottom_underline_selected_bg);
                binding.topRatedFilmsFilter.setBackground(null);
                binding.favFilmFilter.setBackground(null);
                fetchData();
            }
        });

        binding.topRatedFilmsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = CurrentMode.TOPRATED;
                binding.movieDatabaseSubtitle.setText(R.string.top_movies_subtitle);
                binding.topRatedFilmsFilter.setBackgroundResource(R.drawable.bottom_underline_selected_bg);
                binding.popularFilmsFilter.setBackground(null);
                binding.favFilmFilter.setBackground(null);
                fetchData();
            }
        });
    }
}
