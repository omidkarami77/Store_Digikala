package com.example.store_digikala;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfProductFragment extends Fragment {
    private int requsetCode;

    private static final String ARG_GALLERY = "requestItemProduct";
    private RecyclerView mRecyclerView;
 //   private ProductAdapter mAdapter;


    public ListOfProductFragment() {
        // Required empty public constructor
    }


    public static ListOfProductFragment newInstance(int requestCode) {
        Bundle args = new Bundle();

        ListOfProductFragment fragment = new ListOfProductFragment();
        fragment.setArguments(args);
        args.putInt(ARG_GALLERY, requestCode);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_product, container, false);
    }

}
