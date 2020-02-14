package com.example.store_digikala.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_digikala.ProductInformationActivity;
import com.example.store_digikala.R;
import com.example.store_digikala.model.Products;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "com.example.store_digikala.searchresult";
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private String SearchString;
    private ProductAdapter mAdapter;
    private Spinner mSpinnerSort;
    private Spinner mSpineerFilter;
    private ArrayAdapter<CharSequence> arrayAdapterSort;
    private ArrayAdapter<CharSequence> arrayAdapterFilter;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        return intent;
    }

    private void findItem() {
        mToolbar = findViewById(R.id.toolbar_search);
        mRecyclerView = findViewById(R.id.search_recycler);
        mSpineerFilter = findViewById(R.id.filter_spinner);
        mSpinnerSort = findViewById(R.id.sort_spinner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_sort_filter);
        findItem();
        mToolbar.setTitle("TibikalaStore");
        setSupportActionBar(mToolbar);

        arrayAdapterSort = ArrayAdapter.createFromResource(this, R.array.sort_spinner_string,
                R.layout.support_simple_spinner_dropdown_item);
        arrayAdapterFilter = ArrayAdapter.createFromResource(this, R.array.filter_color_spinner_string, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapterSort.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        arrayAdapterFilter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinnerSort.setAdapter(arrayAdapterSort);
        mSpineerFilter.setAdapter(arrayAdapterFilter);
//        Toast.makeText(this, filterSpinner, Toast.LENGTH_LONG).show();
//        mSpinnerSort.get
//         String filterSpinner=mSpineerFilter.getSelectedItem().toString();
        mSpineerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filterSpinner = mSpineerFilter.getSelectedItem().toString();
                Toast.makeText(SearchResultActivity.this, filterSpinner, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_result_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchString = query;

                getApi().getProductWithSearch(SearchString).enqueue(new Callback<List<Products>>() {
                    @Override
                    public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                        if (response.isSuccessful()) {
                            List<Products> productsList = response.body();
                            int productsize = productsList.size();
                            View parentLayout = findViewById(R.id.fragment_sort_search_filter);
                            mAdapter = new ProductAdapter(productsList);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                            if (productsize == 0) {
                                Snackbar.make(parentLayout, " There are no products with this feature ", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(parentLayout, "There are " + productsize + " items", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Products>> call, Throwable t) {
                        View parentLayout = findViewById(R.id.activity_main_store);
                        Snackbar.make(parentLayout, t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return true;
    }

    private Api getApi() {
        return RetrofitClientInstance.getRetrofitInstance().create(Api.class);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_result_search:
                return true;

//            case R.id.search_filter_type:
//                View parentLayout = findViewById(R.id.activity_main_store);
//                Snackbar.make(parentLayout, "Holyyyyyyy sssssssssheeeeeeeeeeeet", Snackbar.LENGTH_LONG).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
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
                    Intent intent = ProductInformationActivity.newInent(SearchResultActivity.this, mProducts.getId());
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
            LayoutInflater inflater = LayoutInflater.from(SearchResultActivity.this);
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
