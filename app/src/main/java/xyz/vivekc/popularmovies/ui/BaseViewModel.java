package xyz.vivekc.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import xyz.vivekc.popularmovies.repository.MoviesRepository;

public abstract class BaseViewModel extends AndroidViewModel {

    protected MoviesRepository repository;
    public BaseViewModel(@NonNull Application application) {
        super(application);
        repository = new MoviesRepository();
    }

}
