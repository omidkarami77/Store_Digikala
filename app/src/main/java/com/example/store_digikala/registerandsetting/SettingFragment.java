package com.example.Store_Digikala.registerandsetting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Store_Digikala.R;
import com.example.Store_Digikala.service.PollService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private EditText mEditTextTime;
    private Button mButtonSet;
    private Spinner mSpinnerSettingNotification;
    private ArrayAdapter<CharSequence> arrayAdapterTime;
    int timeSpinner;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        findItem(view);
        arrayAdapterTime = ArrayAdapter.createFromResource(getActivity(), R.array.setting_spinner_notification_time,
                R.layout.support_simple_spinner_dropdown_item);
        arrayAdapterTime = ArrayAdapter.createFromResource(getActivity(), R.array.setting_spinner_notification_time, R.layout.support_simple_spinner_dropdown_item);
        mSpinnerSettingNotification.setAdapter(arrayAdapterTime);
//        PollService.setServiceAlarm(getContext(), true, timeChoose);
        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextTime.getText().length() != 0) {
                    int timeEditText = Integer.parseInt(mEditTextTime.getText().toString());
                    PollService.setServiceAlarm(getActivity(), true, timeEditText);
                    Toast.makeText(getActivity(), timeEditText + "", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else if (mEditTextTime.getText().length() == 0) {
                    PollService.setServiceAlarm(getActivity(), true, timeSpinner);
                    Toast.makeText(getActivity(), timeSpinner + "", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "your time is not set", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSpinnerSettingNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        timeSpinner = 3;
                        return;
                    case 3:
                        timeSpinner = 5;
                        return;
                    case 4:
                        timeSpinner = 8;
                        return;
                    case 5:
                        timeSpinner = 12;
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;

    }

    private void findItem(View view) {
        mEditTextTime = view.findViewById(R.id.edit_text_setting_notification);
        mButtonSet = view.findViewById(R.id.button_setting_notification);
        mSpinnerSettingNotification = view.findViewById(R.id.spinner_setting_notification);
    }


}
