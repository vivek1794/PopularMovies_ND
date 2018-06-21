package xyz.vivekc.popularmovies.ui.detailsscreen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.MovieItem;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        //we receive the movie details from the intent extra
        MovieItem movieId = ((MovieItem) getIntent().getSerializableExtra("movie"));

        //to do shared element transition from the listing activity to view in the DetailsFragment,
        //we need the fragment to be loaded before animations are done
        //so, we postpone the enter transitions until fragment is alive
        postponeEnterTransition();

        //we do not do anything in the activity. Everything is taken care of by the fragment
        //this is great if we want to reuse the UI across the app
        //also, with the upcoming Navigation pattern introduced this I/O, having all UI in fragments
        //would be easier to migrate when it reaches stable version
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(movieId))
                    .commitNow();
        }
    }

    public static Intent getDetailsPage(Context context, MovieItem item) {
        Intent i = new Intent(context, DetailsActivity.class);
        i.putExtra("movie", item);
        i.putExtra("transition_name", item.id);
        return i;
    }
}
