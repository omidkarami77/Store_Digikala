package com.example.store_digikala.comment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.store_digikala.R;
import com.example.store_digikala.model.Products;
import com.example.store_digikala.model.Review;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    private static final String EXTRA_PRODUCT_ID_REVIEW = "productId.com.example.store_digikala.comment.commentcontroller";
    private RecyclerView mRecyclerview;
    private commentAdapter mAdapter;
    private int product_id;
    private Review mReview;
    private ImageView imageViewdelete;
    private FloatingActionButton floatingActionButton;
    private TextView mTitle;
    private RatingBar ratingBar;
    private Products mProducts;
    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID_REVIEW, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        floatingActionButton = findViewById(R.id.floating_button_comment_activity);
        imageViewdelete = findViewById(R.id.image_view_delete_comment);
        ratingBar=findViewById(R.id.ratingbar_comment);
        mTitle=findViewById(R.id.product_name_text_comment);
        product_id = getIntent().getIntExtra(EXTRA_PRODUCT_ID_REVIEW, 0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCommentActivity.newIntent(CommentActivity.this, product_id);
                startActivity(intent);
            }
        });
        mRecyclerview = findViewById(R.id.recyclerview_comment_review_activity);

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProductReviewsById(product_id).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviewList = response.body();
                    if (reviewList.size() > 0) {
                        mAdapter = new commentAdapter(reviewList);
                        mRecyclerview.setAdapter(mAdapter);
                        mRecyclerview.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getProduct(product_id).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()){
                    Products products=response.body();
                    mProducts=products;
                    ratingBar.setRating(Float.parseFloat(mProducts.getAverage_rating()));
                    LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    mTitle.setText(mProducts.getName());

                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });


    }

    private class commentholder extends RecyclerView.ViewHolder {
        private TextView mTextUserName;
        private TextView mTextComment;

        public commentholder(@NonNull View itemView) {
            super(itemView);
            mTextComment = itemView.findViewById(R.id.comment_itemview_comment);
            mTextUserName = itemView.findViewById(R.id.username_itemview_comment);
        }

        public void bind(Review review) {
            mReview = review;
            mTextUserName.setText(review.getReviewer());
            String cm = review.getReview();
            if (cm.length() > 0) {
                int length = cm.length() - 5;
                cm = cm.substring(3, length);
                mTextComment.setText(cm);
            }


        }
    }

    private class commentAdapter extends RecyclerView.Adapter<commentholder> {
        public List<Review> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<Review> reviewList) {
            this.reviewList = reviewList;
        }

        public commentAdapter(List<Review> reviewList) {
            this.reviewList = reviewList;
        }

        private List<Review> reviewList;

        @NonNull
        @Override
        public commentholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CommentActivity.this).inflate(R.layout.item_comment_layout, parent, false);
            return new commentholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull commentholder holder, int position) {
            holder.bind(reviewList.get(position));
        }

        @Override
        public int getItemCount() {
            return reviewList.size();
        }
    }
}
