package xyz.vivekc.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import xyz.vivekc.popularmovies.repository.MoviesRepository;

/**
 * An abstract BaseViewModel of type AndroidViewModel.
 *
 * This class would reduce the need to create a Repository for each and every viewmodel class.
 */
public abstract class BaseViewModel extends AndroidViewModel {

    protected MoviesRepository repository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        repository = new MoviesRepository();
    }

}
