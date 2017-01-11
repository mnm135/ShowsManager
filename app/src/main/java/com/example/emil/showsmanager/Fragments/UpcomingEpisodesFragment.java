package com.example.emil.showsmanager.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.adapters.UpcomingEpisodesAdapter;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.ShowDetailsEndPoints;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpcomingEpisodesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //new
    FirebaseUser user;
    String userId;
    private DatabaseReference mDatabase;
    List<SubscribedShow> showList = new ArrayList<SubscribedShow>();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;



    public UpcomingEpisodesFragment() {
    }


    public static UpcomingEpisodesFragment newInstance(String param1, String param2) {
        UpcomingEpisodesFragment fragment = new UpcomingEpisodesFragment();
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
        View view = inflater.inflate(R.layout.fragment_upcoming_episodes, container, false);

        userId = retrieveUserId();

        if (userId != null) {
            showList = getShowList(userId);
        }
        updateTimesToNextEpisodes();


        mRecyclerView = (RecyclerView) view.findViewById(R.id.upcoming_episodes_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UpcomingEpisodesAdapter(showList, R.layout.upcoming_episodes_item, getContext());
        mRecyclerView.setAdapter(adapter);
        return view;

    }

    private void updateTimesToNextEpisodes() {
        for (SubscribedShow show : showList) {
            
            show.setTimeToNextEpisode();
            adapter.notifyDataSetChanged();
        }
    }

    private void updateShow(final String showId, final int position) {
        ShowDetailsEndPoints apiService = ApiClient.getClient().create(ShowDetailsEndPoints.class);
        Call<ShowDetailsWithNextEpisodeResponse> call = apiService.getResponse(showId, "cast", "nextepisode", "seasons");

        call.enqueue(new Callback<ShowDetailsWithNextEpisodeResponse>() {
            @Override
            public void onResponse(Call<ShowDetailsWithNextEpisodeResponse> call,
                                   Response<ShowDetailsWithNextEpisodeResponse> response) {

                String newDate = response.body().getEmbedded().getNextepisode().getAirdate();
                String name = response.body().getName();
                String imageUrl = response.body().getImage().getMedium();

                showList.get(position).setNextEpisodeAirdate(newDate);
                showList.get(position).setTimeToNextEpisode();

                System.out.println("hheheheheh");
                System.out.println("DUPA" + showList.get(position).getDaysToNextEpisode());


                updateShowInFirebase(showId, name, newDate, imageUrl);
                adapter.notifyDataSetChanged();





            }
            @Override
            public void onFailure(Call<ShowDetailsWithNextEpisodeResponse> call, Throwable t) {

            }
        });
    }

    private void updateShowInFirebase(String showId, String name, String newDate, String imageUrl) {

        SubscribedShow show = new SubscribedShow(showId, name, newDate, imageUrl);

        mDatabase.child("users").child(userId).child("shows").child(showId).setValue(show);

        //@TODO cos nie działa xD + ogarnac zeby za kazdym razem sprawdzalo liczbe dni
       // adapter.notifyDataSetChanged();

    }

    private String retrieveUserId() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return null;
        }
    }

    private List<SubscribedShow> getShowList(String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // @TODO check out this approach
        final List<SubscribedShow> showList = new ArrayList<>();
        mDatabase.child("users").child(userId).child("shows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                showList.clear();

                for (DataSnapshot show : snapshot.getChildren()) {
                    SubscribedShow singleShow = show.getValue(SubscribedShow.class);
                    showList.add(singleShow);
                }
                checkShowsForUpdates();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return showList;
    }

    private List<SubscribedShow> checkShowsForUpdates() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
//        List<SubscribedShow> showsToUpdate = new ArrayList<SubscribedShow>();
        System.out.println("getshowslist");

        for (SubscribedShow show : showList) {
            // @TODO add show.getStatus.equals("Running");
            System.out.println("getshowslist for");
            System.out.println(show.getName());
            System.out.println(show.getNextEpisodeAirdate().compareTo(today));

            if (show.getNextEpisodeAirdate().compareTo(today) < 0) {
                //@TODO  add item to list "shows to update", and then call only for those.
                int position = showList.indexOf(show);
                updateShow(show.getId(), position);
                System.out.println("get shows to update");
                System.out.println(show.getName());
            }
        }
        return showList;
    }

}
