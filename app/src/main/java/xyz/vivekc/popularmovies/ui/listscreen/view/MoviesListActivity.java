package xyz.vivekc.popularmovies.ui.listscreen.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.vivekc.popularmovies.R;

public class MoviesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list_activity);

        //we do not do anything in the activity. Everything is taken care of by the fragment
        //this is great if we want to reuse the UI across the app
        //also, with the upcoming Navigation pattern introduced this I/O, having all UI in fragments
        //would be easier to migrate when it reaches stable version
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MoviesListFragment.newInstance())
                    .commitNow();
        }
    }
}
