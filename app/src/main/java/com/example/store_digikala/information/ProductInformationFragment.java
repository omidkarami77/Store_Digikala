package com.example.store_digikala.information;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_digikala.R;
import com.example.store_digikala.comment.AddCommentActivity;
import com.example.store_digikala.model.Images;
import com.example.store_digikala.model.Products;
import com.example.store_digikala.model.modeldata.Cart;
import com.example.store_digikala.model.modeldata.CartLab;
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
public class ProductInformationFragment extends Fragment {




    private static String ARG_ID_PRODUCT = "productID";
    private int ID;


    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private imagesAdapter mAdapter;
    private TextView mTitle;
    private TextView mPrice;
    private TextView mTotalSales;
    private RatingBar mRatingBar;
    private TextView mDescription;
    private Toolbar mToolbar;
    private Button mButtonAddToCart;
    private Products mProducts;
    private  TextView showComments;
    private TextView addComment;


    public ProductInformationFragment() {
        // Required empty public constructor
    }


    private void findItem(View view) {
        mRecyclerView = view.findViewById(R.id.image_gellery_product_item);
        mTitle = view.findViewById(R.id.item_title_product);
        mPrice = view.findViewById(R.id.item_price_product);
        mTotalSales = view.findViewById(R.id.total_sales_item_product);
        mRatingBar = view.findViewById(R.id.ratingBar_item_product);
        mDescription = view.findViewById(R.id.item_description_product);
        mToolbar = view.findViewById(R.id.toolbar_information);
        mButtonAddToCart = view.findViewById(R.id.button_add_to_cart);
        showComments=view.findViewById(R.id.show_comment_text_view);
        addComment=view.findViewById(R.id.add_comment_text_view);
    }

    public static ProductInformationFragment newInstance(int id) {

        Bundle args = new Bundle();

        ProductInformationFragment fragment = new ProductInformationFragment();
        args.putInt(ARG_ID_PRODUCT, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ID = getArguments().getInt(ARG_ID_PRODUCT);
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setMessage("loading");
        mProgressDialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.cancel();
            }
        },3000);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_information, container, false);
        findItem(view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProduct(ID).enqueue(new Callback<Products>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    Products products = response.body();
                    mProducts=products;
                    mTitle.setText(products.getName());
                    mPrice.setText(getString(R.string.price) + products.getPrice());
                    mTotalSales.setText(getString(R.string.rating) + products.getTotalSales() + "");
                    mDescription.setText(getString(R.string.description) + products.getDescription());
                    mRatingBar.setRating(Float.parseFloat(products.getAverage_rating()));
                    LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    mAdapter = new imagesAdapter(products.getImages());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getActivity(), "از اتصال اینترنت خود اطمینان حاصل فرمایید", Toast.LENGTH_SHORT).show();
            }
        });


        mButtonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart.setMProductCount(1);
                cart.setMProductId(ID);
                cart.setMPrice(Float.valueOf(mProducts.getPrice()));
                if (CartLab.getInstance().checkCart(ID)==null){
                    CartLab.getInstance().addCart(cart);
                    Toast.makeText(getActivity(), "اضافه شد", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "این محصول قبلا ذخیره شده است.", Toast.LENGTH_SHORT).show();
                }

            }

        });

        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= CommentActivity.newIntent(getActivity(),mProducts.getId());
                startActivity(intent);
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= AddCommentActivity.newIntent(getActivity(),ID);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.product_information_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_item_information:
                Toast.makeText(getActivity(), "ProductInformationFragment", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }

    private class imagesHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public imagesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item_information_recycler);

        }

        public void bind(String image) {
            if (image.length() != 0) {
                Picasso.get().load(image).into(imageView);

            }

        }
    }

    private class imagesAdapter extends RecyclerView.Adapter<imagesHolder> {
        private List<Images> imagesList;

        public void setImagesList(List<Images> imagesList) {
            this.imagesList = imagesList;
        }

        public imagesAdapter(List<Images> imagesList) {
            this.imagesList = imagesList;
        }

        @NonNull
        @Override
        public imagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.image_item_information_recycler, parent, false);
            return new imagesHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull imagesHolder holder, int position) {
            Images image = imagesList.get(position);
            holder.bind(image.getPath());

        }

        @Override
        public int getItemCount() {
            return imagesList.size();
        }
    }




}
