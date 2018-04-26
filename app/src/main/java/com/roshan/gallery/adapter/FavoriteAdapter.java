package com.roshan.gallery.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roshan.gallery.R;
import com.roshan.gallery.entity.FavouriteEntity;
import com.roshan.gallery.model.FavoriteModel;

import java.util.ArrayList;

import io.realm.Realm;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<FavoriteModel> arrayList;
    private PopupWindow mPopupWindow;
    boolean isCloseBtnEnable = true;
    String desc = "";
    Realm realm;

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel> favouriteEntities) {
        realm = Realm.getDefaultInstance();
        this.mContext = context;
        this.arrayList = favouriteEntities;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail_favorite;

        public MyViewHolder(View view) {
            super(view);
//            title = view.findViewById(R.id.title);
//            like = view.findViewById(R.id.like);
            thumbnail_favorite = view.findViewById(R.id.thumbnail_favorite);
//            favorite = view.findViewById(R.id.favorite);
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final FavoriteModel image = arrayList.get(position);

        // holder.favorite.setImageDrawable(R.drawable.ic_menu_gallery);
        // loading images from Unsplash
        Glide.with(mContext).load(image.getrUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail_favorite);

        holder.thumbnail_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.favorite_popup_layout, null);
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

                final ImageView fvImage = customView.findViewById(R.id.image_thumbnail_fv);
                final EditText editDesc = customView.findViewById(R.id.et_description);
                final Button btnClose = customView.findViewById(R.id.btn_close_window);
                ImageView btnEditDesc = customView.findViewById(R.id.btnEditDesc);

                Glide.with(mContext).load(image.getrUrl())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(fvImage);
                editDesc.setText(image.getDesc());

                btnEditDesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDesc.setEnabled(true);

                        isCloseBtnEnable = false;
                        btnClose.setText("Update");
                    }
                });
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCloseBtnEnable) {
                            mPopupWindow.dismiss();
                        } else {
                            //image.setDesc(desc);
                            image.setDesc(editDesc.getText().toString());
                            updateDesc(image, position);
                        }

                    }
                });

                mPopupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);
            }
        });

        holder.thumbnail_favorite.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteImage(image.getId(), position);
                return false;
            }
        });
    }

    private synchronized void deleteImage(final String id, int position) {

        final FavouriteEntity delete = realm.where(FavouriteEntity.class)
                .equalTo("id", id)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                delete.deleteFromRealm();
            }
        });
        arrayList.remove(position);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void updateDesc(final FavoriteModel model, final int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("Dev" , "DESC "+ desc);
                FavouriteEntity fav = new FavouriteEntity();
                fav.setId(model.getId());
                fav.setrUrl(model.getrUrl());
                fav.setlUrl(model.getlUrl());
                fav.setsUrl(model.getsUrl());
                fav.setOwner(model.getOwner());
                fav.setDesc(desc);
                realm.copyToRealmOrUpdate(fav);
                mPopupWindow.dismiss();
                arrayList.set(position,model);
                notifyDataSetChanged();
            }
        });
    }
}
