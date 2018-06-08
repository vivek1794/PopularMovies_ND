package xyz.vivekc.popularmovies.ui.listscreen.ui.movieslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.model.MovieItem;
import xyz.vivekc.popularmovies.repository.api.ApiService;

class MovieListingAdapter extends RecyclerView.Adapter<MovieListingAdapter.MoviePosterViewHolder> {

    List<MovieItem> movieItems;
    Context context;

    public MovieListingAdapter(Context context) {
        this.context = context;
        this.movieItems = new ArrayList<>();
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviePosterViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.movies_listing_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        MovieItem item = movieItems.get(position);
        Glide.with(context)
                .load(ApiService.getImageUrl(item.posterPath))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder {

        ImageView posterView;

        public MoviePosterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.poster);
        }
    }
}
