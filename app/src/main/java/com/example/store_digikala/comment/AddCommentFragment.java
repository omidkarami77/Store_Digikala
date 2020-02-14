package com.example.store_digikala.comment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_digikala.R;
import com.example.store_digikala.model.Customer;
import com.example.store_digikala.navigation.NavigationItemActivity;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;
import com.example.store_digikala.pref.UserPrefrences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCommentFragment extends Fragment {

    private static final String TAG_ID = "product_id";
    private EditText mUsername;
    private EditText mEmail;
    private EditText mComment;
    private Button mCommetbtn;
    private int mIdProduct;
    private TextView mSignUp;

    public static AddCommentFragment newInstance(int id) {

        Bundle args = new Bundle();

        AddCommentFragment fragment = new AddCommentFragment();
        args.putInt(TAG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public AddCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdProduct = getArguments().getInt(TAG_ID);

    }

    private void findItem(View view) {
        mUsername = view.findViewById(R.id.username_add_comment_frame);
        mComment = view.findViewById(R.id.comment_add_comment_frame);
        mCommetbtn = view.findViewById(R.id.button_add_comment_frame);
        mSignUp = view.findViewById(R.id.sign_up_text_add_comment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_comment, container, false);
        findItem(view);

        mCommetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserPrefrences.getPrefCustomerId(getActivity()) == 0) {
                    Toast.makeText(getActivity(), "You Are Not Register Please First sign up  ", Toast.LENGTH_SHORT).show();
                } else {
                    RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendNewReview(mIdProduct,
                            mComment.getText().toString(), UserPrefrences.getPrefFirstName(getActivity()),
                            UserPrefrences.getPrefUserEmail(getActivity())).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful()) {
                                Log.e("okkTag", response.message());
                                Toast.makeText(getActivity(), "your comment register", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            } else {
                                Log.e("notOk", response.message());
                                Toast.makeText(getActivity(), "lotfan ba deghat por kon haji", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            Toast.makeText(getActivity(), "be fanaaaaaaa  raft", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }


        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NavigationItemActivity.NavigationIntentRegister(getActivity(), 5);
                startActivity(intent);
            }
        });


        return view;
    }


}
