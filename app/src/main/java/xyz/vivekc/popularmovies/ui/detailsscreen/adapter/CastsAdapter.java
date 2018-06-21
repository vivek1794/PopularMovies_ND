package xyz.vivekc.popularmovies.ui.detailsscreen.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.vivekc.popularmovies.R;
import xyz.vivekc.popularmovies.databinding.CastItemBinding;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiService;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.CastViewHolder> {

    private List<MovieDetails.Cast> castList;
    private Context context;

    public CastsAdapter(Context context) {
        this.context = context;
        this.castList = new ArrayList<>();
    }

    public void setCastList(List<MovieDetails.Cast> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.cast_item, parent, false);
        return new CastViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        final MovieDetails.Cast item = castList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {
        CastItemBinding binding;

        CastViewHolder(CastItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        void bind(MovieDetails.Cast item) {

            //set default icon if image is not available
            if (item.profilePath == null) {
                binding.profileImage.setImageResource(R.drawable.empty_user_avatar);
            } else {
                //load the image
                Glide.with(context)
                        .load(ApiService.getImageUrl(item.profilePath, ApiService.ImageSize.AVATAR_SIZE))
                        .apply(RequestOptions.circleCropTransform())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.profileImage);
            }

            //set the actor name and character name
            binding.actorName.setText(item.name);
            binding.actorCharacterName.setText(item.character);
        }
    }
}
