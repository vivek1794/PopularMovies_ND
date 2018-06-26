package xyz.vivekc.popularmovies.ui.detailsscreen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.MovieItem;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.ui.BaseViewModel;

public class DetailsViewModel extends BaseViewModel {

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    /* ******************************************************************
     *  UI
     * ******************************************************************/

    public ObservableField<String> movieTitle = new ObservableField<>();
    public ObservableField<String> moviePlotSummary = new ObservableField<>();
    public ObservableField<String> movieUserRating = new ObservableField<>();
    public ObservableField<String> movieReleaseDate = new ObservableField<>();

    public void setMovieDetails(MovieItem movieItem) {
        movieTitle.set(movieItem.title);
        moviePlotSummary.set(movieItem.overview);
        movieUserRating.set(String.valueOf(movieItem.voteAverage));
        movieReleaseDate.set(getMovieReleaseDateString(movieItem.releaseDate));
    }

    private String getMovieReleaseDateString(String releaseDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat outputDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        try {
            Date date = df.parse(releaseDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Not Available";
        }
    }

    /* ******************************************************************
     *  API works
     * ******************************************************************/
    public LiveData<ApiResponse<MovieDetails>> getMovieDetails(int movieId) {
        return repository.getMovieDetails(movieId, getApplication().getString(R.string.api_key));
    }


}
