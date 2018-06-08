package xyz.vivekc.popularmovies.ui.listscreen.ui.movieslist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.ui.BaseViewModel;

public class MoviesListViewModel extends BaseViewModel {

    public MoviesListViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<ApiResponse<ListResponse>> getPopularMovies(int pageNumber) {
        return repository.getPopularMovies(pageNumber, getApplication().getString(R.string.api_key));
    }

    LiveData<ApiResponse<ListResponse>> getTopRatedMovies(int pageNumber) {
        return repository.getTopRatedMovies(pageNumber, getApplication().getString(R.string.api_key));
    }

}
