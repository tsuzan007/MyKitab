package com.sujan.mykitaab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sujan.mykitaab.HelperClass.MyRecyclerViewAdapter;
import com.sujan.mykitaab.R;

/**
 * Created by macbookpro on 4/16/17.
 */

public class Friendlistfragment extends Fragment {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.friendlistfragment,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.myrecyclerview);
         adapter=new MyRecyclerViewAdapter(getActivity());
        layoutManager=new LinearLayoutManager(getActivity());
        Toast.makeText(getActivity(),"Friendlist is selected",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
}
