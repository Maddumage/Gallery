package com.roshan.gallery.fragment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshan.gallery.R;
import com.roshan.gallery.activity.MainActivity;
import com.roshan.gallery.adapter.FavoriteAdapter;
import com.roshan.gallery.entity.FavouriteEntity;
import com.roshan.gallery.listener.OnFragmentChangeListener;
import com.roshan.gallery.model.FavoriteModel;
import com.roshan.gallery.util.GridSpacingItemDecoration;
import com.roshan.gallery.util.Utils;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavouriteFragment extends Fragment implements OnFragmentChangeListener {

    private Realm realm;
    private ProgressDialog pDialog;
    private ArrayList<FavoriteModel> favouriteEntities;
    private FavoriteAdapter favoriteAdapter;
    private RecyclerView recyclerView;

    public FavouriteFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        pDialog = new ProgressDialog(getContext());

        //favouriteEntities = new ArrayList<>();
        //favoriteAdapter = new FavoriteAdapter(getContext(), favouriteEntities);
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getContext(), 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //getFavImages();


        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setFavListener(this);
        return view;
    }

    @Override
    public void onPageLoad() {
        getFavImages();
    }

    public void getFavImages() {
        realm = Realm.getDefaultInstance();
        RealmQuery<FavouriteEntity> query = realm.where(FavouriteEntity.class);
        favouriteEntities = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(getContext(), favouriteEntities);
        RealmResults<FavouriteEntity> list = query.findAll();
        for (int i = 0; i < list.size(); i++) {
            FavouriteEntity ob = list.get(i);
            FavoriteModel newOb = new FavoriteModel();
            newOb.setId(ob.getId());
            newOb.setDesc(ob.getDesc());
            newOb.setlUrl(ob.getlUrl());
            newOb.setrUrl(ob.getrUrl());
            newOb.setsUrl(ob.getsUrl());
            newOb.setOwner(ob.getOwner());
            favouriteEntities.add(newOb);
            Log.d("Test", i + " => " + ob.getId() + " " + ob.getOwner() + " Link => " + ob.getlUrl());
        }
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();
    }
}
