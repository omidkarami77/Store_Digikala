package com.example.store_digikala.registerandsetting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.store_digikala.R;
import com.example.store_digikala.UserPrefrences;
import com.example.store_digikala.model.Customer;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private Button mRegisterBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText name = view.findViewById(R.id.add_user_first_name);
        final EditText lastName = view.findViewById(R.id.add_user_last_name);
        final EditText userName = view.findViewById(R.id.add_user_name);
        final EditText email = view.findViewById(R.id.add_user_email);
        final EditText password = view.findViewById(R.id.add_user_password);
        mRegisterBtn=view.findViewById(R.id.register_btn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0
                        && lastName.getText().toString().length() > 0
                        && userName.getText().toString().length() > 0
                        && email.getText().toString().length() > 0
                        && password.getText().toString().length() > 0) {
                    UserPrefrences.setPrefFirstName(getActivity(), name.getText().toString());
                    UserPrefrences.setPrefLastName(getActivity(), lastName.getText().toString());
                    UserPrefrences.setPrefUserName(getActivity(), userName.getText().toString());
                    UserPrefrences.setPrefUserEmail(getActivity(), email.getText().toString());
                    UserPrefrences.setPrefUserPassword(getActivity(), password.getText().toString());

                    registerCustomer(name.getText().toString(),lastName.getText().toString(),
                            userName.getText().toString(),email.getText().toString());


                } else {
                    Toast.makeText(getActivity(), "You should fill all blanks", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }
    private void registerCustomer(String name,String lastName,String userName,String email) {
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .createCustomer(name,lastName,
                        userName,email).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()){
                    int id=response.body().getId();
                    Log.e("omidkarami", "onResponse: "+UserPrefrences.getPrefCustomerId(getActivity()));
                    UserPrefrences.setPrefCustomerId(getActivity(),id);
                    Toast.makeText(getActivity(), UserPrefrences.getPrefCustomerId(getActivity())+"", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }else {
                    Log.e("omidkarami2", "onResponse:sik");
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

    }
}