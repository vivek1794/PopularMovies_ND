package xyz.vivekc.popularmovies.ui.detailsscreen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import xyz.vivekc.popularmovies.R;
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
        return new CastViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.cast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        final MovieDetails.Cast item = castList.get(position);

        if (item.profilePath == null) {
            holder.profileView.setImageResource(R.drawable.empty_user_avatar);
        } else {
            Glide.with(context)
                    .load(ApiService.getImageUrl(item.profilePath))
                    .apply(RequestOptions.circleCropTransform())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.profileView);
        }

        holder.actorName.setText(item.name);
        holder.characterName.setText(item.character);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView profileView;
        TextView actorName;
        TextView characterName;

        public CastViewHolder(View itemView) {
            super(itemView);
            profileView = itemView.findViewById(R.id.profile_image);
            characterName = itemView.findViewById(R.id.actor_character_name);
            actorName = itemView.findViewById(R.id.actor_name);
        }
    }
}
