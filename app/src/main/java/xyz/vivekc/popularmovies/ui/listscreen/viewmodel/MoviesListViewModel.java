package xyz.vivekc.popularmovies.ui.listscreen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.ui.BaseViewModel;

public class MoviesListViewModel extends BaseViewModel {

    public MoviesListViewModel(@NonNull Application application) {
        super(application);
    }



    /* ************************************************************************************
     * UI works
     * ************************************************************************************/

    /**
     * Enum to define which set of movies to load. This enum is used in filtering
     * the UI grid and displaying popular or top rated or favourites (in stage2) movies
     */
    public enum CurrentMode {
        POPULAR, TOPRATED, FAVOURITES
    }

    private MoviesListViewModel.CurrentMode mode = MoviesListViewModel.CurrentMode.POPULAR;


    public ObservableField<String> subTitleString = new ObservableField<>(getApplication().getResources().getString(R.string.popular_movies_subtitle));


    public void setCurrentMode(CurrentMode mode) {
        this.mode = mode;
        if (mode == CurrentMode.POPULAR) {
            subTitleString.set(getApplication().getString(R.string.popular_movies_subtitle));
        } else if (mode == CurrentMode.TOPRATED) {
            subTitleString.set(getApplication().getString(R.string.top_movies_subtitle));
        }

    }

    /* ************************************************************************************
     * API calls
     * ************************************************************************************/

    public LiveData<ApiResponse<ListResponse>> fetchData() {
        LiveData<ApiResponse<ListResponse>> movies = null;
        switch (mode) {
            case POPULAR: {
                movies = getPopularMovies();
                break;
            }
            case TOPRATED: {
                movies = getTopRatedMovies();
                break;
            }
            case FAVOURITES: {
                //todo NOT IMPLEMENTED FOR STAGE1
            }
        }
        return movies;
    }

    private LiveData<ApiResponse<ListResponse>> getPopularMovies() {
        return repository.getPopularMovies(1, getApplication().getString(R.string.api_key));
    }

    private LiveData<ApiResponse<ListResponse>> getTopRatedMovies() {
        return repository.getTopRatedMovies(1, getApplication().getString(R.string.api_key));
    }

}
