package com.suicune.poketools.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link IvCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class IvCalcFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IvCalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IvCalcFragment newInstance() {
        IvCalcFragment fragment = new IvCalcFragment();
        return fragment;
    }
    public IvCalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iv_calc, container, false);
    }

}
