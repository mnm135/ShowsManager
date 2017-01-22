package com.example.emil.showsmanager.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.LoadingDialog;
import com.example.emil.showsmanager.activities.SearchResultDetailsActivity;
import com.example.emil.showsmanager.adapters.SubscribedShowsGridAdapter;
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


    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;

    String userId;

    private DatabaseReference mDatabase;
    FirebaseUser user;
    List<SubscribedShow> showList = new ArrayList<>();

    GridView gridView;


    public SubscribedShowsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscribed_shows, container, false);



        gridView = (GridView) view.findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String showId = showList.get(position).getId();

                Intent intent = new Intent(v.getContext(), SearchResultDetailsActivity.class);
                intent.putExtra("showId", showId);
                v.getContext().startActivity(intent);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            // No user is signed in
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userId).child("shows").addValueEventListener(new ValueEventListener() {
            ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(getActivity(),
                    getResources().getString(R.string.loading_dialog_msg));
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                showList.clear();

                for (DataSnapshot showDataSnapshot : snapshot.getChildren()) {
                    SubscribedShow subscribedShow = showDataSnapshot.getValue(SubscribedShow.class);
                    showList.add(subscribedShow);
                }
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                gridView.setAdapter(new SubscribedShowsGridAdapter(getActivity(), showList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
        return view;
    }
}

















