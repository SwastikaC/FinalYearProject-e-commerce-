package com.example.lookclassy.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.lookclassy.More.AboutUsActivity;
import com.example.lookclassy.More.ContactUsActivity;
import com.example.lookclassy.More.TermsAndConditionActivity;
import com.example.lookclassy.R;
import com.example.lookclassy.admin.AdminActivity;
import com.example.lookclassy.More.ProfileActivity;
import com.example.lookclassy.checkout.orderComplete.OrderActivity;
import com.example.lookclassy.checkout.orderComplete.OrderHistoryDetailsActivity;
import com.example.lookclassy.userAccount.UserAccountActivity;
import com.example.lookclassy.utils.SharedPrefUtils;

public class MoreFragment extends Fragment {
    TextView logOutTV;
    TextView profileTV;
    TextView adminAreaTV;
    TextView policiesTV, OrderhistoryTV;
    TextView aboutusTV, contactusTV;
    Window window;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutTV = view.findViewById(R.id.logOutTV);
        profileTV = view.findViewById(R.id.profileTV);
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        policiesTV = view.findViewById(R.id.policiesTV);
        aboutusTV = view.findViewById(R.id.aboutusTV);
        OrderhistoryTV = view.findViewById(R.id.OrderhistoryTV);

        orderhistoryOnClick();
        contactusTV = view.findViewById(R.id.contactusTV);
        contactusOnClick();
        checkAdmin();
        setClickListeners();
        ProfileOnClick();
        PoliciesOnClick();

    }

    private void orderhistoryOnClick() {
        OrderhistoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void contactusOnClick() {

        contactusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void PoliciesOnClick() {
        policiesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), TermsAndConditionActivity.class);
                startActivity(intent);
            }
        });
        aboutusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkAdmin() {
        boolean is_staff = SharedPrefUtils.getBool(getActivity(), getString(R.string.staff_key), false);
        if (is_staff)
            adminAreaTV.setVisibility(View.VISIBLE);

    }

    private void ProfileOnClick() {
        profileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);

            }
        });
    }
    private void setClickListeners() {
        logOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtils.clear(getContext());
                Intent userAccount = new Intent(getContext(), UserAccountActivity.class);
                startActivity(userAccount);
                getActivity().finish();
            }
        });
        adminAreaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            }
        });


    }
}