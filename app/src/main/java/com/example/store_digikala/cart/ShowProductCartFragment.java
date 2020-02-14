package com.example.Store_Digikala.cart;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.model.Products;
import com.example.Store_Digikala.modeldata.Cart;
import com.example.Store_Digikala.modeldata.CartLab;
import com.example.Store_Digikala.network.Api;
import com.example.Store_Digikala.network.RetrofitClientInstance;
import com.example.Store_Digikala.prefs.UserPrefrences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductCartFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private List<Products> productsList = new ArrayList<>();
    private Toolbar mToolbar;
    private TextView mTextviewFinalize;
    private TextView mTextViewPrice;


    public static ShowProductCartFragment newInstance() {

        Bundle args = new Bundle();

        ShowProductCartFragment fragment = new ShowProductCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShowProductCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_product_show_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_show_cart);
        mTextviewFinalize = view.findViewById(R.id.text_view_finalize_your_purchase);
        mTextViewPrice = view.findViewById(R.id.total_price_cart_list);
        if (CartLab.getInstance().getAllPrice() != 0) {
//            int price=CartLab.getInstance().getAllPrice();
            float price=CartLab.getInstance().getAllPrice();
            int allPrice= (int) price;
            mTextViewPrice.setText(allPrice + "");
        } else {
            mTextViewPrice.setText(0 + "");
        }
        Toast.makeText(getActivity(), CartLab.getInstance().getCarts().size() + "", Toast.LENGTH_SHORT).show();
        mAdapter = new CartAdapter(CartLab.getInstance().getCarts());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mTextviewFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPrefrences.getPrefCustomerId(getActivity())==0){
                    Toast.makeText(getActivity(), "Please First Sign Up", Toast.LENGTH_SHORT).show();
                }else {
                    if (CartLab.getInstance().getAllPrice() != 0) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.fram_layout_main, FinalizePostProductFragment.newInstance()).commit();
                        Intent intent= FinalizePostProductActivity.newIntent(getActivity());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "nashod haji", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private class cartHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView mName;
        private TextView mPrice;
        private Products mProducts;
        private Button mButtonDelete;
        private Cart mCart;


        public cartHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_cart_item_product);
            mName = itemView.findViewById(R.id.text_view_title_product_cart);
            mPrice = itemView.findViewById(R.id.totoal_price_text_view_cartItem);
            mButtonDelete = itemView.findViewById(R.id.button_delete_from_cart);


        }


        public void bind(final Cart cart) {
            mCart = cart;
            Integer mProductId = cart.getMProductId();
            RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProduct(mProductId).
                    enqueue(new Callback<Products>() {
                        @Override
                        public void onResponse(Call<Products> call, Response<Products> response) {
                            if (response.isSuccessful()) {
                                Products product = response.body();
                                Picasso.get().load(product.getImages().get(0).getPath()).into(imageView);
                                mPrice.setText(product.getPrice());
                                mName.setText(product.getName());

                            }

                        }

                        @Override
                        public void onFailure(Call<Products> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartLab.getInstance().deleteCart(cart);
//                    Toast.makeText(getActivity(), "The Product Delete Frome Your Cart", Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    mAdapter = new CartAdapter(CartLab.getInstance().getCarts());
                    mRecyclerView.setAdapter(mAdapter);
                    mTextViewPrice.setText(CartLab.getInstance().getAllPrice()+"");

                }
            });
        }
    }

    private class CartAdapter extends RecyclerView.Adapter<cartHolder> {
        public CartAdapter(List<Cart> mCarts) {
            this.mCarts = mCarts;
        }

        private List<Cart> mCarts;

        public List<Cart> getmCarts() {
            return mCarts;
        }


        public void setmCarts(List<Cart> mCarts) {
            this.mCarts = mCarts;
        }


        @NonNull
        @Override
        public cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cart_product, parent, false);
            return new cartHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull cartHolder holder, int position) {
            holder.bind(mCarts.get(position));
        }

        @Override
        public int getItemCount() {
            return mCarts.size();
        }
    }


}
