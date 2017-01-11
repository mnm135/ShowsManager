package com.example.emil.showsmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.TVShow;
import com.example.emil.showsmanager.User;
import com.example.emil.showsmanager.adapters.SearchResultsAdapter;
import com.example.emil.showsmanager.adapters.SubscribedShowsAdapter;
import com.example.emil.showsmanager.models.Show;
import com.example.emil.showsmanager.models.ShowsListResponse;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class SubscribedShowsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;

    String userId;

    private DatabaseReference mDatabase;
    FirebaseUser user;
    List<SubscribedShow> showList = new ArrayList<>();


    public SubscribedShowsFragment() {
        // Required empty public constructor
    }

    public static SubscribedShowsFragment newInstance(String param1, String param2) {
        SubscribedShowsFragment fragment = new SubscribedShowsFragment();
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
        View view = inflater.inflate(R.layout.fragment_subscribed_shows, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.subscribed_shows_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SubscribedShowsAdapter(showList, R.layout.subscribed_shows_item, getContext());
        mRecyclerView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            // No user is signed in
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //List<Show> showList = new ArrayList<Show>();
        System.out.println("DUPA");

        mDatabase.child("users").child(userId).child("shows").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                showList.clear();

                for (DataSnapshot show : snapshot.getChildren()) {
                    SubscribedShow show1 = show.getValue(SubscribedShow.class);
                    showList.add(show1);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (SubscribedShow show : showList ) {
            System.out.println("xx");
            System.out.print(show.getName());
        }






        return view;
    }
}

















