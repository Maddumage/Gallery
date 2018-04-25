package com.roshan.gallery.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.roshan.gallery.R;

public class GalleryFragment extends Fragment {

    private GridView gridView;
    View view;
    public GalleryFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView gridView = view.findViewById(R.id.recycler_view);
        //gridView.setAdapter(new GalleryAdapter(view.getContext()));
        return view;
    }

}
