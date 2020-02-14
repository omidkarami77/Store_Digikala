package com.example.Store_Digikala.categories;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Store_Digikala.informaion.ProductInformationActivity;
import com.example.Store_Digikala.R;
import com.example.Store_Digikala.model.Products;
import com.example.Store_Digikala.network.Api;
import com.example.Store_Digikala.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesProductFragment extends Fragment {
    private static final String ARG_CAT="categories_id";
    private static final String TAG="tag";
    private RecyclerView mRecyclerView;
    private int ID;
    private ProductsAdapter mAdapter;
    public CategoriesProductFragment() {
        // Required empty public constructor
    }


    public static CategoriesProductFragment newInstance(int id) {

        Bundle args = new Bundle();

        CategoriesProductFragment fragment = new CategoriesProductFragment();
        fragment.setArguments(args);
        args.putInt(ARG_CAT,id);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ID=getArguments().getInt(ARG_CAT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_product, container, false);
        mRecyclerView=view.findViewById(R.id.recycler_gallery_list_product);
        LinearLayoutManager layoutManagerMostSeler
                = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManagerMostSeler);
        getApi().getProductsWithCatId(ID+"").enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()){
                    List<Products> productsList=response.body();
                    if (productsList.size()==0){
                        Toast.makeText(getActivity(), "This List is null Please cheack other Categories", Toast.LENGTH_SHORT).show();
                    }else {
                        mAdapter=new ProductsAdapter(productsList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getActivity(), "categories list is fail", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private Api getApi() {
        return RetrofitClientInstance.getRetrofitInstance().create(Api.class);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mProductImageView;
        private TextView mProductNameTextView;
        private TextView mProductPriceTextView;
        private Products mProducts;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImageView = itemView.findViewById(R.id.product_image_view);
            mProductNameTextView = itemView.findViewById(R.id.product_name_text_view);
            mProductPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInformationActivity.newInent(getActivity(), mProducts.getId());
                    startActivity(intent);
                }
            });

        }

        public void bind(Products products) {
            mProducts = products;
            mProductNameTextView.setText(products.getName());
            mProductPriceTextView.setText(products.getPrice());
            if (mProducts.getImages() != null && mProducts.getImages().size() > 0) {
                Picasso.get().load(mProducts.getImages().get(0).getPath()).fit().centerCrop().into(mProductImageView);

            }

        }
    }

    private class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
        private List<Products> mList;

        public ProductsAdapter(List<Products> list) {
            mList = list;
        }

        public void setList(List<Products> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).
                    inflate(R.layout.product_item_newlist, parent, false);
            return new ProductsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
            Products products = mList.get(position);
            holder.bind(products);


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }



}
