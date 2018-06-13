package xyz.vivekc.popularmovies.ui.detailsscreen.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.DetailsFragmentBinding;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.repository.api.ApiService;
import xyz.vivekc.popularmovies.ui.detailsscreen.adapter.CastsAdapter;
import xyz.vivekc.popularmovies.ui.detailsscreen.viewmodel.DetailsViewModel;

public class DetailsFragment extends Fragment {

    private DetailsViewModel viewModel;
    DetailsFragmentBinding binding;

    public static DetailsFragment newInstance(int movieId) {
        DetailsFragment frag = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieId);
        frag.setArguments(bundle);
        return frag;
    }

    private static int getMovieIdFromArgs(Bundle args) {
        if (args != null) {
            return args.getInt("movie_id");
        } else {
            return 0;
        }
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

        LiveData<ApiResponse<MovieDetails>> detailsLiveData = viewModel.getMovieDetails(getMovieIdFromArgs(getArguments()));

        detailsLiveData.observe(this, movieDetailsApiResponse -> {
            if (movieDetailsApiResponse != null) {
                switch (movieDetailsApiResponse.currentState) {
                    case SUCCESS: {
                        populateUI(movieDetailsApiResponse.data);
                        break;
                    }
                }
            }
        });
    }

    private void populateUI(MovieDetails movieDetails) {

        loadImages(movieDetails, () -> {
            binding.details.setVisibility(View.VISIBLE);
            binding.movieTitle.setText(movieDetails.title);
            binding.plotSummaryText.setText(movieDetails.overview);
            binding.movieGenreList.setText(viewModel.getGenresList(movieDetails));
            binding.movieUserRating.setText(String.valueOf(movieDetails.voteAverage));
            binding.movieReleaseDate.setText(movieDetails.releaseDate);

            setupCastsGrid(movieDetails.casts.cast);
        });

    }

    private void setupCastsGrid(List<MovieDetails.Cast> cast) {
        CastsAdapter adapter = new CastsAdapter(requireContext());
        adapter.setCastList(cast);
        binding.castsGridView.setAdapter(adapter);
        binding.castsGridView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    private void loadImages(MovieDetails movieDetails, ImageLoadCompletedListener listener) {

        //we load the images first and then make the UI visible. This will make the UI complete when it appears
        //only the backdropimage and the posterimage are loaded upfront.
        Glide.with(requireContext())
                .load(ApiService.getBackdropImageUrl(movieDetails.backdropPath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new CustomGlideListener() {
                    @Override
                    public void onImageLoaded() {
                        Glide.with(requireContext())
                                .load(ApiService.getImageUrl(movieDetails.posterPath))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .listener(new CustomGlideListener() {
                                    @Override
                                    public void onImageLoaded() {
                                        listener.onImagesLoaded();
                                    }
                                })
                                .into(binding.movieThumb);
                    }
                })
                .into(binding.moviePoster);
    }

    interface ImageLoadCompletedListener {
        public void onImagesLoaded();
    }

    abstract class CustomGlideListener implements RequestListener<Drawable> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            onImageLoaded();
            return false;
        }

        abstract public void onImageLoaded();
    }
}
