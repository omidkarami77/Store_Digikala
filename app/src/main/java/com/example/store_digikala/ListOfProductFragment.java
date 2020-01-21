package com.example.store_digikala;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.store_digikala.model.Products;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfProductFragment extends Fragment {

    private static final String ARG_GALLERY = "requestItemProduct";
    private RecyclerView mRecyclerView;
 //   private ProductAdapter mAdapter;

    private int requsetCode;

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
        View view =  inflater.inflate(R.layout.fragment_list_of_product, container, false);
 mRecyclerView=view.findViewById(R.id.recycler_navigarion_item_product);
 if (requsetCode==1){
     RetrofitClientInstance.getRetrofitInstance().create(Api.class).getPopularity().enqueue(new Callback<List<Products>>() {
         @Override
         public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
           if (response.isSuccessful()){
               List<Products>productsList=response.body();
           }
         }

         @Override
         public void onFailure(Call<List<Products>> call, Throwable t) {

         }
     });
 }
    }

}
