package xyz.vivekc.popularmovies.ui.listscreen.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.MoviesListingItemLayoutBinding;
import xyz.vivekc.popularmovies.model.MovieItem;
import xyz.vivekc.popularmovies.repository.api.ApiService;

public class MovieListingAdapter extends RecyclerView.Adapter<MovieListingAdapter.MoviePosterViewHolder> {

    private List<MovieItem> movieItems;
    private Context context;
    private MovieItemSelectedListener listener;

    /**
     * Interface to help in communicating movie item clicked to the fragment
     */
    public interface MovieItemSelectedListener {
        void onMovieItemSelected(MovieItem movieItem, ImageView posterView);
    }

    public MovieListingAdapter(Context context) {
        this.context = context;
        this.movieItems = new ArrayList<>();
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

    public void setItemClickListener(MovieItemSelectedListener movieItemSelectedListener) {
        this.listener = movieItemSelectedListener;
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MoviesListingItemLayoutBinding itemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movies_listing_item_layout,
                parent, false);
        return new MoviePosterViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
        final MovieItem item = movieItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder {
        MoviesListingItemLayoutBinding binding;

        MoviePosterViewHolder(MoviesListingItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        void bind(MovieItem item) {
            //setting the movie id as transitionName as it would be unique
            ViewCompat.setTransitionName(binding.poster, String.valueOf(item.id));

            //loading the image to imageview
            Glide.with(context)
                    .load(ApiService.getImageUrl(item.posterPath, ApiService.ImageSize.POSTER_SIZE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.poster);

            //setting click listener
            binding.getRoot().setOnClickListener(v -> listener.onMovieItemSelected(item, binding.poster));
        }
    }
}
