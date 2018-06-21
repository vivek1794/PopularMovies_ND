package xyz.vivekc.popularmovies.ui.detailsscreen.view;

import android.arch.lifecycle.LiveData;
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
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.DetailsFragmentBinding;
import xyz.vivekc.popularmovies.model.MovieItem;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.repository.api.ApiService;
import xyz.vivekc.popularmovies.ui.detailsscreen.adapter.CastsAdapter;
import xyz.vivekc.popularmovies.ui.detailsscreen.viewmodel.DetailsViewModel;

public class DetailsFragment extends Fragment {

    //MVVM and databinding stuff
    private DetailsViewModel viewModel;
    DetailsFragmentBinding binding;
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE = "movie";

    //Util method to return instance of fragment.
    public static DetailsFragment newInstance(MovieItem movie) {
        DetailsFragment frag = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID, movie.id);
        bundle.putSerializable(MOVIE, movie);
        frag.setArguments(bundle);
        return frag;
    }

    /**
     * Method to return MovieId from the arguments
     */
    private static int getMovieIdFromArgs(Bundle args) {
        return args.getInt(MOVIE_ID);
    }

    /**
     * Method to return Movie instance from arguments
     */
    private static MovieItem getMovieFromArgs(Bundle args) {
        return ((MovieItem) args.getSerializable(MOVIE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //inflating the views
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpEntryTransition();
        setUpViewModel();
        setUpUI();
    }

    /**
     * Method to take care of the Shared element entry transition
     */
    private void setUpEntryTransition() {
        //set the transitionName so that the shared element transition would work
        if (getArguments() != null) {
            binding.movieThumb.setTransitionName(String.valueOf(getMovieIdFromArgs(getArguments())));
        }

        //Now the fragment is loaded, we can resume the postponed shared element transition
        binding.movieThumb.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                binding.movieThumb.getViewTreeObserver().removeOnPreDrawListener(this);
                //resume the transition
                requireActivity().startPostponedEnterTransition();
                return true;
            }
        });
    }

    /**
     * Method to create the ViewModel and set it to Binding variable
     */
    private void setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        binding.setDetailsViewModel(viewModel);
    }

    /**
     * The method which takes care of all the UI works.
     * This would initially load the data available from the intent extras and fragment bundle.
     * It would also initiate API call to get data regarding the Genre and cast details
     */
    private void setUpUI() {
        if (getArguments() != null) {
            populateUIFromArgs(getMovieFromArgs(getArguments()));
            fetchGenreAndCastDetails();
        }
    }


    /**
     * Populate UI from the MovieItem class instance received.
     * We will get the image URLs, movie title, release dates, summary and user ratings
     *
     * @param movieItem MovieItem instance
     */
    private void populateUIFromArgs(MovieItem movieItem) {
        loadImage(movieItem.backdropPath, ApiService.ImageSize.BACKDROP_SIZE, binding.moviePoster);
        loadImage(movieItem.posterPath, ApiService.ImageSize.POSTER_SIZE, binding.movieThumb);

        viewModel.setMovieDetails(movieItem);
    }


    /**
     * Load the image of given size to the given imageview
     */
    private void loadImage(String imagePath, ApiService.ImageSize imageSize, ImageView imageView) {
        Glide.with(requireContext())
                .load(ApiService.getImageUrl(imagePath, imageSize))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * Fetch the Genre and Cast from Movie Details
     */
    private void fetchGenreAndCastDetails() {
        LiveData<ApiResponse<MovieDetails>> detailsLiveData;
        if (getArguments() != null) {
            detailsLiveData = viewModel.getMovieDetails(getMovieIdFromArgs(getArguments()));
            detailsLiveData.observe(this, movieDetailsApiResponse -> {
                if (movieDetailsApiResponse != null) {
                    switch (movieDetailsApiResponse.currentState) {
                        case LOADING: {
                            break;
                        }
                        case SUCCESS: {
                            populateUIFromApi(movieDetailsApiResponse.data);
                            break;
                        }
                        case ERROR:
                        case FAILURE: {
                            break;
                        }

                    }
                }
            });
        }
    }

    /**
     * Populating the UI from the api MovieDetails
     */
    private void populateUIFromApi(MovieDetails movieDetails) {
        binding.genreTitle.setVisibility(View.VISIBLE);
        binding.genreList.setVisibility(View.VISIBLE);
        binding.castTitle.setVisibility(View.VISIBLE);
        setupGenreList(binding.genreContainer, movieDetails.genres);
        setupCastsGrid(movieDetails.casts.cast);
    }

    /**
     * Method to create multiple TextViews to inflate the Genres
     */
    private void setupGenreList(LinearLayout genreContainer, List<MovieDetails.Genre> genres) {
        for (MovieDetails.Genre genre : genres) {
            TextView genreTV = (TextView) LayoutInflater.from(requireContext()).inflate(R.layout.genre_chip_layout, genreContainer, false);
            genreTV.setText(genre.name);
            genreContainer.addView(genreTV);
        }
    }

    /**
     * Method to setup the casts grid adapter
     */
    private void setupCastsGrid(List<MovieDetails.Cast> cast) {
        CastsAdapter adapter = new CastsAdapter(requireContext());
        adapter.setCastList(cast);
        binding.castsGridView.setAdapter(adapter);
        binding.castsGridView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

}
