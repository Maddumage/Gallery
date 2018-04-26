package com.roshan.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roshan.gallery.R;
import com.roshan.gallery.model.Image;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{

    private List<Image> images;
    private Context mContext;
    private boolean isFavoriteClick = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, like;
        public ImageView thumbnail, favorite;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            like = view.findViewById(R.id.like);
            thumbnail = view.findViewById(R.id.thumbnail);
            favorite = view.findViewById(R.id.favorite);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Image image = images.get(position);

        holder.title.setText(image.getId());
        holder.like.setText("" + image.getLike());
        if (image.isFavorite()) {
            holder.favorite.setImageResource(R.drawable.ic_action_star_gold);
        }
        // holder.favorite.setImageDrawable(R.drawable.ic_menu_gallery);
        // loading images from Unsplash
        Glide.with(mContext).load(image.getSmall())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!image.isFavorite() && !isFavoriteClick) {
                    isFavoriteClick = true;
                    holder.favorite.setImageResource(R.drawable.ic_action_star_gold);
                } else {
                    isFavoriteClick = false;
                    holder.favorite.setImageResource(R.drawable.ic_action_star);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
