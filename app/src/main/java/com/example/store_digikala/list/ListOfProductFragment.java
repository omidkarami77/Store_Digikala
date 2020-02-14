package com.example.store_digikala.list;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store_digikala.information.ProductInformationActivity;
import com.example.store_digikala.R;
import com.example.store_digikala.model.Products;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

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
    private ProductAdapter mAdapter;

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
        View view = inflater.inflate(R.layout.fragment_list_of_product, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_navigarion_item_product);
        if (requsetCode == 1) {
            RetrofitClientInstance.getRetrofitInstance().create(Api.class).getPopularity().enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                    if (response.isSuccessful()) {
                        List<Products> productsList = response.body();
                        mAdapter = new ProductAdapter(productsList);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
                }

                @Override
                public void onFailure(Call<List<Products>> call, Throwable t) {

                }
            });
        } else if (requsetCode == 2) {
            RetrofitClientInstance.getRetrofitInstance().create(Api.class).getTBestSelerList().enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                    if (response.isSuccessful()) {
                        List<Products> productsList = response.body();
                        mAdapter = new ProductAdapter(productsList);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                }


                @Override
                public void onFailure(Call<List<Products>> call, Throwable t) {

                }
            });
        } else if (requsetCode == 3) {
            RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProductList().enqueue(new Callback<List<Products>>() {
                @Override
                public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                    if (response.isSuccessful()) {
                        List<Products> productsList = response.body();
                        mAdapter = new ProductAdapter(productsList);
                        Log.e("TAG", productsList.size() + "");
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                }

                @Override
                public void onFailure(Call<List<Products>> call, Throwable t) {

                }
            });
        }
        return view;
    }

    private class ProductHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextViewName;
        private TextView mTextViewPrice;
        private Products mProducts;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_search_result);
            mTextViewName = itemView.findViewById(R.id.text_name_product_search);
            mTextViewPrice = itemView.findViewById(R.id.text_price_search_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInformationActivity.newInent(getActivity(), mProducts.getId());
                    startActivity(intent);
                }
            });
        }

        private void bind(Products products) {
            mProducts = products;
            mTextViewName.setText(products.getName());
            mTextViewPrice.setText(products.getPrice());
            if (mProducts.getImages() != null && mProducts.getImages().size() > 0) {
                Picasso.get().load(mProducts.getImages().get(0).getPath()).fit().centerCrop().into(mImageView);
            }
        }

    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
        private List<Products> productsList;

        public void setProductsList(List<Products> productsList) {
            this.productsList = productsList;
        }

        public List<Products> getProductsList() {
            return productsList;
        }

        private ProductAdapter(List<Products> productsList) {
            this.productsList = productsList;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.search_item_product, parent, false);
            return new ProductHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            Products products = productsList.get(position);
            holder.bind(products);
        }

        @Override
        public int getItemCount() {
            return productsList.size();
        }
    }

}

