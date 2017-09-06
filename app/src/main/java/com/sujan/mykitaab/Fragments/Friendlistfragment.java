package com.sujan.mykitaab.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sujan.mykitaab.HelperClass.FeedClass;
import com.sujan.mykitaab.HelperClass.MessageEvent;
import com.sujan.mykitaab.HelperClass.MyRecyclerViewAdapter;
import com.sujan.mykitaab.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 4/16/17.
 */

public class Friendlistfragment extends Fragment {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    StaggeredGridLayoutManager stagManager;
    SwipeRefreshLayout swipeRefreshLayout;
    List<FeedClass> mylist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendlistfragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myrecyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                fetchTimelineAsync(0);


            }
        });


        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (recyclerView.getLayoutManager().equals(stagManager)) {
            recyclerView.setLayoutManager(linearLayoutManager);

        } else {
            recyclerView.setLayoutManager(stagManager);
        }
        adapter.notifyDataSetChanged();
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        mylist.clear();

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() == null) {

                            Log.v("LoginActivity", response.toString());
                            JSONObject jsonObject = response.getJSONObject();
                            try {
                                JSONObject feedmasterobject = jsonObject.getJSONObject("feed");
                                JSONArray data_feed = feedmasterobject.getJSONArray("data");
                                for (int x = 0; x < data_feed.length(); x++) {
                                    JSONObject feed_object = data_feed.getJSONObject(x);
                                    //  Log.e("....",feed_object.toString());
                                    String story = "";
                                    try {
                                        story = feed_object.getString("story");

                                    } catch (JSONException ex) {
                                        story = feed_object.getString("message");

                                    }
                                    String created_time = feed_object.getString("created_time");
                                    String id = feed_object.getString("id");
                                    FeedClass mystory = new FeedClass(story, created_time, id);
                                    mylist.add(mystory);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getActivity(), "no response", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        ).executeAsync();
        swipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mylist.clear();
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getError() == null) {

                            Log.v("LoginActivity", response.toString());
                            JSONObject jsonObject = response.getJSONObject();
                            try {
                                JSONObject feedmasterobject = jsonObject.getJSONObject("feed");
                                JSONArray data_feed = feedmasterobject.getJSONArray("data");
                                for (int x = 0; x < data_feed.length(); x++) {
                                    JSONObject feed_object = data_feed.getJSONObject(x);
                                    //Log.e("....",feed_object.toString());
                                    String story = "";
                                    try {
                                        story = feed_object.getString("story");

                                    } catch (JSONException ex) {
                                        story = feed_object.getString("message");

                                    }
                                    String created_time = feed_object.getString("created_time");
                                    String id = feed_object.getString("id");
                                    FeedClass mystory = new FeedClass(story, created_time, id);
                                    mylist.add(mystory);
                                    adapter.notifyDataSetChanged();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getActivity(), "no response", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        ).executeAsync();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        adapter = new MyRecyclerViewAdapter(getActivity(), mylist);
        //layoutManager=new LinearLayoutManager(getActivity());
        stagManager = new StaggeredGridLayoutManager(2, 1);


    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "Friendlist is selected", Toast.LENGTH_LONG).show();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(stagManager);


    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
}
