package com.example.Store_Digikala.cart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.map.MapActivity;
import com.example.Store_Digikala.model.address.Address;
import com.example.Store_Digikala.model.address.AddressLab;
import com.example.Store_Digikala.model.orderModel.Biling;
import com.example.Store_Digikala.model.orderModel.Coupon;
import com.example.Store_Digikala.model.orderModel.Customer;
import com.example.Store_Digikala.model.orderModel.Order;
import com.example.Store_Digikala.model.orderModel.OrderJsonBody;
import com.example.Store_Digikala.modeldata.CartLab;
import com.example.Store_Digikala.network.Api;
import com.example.Store_Digikala.network.RetrofitClientInstance;
import com.example.Store_Digikala.prefs.UserPrefrences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalizePostProductFragment extends Fragment {

    private static final int REQ_MAP = 1;
    private static final String EXTRA_RESULT_CHOOSE = "com.example.Store_Digikala.fragmentfolder.chooseproduct";
    private EditText mCountry;
    private EditText mCity;
    private EditText mPostCode;
    private EditText mPhoneNumber;
    private EditText mAddress1;
    private EditText mAddress2;
    private Button mSubmitBtn;
    private Button mButtonMap;
    Address mAddressForSaved;
    private EditText mCouponEditText;
    private List<Coupon> couponList=new ArrayList<>();

    private void findItem(View view) {
        mCountry = view.findViewById(R.id.edit_name);
        mCity = view.findViewById(R.id.edit_lastname);
        mPostCode = view.findViewById(R.id.edit_email);
        mPhoneNumber = view.findViewById(R.id.edit_phone);
        mAddress1 = view.findViewById(R.id.address1_edit);
        mAddress2 = view.findViewById(R.id.address2_edit);
        mSubmitBtn = view.findViewById(R.id.submit_btn);
        mButtonMap = view.findViewById(R.id.map_btn);
        mCouponEditText = view.findViewById(R.id.coupon_edit_text);
    }

    public static FinalizePostProductFragment newInstance() {

        Bundle args = new Bundle();

        FinalizePostProductFragment fragment = new FinalizePostProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FinalizePostProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_costomer, container, false);

        findItem(view);

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getCoupons().enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (response.isSuccessful()) {
                    List<Coupon> coupons = response.body();
                    couponList = coupons;
                }
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {
                Toast.makeText(getActivity(), "your coupon is null :( ", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MapActivity.newIntent(getActivity());
                startActivity(intent);

            }
        });


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = UserPrefrences.getPrefUserName(getActivity());
                String lastname = UserPrefrences.getPrefLastName(getActivity());
                String email = UserPrefrences.getPrefUserEmail(getActivity());
                String country = mCountry.getText().toString();
                String city = mCity.getText().toString();
                String postalcode = mPostCode.getText().toString();
                String address1 = mAddress1.getText().toString();
                String address2 = mAddress2.getText().toString();
                String phone = mPhoneNumber.getText().toString();
                Biling biling = new Biling(name, lastname, address1, address2, postalcode, country, email, phone);

                List<Order> orders = new ArrayList<>();
                List<Integer> savedCart = CartLab.getInstance().getCartsID();
                for (int i = 0; i < savedCart.size(); i++) {
                    Order order = new Order(savedCart.get(i), 1);
                    orders.add(i, order);
                }

                if (couponList.size() > 0) {
                    List<Coupon> mCouponListCustomer = new ArrayList<>();
                    for (int i = 0; i < couponList.size(); i++) {
                        if (mCouponEditText.getText().length() != 0) {
                            if (mCouponEditText.getText().toString() == couponList.get(i).getCode()) {
                                mCouponListCustomer.add(couponList.get(i));
                            }
                        }
                    }

                    OrderJsonBody orderJsonBody = new OrderJsonBody(biling, orders, UserPrefrences.getPrefCustomerId(getActivity()), mCouponListCustomer);

                    RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendOrder(orderJsonBody).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful()) {
                                //Toast.makeText(getContext(), "sucsefull", Toast.LENGTH_SHORT).show();
                                int orderId = response.body().getId();
                                Log.e("holyshet witout coupong", orderId + "orderId");
                                /////order submited
                                Toast.makeText(getContext(), "your order is ok with coupon", Toast.LENGTH_SHORT).show();
                                CartLab.getInstance().deleteAllCart();
                            } else {
                                Toast.makeText(getActivity(), "nashod haji be fana raft", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {

                        }
                    });


                } else {
                    OrderJsonBody orderJsonBody = new OrderJsonBody(biling, orders, UserPrefrences.getPrefCustomerId(getActivity()));

                    RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendOrder(orderJsonBody).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful()) {
                                //Toast.makeText(getContext(), "sucsefull", Toast.LENGTH_SHORT).show();
                                int orderId = response.body().getId();
                                Log.e("holyshet", orderId + "orderId");
                                /////order submited
                                Toast.makeText(getContext(), "your order is ok withot coupon", Toast.LENGTH_SHORT).show();
                                CartLab.getInstance().deleteAllCart();
                            } else {
                                Toast.makeText(getActivity(), "nashod haji", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {

                        }
                    });
                }
            }
//            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQ_MAP) {
            String address1 = (String) data.getSerializableExtra(EXTRA_RESULT_CHOOSE);
            mAddress1.setText(address1);
            mAddressForSaved = AddressLab.getInstance().getAddress(address1);

        }


    }

}
