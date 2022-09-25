package com.example.foodutan;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ShortcutFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ShortcutFragment() {
        // Required empty public constructor
    }

    public static ShortcutFragment newInstance(String param1, String param2) {
        ShortcutFragment fragment = new ShortcutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shortcut, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ImageButton homeBtn = view.findViewById(R.id.homeBtn);
        ImageButton checkoutBtn = view.findViewById(R.id.checkoutBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("The actvitiy: " + getActivity());
                Intent intent = new Intent(getActivity(),MainActivity.class);

                /*intent.putExtra("name",restaurantName[i]);
                intent.putExtra("picture",drawableId[i]);*/ //willl add anything if required

                startActivity(intent);
            }
        });


        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut checkout = new checkOut();
                FragmentManager ft = getChildFragmentManager();
                FragmentTransaction transaction = ft.beginTransaction();
                transaction.replace(R.id.register_container, checkout);
                transaction.commit();
            }
        });

    }
}