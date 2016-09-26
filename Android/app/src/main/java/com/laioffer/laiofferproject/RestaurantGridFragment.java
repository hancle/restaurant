package com.laioffer.laiofferproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class RestaurantGridFragment extends Fragment {

    public RestaurantGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.restaurant_grid);
        //gridView.setAdapter(new RestaurantAdapter(getActivity()));
        return view;

    }

}
