package com.suicune.poketools.view.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link IvBreedingCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class IvBreedingCalcFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IvBreedingCalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IvBreedingCalcFragment newInstance() {
        IvBreedingCalcFragment fragment = new IvBreedingCalcFragment();
        return fragment;
    }
    public IvBreedingCalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iv_breeding_calc, container, false);
    }

}
