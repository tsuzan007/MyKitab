package com.sujan.mykitaab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujan.mykitaab.R;

/**
 * Created by macbookpro on 4/18/17.
 */

public class CameraPreviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.photopreview_fragment,container,false);
        return view;
    }
}
