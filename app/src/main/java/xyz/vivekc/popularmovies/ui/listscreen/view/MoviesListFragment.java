package xyz.vivekc.popularmovies.ui.listscreen.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;
import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.MoviesListFragmentBinding;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.model.MovieItem;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.ui.detailsscreen.view.DetailsActivity;
import xyz.vivekc.popularmovies.ui.listscreen.adapter.MovieListingAdapter;
import xyz.vivekc.popularmovies.ui.listscreen.viewmodel.MoviesListViewModel;


public class MoviesListFragment extends Fragment {


    //MVVM and Databinding stuff
    private MoviesListViewModel mViewModel;
    private MoviesListFragmentBinding binding;

    //recyclerview stuff
    private MovieListingAdapter adapter;
    List<MovieItem> movieResults = new ArrayList<>();

    //Util method to return instance of fragment.
    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflate layout using DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //create ViewModel and set it to the view
        mViewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        binding.setViewModel(mViewModel);

        //based on the orientation of the app, show 2 or 4 columns in the grid view
        int orientation = getResources().getConfiguration().orientation;
        setUpAdapter(orientation * 2);
        fetchData();
        setupFilter();
    }

    private void setUpAdapter(int spanCount) {

        //the usual setting up the recyclerview with layout manager and adapter
        binding.moviesListingGrid.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        adapter = new MovieListingAdapter(getActivity());
        binding.moviesListingGrid.setAdapter(adapter);
        adapter.setMovieItems(movieResults);


        //Getting the item that is clicked. Create a shared element transition
        //the transition name would be the movie's ID.
        adapter.setItemClickListener((movieItem, posterView) -> {
            Intent detailsPage = DetailsActivity.getDetailsPage(getActivity(), movieItem);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(), posterView,
                    String.valueOf(movieItem.id));
            startActivity(detailsPage, options.toBundle());
        });
    }

    /**
     * Fetch the data from TMDB server using APIs. Uses the `CurrentMode` enum value
     * to fetch POPULAR or TOPRATED movies list.
     */
    private void fetchData() {

        //LiveData containing the stages of API call along with MoviesList and the errors (if any)
        LiveData<ApiResponse<ListResponse>> movies = mViewModel.fetchData();

        if (movies != null) {
            //Observer added to the LiveData
            movies.observe(this, apiResponse -> {
                if (apiResponse != null) {
                    switch (apiResponse.currentState) {
                        case LOADING: {
                            startAnimation("loading.json");
                            break;
                        }
                        case SUCCESS: {
                            //success
                            stopAnimation();
                            movieResults = apiResponse.data.results;
                            adapter.setMovieItems(movieResults);
                            break;
                        }

                        case ERROR: {
                            startAnimation("error.json");
                            break;
                        }

                        case FAILURE: {
                            break;
                        }
                    }
                }
            });
        }
    }

    /**
     * Method which would hide the Grid view and show the Lottie Animation for LOADING and ERROR
     * LottieComposition is used to load the animation in the background and then display it once it is ready
     */
    private void startAnimation(String animationFile) {
        //these visibility changes can also go through viewmodel and data binding
        //but for the sake of simplicity, there are done here itself.
        binding.moviesListingGrid.setVisibility(View.GONE);
        binding.animationHolder.setVisibility(View.VISIBLE);
        LottieComposition.Factory.fromAssetFileName(requireContext(), animationFile, composition -> {
            if (composition != null) {
                binding.animationView.setComposition(composition);
                binding.animationView.setRepeatCount(LottieDrawable.INFINITE);
                binding.animationView.playAnimation();
            }
        });
    }

    /**
     * Stops the animation, hides the animation view and makes the Grid layout appear
     */
    private void stopAnimation() {
        binding.animationView.cancelAnimation();
        binding.animationHolder.setVisibility(View.GONE);
        binding.moviesListingGrid.setVisibility(View.VISIBLE);
    }

    /**
     * Method which initiates the filter logic.
     * <p>
     * Will display the filter options when the filter icon is clicked. The filter buttons would then
     * make the logic and UI decisions based on the item clicked
     */
    private void setupFilter() {
        binding.filterIcon.setOnClickListener(v -> toggleFilterUi());

        //make the UI for popular film as selected and fetch data from api
        binding.popularFilmsFilter.setOnClickListener(v -> {
            selectPopularMovies();
            fetchData();
        });

        //make the UI for toprated film as selected and fetch data from api
        binding.topRatedFilmsFilter.setOnClickListener(v -> {
            selectTopRatedMovies();
            fetchData();
        });
    }

    /**
     * Switches the visibility of the Filter UI
     */
    private void toggleFilterUi() {
        int visibility = binding.filterUi.getVisibility();
        if (visibility == View.VISIBLE) {
            //make it gone now
            binding.filterUi.setVisibility(View.GONE);
        } else {
            binding.filterUi.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Mark the "POPULAR" movies as selected mode for the listing
     * updates the UI to reflect the same.
     */
    private void selectPopularMovies() {
        mViewModel.setCurrentMode(MoviesListViewModel.CurrentMode.POPULAR);
        binding.popularFilmsFilter.setBackgroundResource(R.drawable.bottom_underline_selected_bg);
        binding.topRatedFilmsFilter.setBackground(null);
        binding.favFilmFilter.setBackground(null);
    }

    /**
     * Mark the "TOPRATED" movies as selected mode for the listing
     * updates the UI to reflect the same.
     */
    private void selectTopRatedMovies() {
        mViewModel.setCurrentMode(MoviesListViewModel.CurrentMode.TOPRATED);
        binding.topRatedFilmsFilter.setBackgroundResource(R.drawable.bottom_underline_selected_bg);
        binding.popularFilmsFilter.setBackground(null);
        binding.favFilmFilter.setBackground(null);
    }

    /**
     * We have asked Android not to recreate this activity when rotation happens in the manifest
     * So, we are handling the config changes and resetting the adapter without hitting the API again
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentOrientation = getResources().getConfiguration().orientation;
        setUpAdapter(currentOrientation * 2);
    }
}
