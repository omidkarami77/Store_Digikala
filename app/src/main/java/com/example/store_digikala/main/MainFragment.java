package com.example.Store_Digikala.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.Store_Digikala.informaion.ProductInformationActivity;
import com.example.Store_Digikala.R;
import com.example.Store_Digikala.model.Products;
import com.example.Store_Digikala.navigation.*;
import com.example.Store_Digikala.network.Api;
import com.example.Store_Digikala.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private FrameLayout frameLayout;
    private LottieAnimationView lottieAnimationView;

    private RecyclerView mRecyclerViewNewest;
    private RecyclerView mRecyclerViewPopular;
    private RecyclerView mRecyclerViewMostSeler;
    private ProductsAdapter mAdapter;
    private ViewPager slider;
    private TextView mNewestProduct;
    private TextView mPopular;
    private TextView mMostSeller;
    private ImageView imageView;
    private ProgressDialog mProgressDialog;
    private ImageView mImageView;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setMessage("loading");
        mProgressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.cancel();
            }
        }, 3500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findItem(view);
        recyclerLayout();
        addItemDecoration();
        mNewestProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = NavigationItemActivity.NavigationItemIntent(getActivity(), 3);
                startActivity(intent2);
            }
        });

        mPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NavigationItemActivity.NavigationItemIntent(getActivity(), 1);
                startActivity(intent);
            }
        });

        mMostSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = NavigationItemActivity.NavigationItemIntent(getActivity(), 2);
                startActivity(intent1);
            }
        });


        Picasso.get().load(R.drawable.indicator6).fit().centerCrop().into(imageView);
        Picasso.get().load(R.drawable.indicatre6).fit().centerCrop(1).into(mImageView);

        slider = view.findViewById(R.id.viewpager_main);
        ViewPagerAdapater adapter = new ViewPagerAdapater(getActivity());
        slider.setAdapter(adapter);

        recyclerHandler();


        return view;

    }

    private void recyclerHandler() {
        getApi().getProductList().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productsList = response.body();
                    mAdapter = new ProductsAdapter(productsList);
                    mRecyclerViewNewest.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getActivity(), "GetProductList is Fail", Toast.LENGTH_SHORT).show();
            }
        });

        getApi().getPopularity().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productsList = response.body();
                    mAdapter = new ProductsAdapter(productsList);
                    mRecyclerViewPopular.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getActivity(), "GetPopularity is Fail", Toast.LENGTH_SHORT).show();
            }
        });

        getApi().getTBestSelerList().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productsList = response.body();
                    mAdapter = new ProductsAdapter(productsList);
                    mRecyclerViewMostSeler.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getActivity(), "GetTBestSelerList is Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItemDecoration() {
//        mRecyclerViewPopular.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
//        mRecyclerViewMostSeler.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
//        mRecyclerViewNewest.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
    }

    private void recyclerLayout() {
        LinearLayoutManager layoutManagerNewest
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewNewest.setLayoutManager(layoutManagerNewest);

        LinearLayoutManager layoutManagerMostSeler
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewMostSeler.setLayoutManager(layoutManagerMostSeler);

        LinearLayoutManager layoutManagerPopular
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewPopular.setLayoutManager(layoutManagerPopular);
    }

    private void findItem(View view) {
        mRecyclerViewNewest = view.findViewById(R.id.recycler_newest);
        mRecyclerViewMostSeler = view.findViewById(R.id.recycler_mostSales);
        mRecyclerViewPopular = view.findViewById(R.id.recycler_popular);
        slider = view.findViewById(R.id.viewpager_main);
        imageView = view.findViewById(R.id.image_view_fragmentmain);
        mMostSeller = view.findViewById(R.id.list_complete_MostSales);
        mPopular = view.findViewById(R.id.list_complete_Popular);
        mNewestProduct = view.findViewById(R.id.list_complete_newestProduct);
        mImageView=view.findViewById(R.id.image_view_frag_main);
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

    public class ViewPagerAdapater extends PagerAdapter {

        private Context context;
        private int[] GalImages = new int[]{
                R.drawable.indicator1,
                R.drawable.indicator10,
                R.drawable.ini5,
                R.drawable.indicator8,
                R.drawable.defulat,
                R.drawable.ini4
        };

        ViewPagerAdapater(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return GalImages.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((ImageView) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            Picasso.get().load(GalImages[position]).fit().centerCrop().into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((ImageView) object);

        }
    }
}
