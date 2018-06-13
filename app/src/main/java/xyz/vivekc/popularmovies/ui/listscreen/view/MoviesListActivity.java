package xyz.vivekc.popularmovies.ui.listscreen.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.vivekc.popularmovies.R;

public class MoviesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MoviesListFragment.newInstance())
                    .commitNow();
        }
    }
}
