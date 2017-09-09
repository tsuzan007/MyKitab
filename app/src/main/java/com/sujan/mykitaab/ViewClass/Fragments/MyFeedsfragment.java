package com.sujan.mykitaab.ViewClass.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujan.mykitaab.POJOClasses.FeedClass;
import com.sujan.mykitaab.POJOClasses.MessageEvent;
import com.sujan.mykitaab.POJOClasses.User_WithFacebook;
import com.sujan.mykitaab.Presenter.MVPContracts;
import com.sujan.mykitaab.Presenter.MyKitabPresenter;
import com.sujan.mykitaab.ViewClass.HelperClass.MyRecyclerViewAdapter;
import com.sujan.mykitaab.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 4/16/17.
 */

public class MyFeedsfragment extends Fragment implements MVPContracts.PublishToView {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    StaggeredGridLayoutManager stagManager;
    SwipeRefreshLayout swipeRefreshLayout;
    List<FeedClass> mylist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    MyKitabPresenter myKitabPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mylist.clear();
        myKitabPresenter = new MyKitabPresenter(this);
        myKitabPresenter.onLoadFeeds();
    }

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


    @Subscribe
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
        myKitabPresenter.onLoadFeeds();
        swipeRefreshLayout.setRefreshing(false);


    }


    @Override
    public void onStart() {
        super.onStart();
        myKitabPresenter.start();
        EventBus.getDefault().register(this);
        adapter = new MyRecyclerViewAdapter(getActivity(), mylist);
        stagManager = new StaggeredGridLayoutManager(2, 1);


    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);



    }

    @Override
    public void onStop() {
        super.onStop();
        myKitabPresenter.onStop();
        EventBus.getDefault().unregister(this);


    }

    @Override
    public void publish_userInfo(User_WithFacebook user) {

    }

    @Override
    public void publish_feeds(FeedClass feedClass) {
        mylist.add(feedClass);
        adapter.notifyDataSetChanged();

    }


}
