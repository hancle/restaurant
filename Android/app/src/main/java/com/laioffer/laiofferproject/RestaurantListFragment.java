package com.laioffer.laiofferproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class RestaurantListFragment extends Fragment {

    public RestaurantListFragment() {
        // Required empty public constructor
    }
    private ListView listView;
    private DataService dataService;
    private SwipeRefreshLayout swipeRefreshLayout;

    // This is used to determine whether we should call RecommendRestaurants API.
    private int visited_restaurant_count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        listView = (ListView) view.findViewById(R.id.restaurant_list);
        dataService = new DataService(Volley.newRequestQueue(this.getActivity()));

        // Set a short click listener to ListView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) listView.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(
                        RestaurantMapActivity.EXTRA_LATLNG,
                        new LatLng(r.getLat(), r.getLng()));
                Intent intent = new Intent(view.getContext(), RestaurantMapActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });

        // Set a long click listener to ListView.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                Restaurant r = (Restaurant) listView.getItemAtPosition(position);
                new SetVisitedRestaurantsAsyncTask(view, dataService, r.getBusinessId()).execute();
                return true;
            }
        });

        refreshRestaurantList(dataService);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRestaurantList(dataService);
            }
        });

        return view;
    }

    // Make a async call to get restaurant data.
    private void refreshRestaurantList(DataService dataService) {
        new GetRestaurantsNearbyAsyncTask(this, dataService).execute();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }



    private class GetRestaurantsNearbyAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {

        private Fragment fragment;
        private DataService dataService;
        private Clock clock;

        public GetRestaurantsNearbyAsyncTask(Fragment fragment, DataService dataService) {
            this.fragment = fragment;
            this.dataService = dataService;
            this.clock = new Clock();
            this.clock.reset();
        }

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            clock.start();
            if (visited_restaurant_count > 0) {
                visited_restaurant_count = 0;
                return dataService.RecommendRestaurants();
            } else {
                return dataService.getNearbyRestaurants();
            }
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            // Measure the latency of the API call.
            clock.stop();
            Log.e("Latency", Long.toString(clock.getCurrentInterval()));

            if (restaurants != null) {
                super.onPostExecute(restaurants);
                RestaurantAdapter adapter = new RestaurantAdapter(fragment.getActivity(), restaurants);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(fragment.getActivity(), "Data service error.", Toast.LENGTH_LONG);
            }
        }
    }

    private class SetVisitedRestaurantsAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private View view;
        private DataService dataService;
        private String businessId;
        private Clock clock;

        public SetVisitedRestaurantsAsyncTask(View view, DataService dataService,
                                              String businessId) {
            this.view = view;
            this.dataService = dataService;
            this.businessId = businessId;

            this.clock = new Clock();
            this.clock.reset();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            clock.start();
            return dataService.setVisitedRestaurants(businessId) != null;
        }

        @Override
        protected void onPostExecute(Boolean succeeded) {
            // Measure the latency of the API call.
            clock.stop();
            Log.e("Latency", Long.toString(clock.getCurrentInterval()));

            if (succeeded) {
                visited_restaurant_count++;
                ImageView vistedImageView = (ImageView) view.findViewById(R.id.has_visited_img);
                vistedImageView.setImageResource(R.drawable.visited);
            }
        }
    }

}
