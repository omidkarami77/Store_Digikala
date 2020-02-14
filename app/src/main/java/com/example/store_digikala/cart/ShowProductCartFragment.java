package com.example.store_digikala.cart;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.store_digikala.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductCartFragment extends Fragment {


    public ShowProductCartFragment() {
        // Required empty public constructor
    }


    public static ShowProductCartFragment newInstance() {

        Bundle args = new Bundle();

        ShowProductCartFragment fragment = new ShowProductCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_product_cart, container, false);
    }

}
