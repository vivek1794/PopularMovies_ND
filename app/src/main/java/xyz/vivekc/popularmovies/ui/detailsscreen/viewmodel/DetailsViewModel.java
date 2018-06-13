package xyz.vivekc.popularmovies.ui.detailsscreen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.ui.BaseViewModel;

public class DetailsViewModel extends BaseViewModel {

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ApiResponse<MovieDetails>> getMovieDetails(int movieId) {
        return repository.getMovieDetails(movieId, getApplication().getString(R.string.api_key));
    }

    public String getGenresList(MovieDetails movieDetails) {
        List<MovieDetails.Genre> genreList = movieDetails.genres;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < genreList.size(); i++) {
            MovieDetails.Genre genre = genreList.get(i);
            builder.append(genre.name);
            if (i != genreList.size() - 1) {
                //add comma for all elements except last
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
