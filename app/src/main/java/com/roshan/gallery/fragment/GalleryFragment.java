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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.roshan.gallery.R;
import com.roshan.gallery.adapter.GalleryAdapter;
import com.roshan.gallery.app.AppController;
import com.roshan.gallery.entity.FavouriteEntity;
import com.roshan.gallery.model.ImageModel;
import com.roshan.gallery.utills.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeSet;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class GalleryFragment extends Fragment implements GalleryAdapter.OnFavoriteListener {

    View view;
    private RecyclerView recyclerView;

    private static final String url = "https://api.unsplash.com/photos/?client_id=cc506a9dd764cf4cc5bbe085fd95b7961afded842cd02c6ab3ea75bd5fea5514&per_page=20";
    private ArrayList<ImageModel> imageList;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private String TAG = GalleryFragment.class.getSimpleName();
    private Realm realm;
    private TreeSet<String> favImageList;

    public GalleryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        pDialog = new ProgressDialog(getContext());
        imageList = new ArrayList<>();
        mAdapter = new GalleryAdapter(getContext(), imageList);

        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);
        realm = Realm.getDefaultInstance();
        getFavImages();
        fetchImages();
        return view;
    }

    private void getFavImages() {
        favImageList = new TreeSet<>();
        RealmQuery<FavouriteEntity> query = realm.where(FavouriteEntity.class);
        RealmResults<FavouriteEntity> list = query.findAll();
        for (int i = 0; i < list.size(); i++) {
            FavouriteEntity ob = list.get(i);
            favImageList.add(ob.getId());
        }

    }

    private void fetchImages() {

        pDialog.setMessage("Loading Images...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        imageList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                ImageModel image = new ImageModel();
                                image.setId(object.getString("id"));
                                image.setWidth(object.getInt("width"));
                                image.setHeight(object.getInt("height"));
                                image.setLike(object.getInt("likes"));

                                JSONObject url = object.getJSONObject("urls");
                                image.setSmall(url.getString("small"));
                                image.setRegular(url.getString("regular"));
                                image.setFull(url.getString("full"));
                                //image.setTimestamp(object.getString("timestamp"));

                                JSONObject user = object.getJSONObject("user");
                                image.setOwner(user.getString("name"));
                                boolean isFav = favImageList.contains(image.getId());
                                image.setFavorite(isFav);
                                imageList.add(image);
                                //Log.d("Dev", "Id : " + i +" ImgId : "+ image.getId() +" IsFav " + isFav );
                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onClick(final ImageModel model) {
       // Toast.makeText(getActivity(), model.getOwner(), Toast.LENGTH_SHORT).show();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                FavouriteEntity fav = new FavouriteEntity();
                fav.setOwner(model.getOwner());
                fav.setDesc("Sample Desc");
                fav.setlUrl(model.getFull());
                fav.setsUrl(model.getSmall());
                fav.setrUrl(model.getRegular());
                fav.setId(model.getId());
                realm.copyToRealmOrUpdate(fav);
            }
        });


    }
}
