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
        MovieItem item = ((MovieItem) getIntent().getSerializableExtra("movie"));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance())
                    .commitNow();
        }
    }

    public static Intent getDetailsPage(Context context, MovieItem item) {
        Intent i = new Intent(context, DetailsActivity.class);
        i.putExtra("movie", item);
        return i;
    }
}
