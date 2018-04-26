package com.roshan.gallery.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roshan.gallery.R;
import com.roshan.gallery.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<ImageModel> images;
    private Context mContext;
    private boolean isFavoriteClick = false;
    private PopupWindow mPopupWindow;
    private List<ImageModel> favorite_images;
    private OnFavoriteListener listener;

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


    public GalleryAdapter(Context context, List<ImageModel> images) {
        this.mContext = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ImageModel image = images.get(position);

        //holder.title.setText("" + image.getId());
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
                listener.onClick(image);
                if (!image.isFavorite() && !isFavoriteClick) {
                    isFavoriteClick = true;
                    favorite_images = new ArrayList<>();
                    favorite_images.add(image);
                    holder.favorite.setImageResource(R.drawable.ic_action_star_gold);
                } else {
                    favorite_images.remove(image);
                    isFavoriteClick = false;
                    holder.favorite.setImageResource(R.drawable.ic_action_star);
                }
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.popup_layout, null);
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        980,
                        1280
                );
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setFocusable(true);
                // Set an elevation value for popup window
                // Call requires API level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }

                TextView id = customView.findViewById(R.id.tv_thumbnail_id);
                TextView width = customView.findViewById(R.id.tv_thumbnail_width);
                TextView height = customView.findViewById(R.id.tv_thumbnail_height);
                TextView owner = customView.findViewById(R.id.tv_thumbnail_owner);
                ImageView imageView = customView.findViewById(R.id.image_thumbnail);
                Button btnClose = customView.findViewById(R.id.btn_close_window);
                Glide.with(mContext).load(image.getRegular())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);

                id.setText("" + image.getId().toString());
                width.setText("" + image.getWidth());
                height.setText("" + image.getHeight());
                owner.setText("" + image.getOwner());

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);
            }
        });
//        holder.thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(mContext,"Long Clicked",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    public interface OnFavoriteListener{
        public void onClick(ImageModel model);
    }

    public void setListener(OnFavoriteListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

//    public interface ClickListener {
//        void onClick(View view, int position);
//
//        void onLongClick(View view, int position);
//    }
//
//    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//
//        private GestureDetector gestureDetector;
//        private GalleryAdapter.ClickListener clickListener;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
//            this.clickListener = clickListener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//                clickListener.onClick(child, rv.getChildPosition(child));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }

}
