package com.example.emil.showsmanager.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.LoadingDialog;
import com.example.emil.showsmanager.activities.SearchResultDetailsActivity;

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
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultMoreDetails extends Fragment {

    String userId;
    String mShowId;
    String mNextEpSeason;
    String mNextEpNumber;
    String mShowName;
    String mNextEpisodeAirdate;
    String mImageUrlMedium;
    String mStatus;
    String mAirtime;
    String mChannel;
    String nextEpisodeId;

    boolean isShowSubscribed = false;

    @BindView(R.id.show_name) TextView showName;
    @BindView(R.id.show_years_first_section) TextView showYears;
    @BindView(R.id.show_description) TextView showDescription;
    @BindView(R.id.show_poster) ImageView showPoster;
    @BindView(R.id.show_status) TextView showStatus;
    @BindView(R.id.show_number_of_season) TextView numberOfSeasons;
    @BindView(R.id.show_number_of_episodes) TextView numberOfEpisodes;
    @BindView(R.id.show_runtime) TextView showRuntime;
    @BindView(R.id.show_airdate) TextView showAirdate;
    @BindView(R.id.show_language) TextView showLanguage;
    @BindView(R.id.show_network) TextView showNetwork;
    @BindView(R.id.show_country) TextView showCountry;
    @BindView(R.id.genres) TextView showGenres;
    @BindView(R.id.imdb_pic) ImageView imdbPicture;
    @BindView(R.id.tvmaze_pic) ImageView tvmazePicture;
    @BindView(R.id.thetvdb_pic) ImageView thetvdbPicture;
    @BindView(R.id.next_episode_bar) TextView nextEpisodeBar;
    @BindView(R.id.fab_subscribe)


    FloatingActionButton fabSubscribe;

    @BindView(R.id.next_episode) LinearLayout nextEpisode;
    @OnClick(R.id.next_episode)
    public void startNextEpisodeFragment(View view) {

        Fragment fragment = new NextEpisodeDetailsFragment();
        Bundle bundle = new Bundle();

        bundle.putString( "showId", mShowId);
        bundle.putString( "seasonNumber", mNextEpSeason);
        bundle.putString( "episodeNumber", mNextEpNumber);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @OnClick(R.id.fab_subscribe)
    public void SnackbarNotification(View view) {
        if (!isShowSubscribed) {
            subscribeShow(userId, mShowId, mShowName, mNextEpisodeAirdate, mImageUrlMedium);
            isShowSubscribed = true;
        } else {
            unsubscribeShow(userId, showId);
            isShowSubscribed = false;
        }
        updateFab();
    }

    HorizontalScrollView scrollView;
    ImageView toolbarImage;
    HorizontalScrollView seasonsScrollView;
    LinearLayout topLinearLayout;
    LinearLayout seasonsLinearLayout;
    String showId;
    FirebaseUser user;

    private DatabaseReference mDatabase;

    public SearchResultMoreDetails() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SearchResultDetailsActivity activity = (SearchResultDetailsActivity) getActivity();
        showId = activity.getShowId();
        View view = inflater.inflate(R.layout.fragment_search_result_more_details, container, false);
        ButterKnife.bind(this, view);

        toolbarImage = (ImageView) view.findViewById(R.id.image_toolbar);
        scrollView = (HorizontalScrollView) view.findViewById(R.id.srollview_cast_gallery);
        seasonsScrollView = (HorizontalScrollView) view.findViewById(R.id.srollview_seasons_gallery);
        topLinearLayout = new LinearLayout(getContext());
        seasonsLinearLayout = new LinearLayout(getContext());

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("users").child(userId).child("shows").hasChild(showId)) {
                    isShowSubscribed = true;
                    System.out.println("subsrybowane");

                } else {
                    isShowSubscribed = false;
                    System.out.println("nie subsrybowane");

                }
                updateFab();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        getShowDetails(showId);

        return view;
    }

    public void updateFab() {
        if (isShowSubscribed) {
            fabSubscribe.setImageResource(R.drawable.ic_clear_black_24dp);
        } else {
            fabSubscribe.setImageResource(R.drawable.ic_add_black_24dp);
        }

    }

    private void unsubscribeShow(String userId, String showId) {
        mDatabase.child("users").child(userId).child("shows").child(showId).removeValue();
    }

    private void subscribeShow(String userId, String id, String name, String nextEpisodeAirdate, String imageUrl) {
        SubscribedShow show = new SubscribedShow(id, name, nextEpisodeAirdate, imageUrl, mStatus, mAirtime,
                mChannel, mNextEpNumber, mNextEpSeason);
        mDatabase.child("users").child(userId).child("shows").child(showId).setValue(show);
    }

    public void getShowDetails(String showId) {
        ShowDetailsEndPoints apiService = ApiClient.getClient().create(ShowDetailsEndPoints.class);
        Call<ShowDetailsWithNextEpisodeResponse> call = apiService.getResponse(showId, "cast", "nextepisode", "seasons");

        final ProgressDialog myDialog= LoadingDialog.showProgressDialog(getActivity(),
                getResources().getString(R.string.loading_dialog_msg));

        call.enqueue(new Callback<ShowDetailsWithNextEpisodeResponse>() {
            @Override
            public void onResponse(Call<ShowDetailsWithNextEpisodeResponse> call, Response<ShowDetailsWithNextEpisodeResponse> response) {
                mShowId = response.body().getId().toString();
                mShowName = response.body().getName();
                mStatus = response.body().getStatus();
                mAirtime = response.body().getSchedule().getTime();
                mChannel = response.body().getNetwork().getName();

                String showDescriptionString = response.body().getSummary();
                String showGenresString = response.body().getGenres().toString();
                String showCountryString = response.body().getNetwork().getCountry().getName();
                String showNetworkString = response.body().getNetwork().getName();
                String showLanguageString = response.body().getLanguage();
                String showAirdateString = response.body().getPremiered();
                String showRuntimeString = response.body().getRuntime().toString();
                String numberOfSeasonsString = String.valueOf(response.body().getEmbedded().getSeasons().size());
                String showStatusString = response.body().getStatus();


                showDescription.setText(showDescriptionString);
                showGenres.setText(showGenresString);
                showCountry.setText(showCountryString);
                showNetwork.setText(showNetworkString);
                showLanguage.setText(showLanguageString);
                showAirdate.setText(showAirdateString);
                showRuntime.setText(showRuntimeString);
                numberOfSeasons.setText(numberOfSeasonsString);
                showStatus.setText(showStatusString);
                showName.setText(mShowName);

                if (response.body().getImage() != null) {
                    mImageUrlMedium = response.body().getImage().getMedium();
                    String pictureUrl = response.body().getImage().getOriginal();
                    Picasso.with(getContext())
                            .load(pictureUrl)
                            .resize(600, 700).centerCrop()
                            .into(toolbarImage);

                    Picasso.with(getContext()).load(mImageUrlMedium).into(showPoster);
                }

                Resources resources = getResources();
                if (response.body().getEmbedded().getNextepisode() != null){
                    mNextEpisodeAirdate = response.body().getEmbedded().getNextepisode().getAirdate();
                    nextEpisodeId = response.body().getEmbedded().getNextepisode().getId().toString();
                    mNextEpSeason = response.body().getEmbedded().getNextepisode().getSeason().toString();
                    mNextEpNumber = response.body().getEmbedded().getNextepisode().getNumber().toString();
                    String date = response.body().getEmbedded().getNextepisode().getAirdate();
                    String network = response.body().getNetwork().getName();
                    String nextEpisodeInfo = String.format(resources.getString(R.string.show_section_next_episode_link), mNextEpSeason, mNextEpNumber, date, mAirtime, network);
                    nextEpisodeBar.setText(nextEpisodeInfo);
                } else {
                    nextEpisodeBar.setText(getResources().getString(R.string.next_ep_no_info));
                }

                int seasonsSize = response.body().getEmbedded().getSeasons().size();

                String showAirYears;
                String startYear = response.body().getPremiered();
                if (response.body().getStatus().equals("Running")) {
                    showAirYears = String.format(resources.getString(R.string.show_air_years), startYear, "" );
                } else {
                    String endYear = response.body().getEmbedded().getSeasons().get(seasonsSize-1).getEndDate();
                    showAirYears = String.format(resources.getString(R.string.show_air_years), startYear, endYear );
                }
                showYears.setText(showAirYears);

                int castSize = response.body().getEmbedded().getCast().size();
                for (int i=0; i<castSize; i++) {
                    if (response.body().getEmbedded().getCast().get(i).getCharacter().getImage() != null ) {

                        View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                                .inflate(R.layout.cast_item, null);

                        TextView person = (TextView) vi.findViewById(R.id.cast_person);
                        TextView character = (TextView) vi.findViewById(R.id.cast_character);
                        ImageView characterImage = (ImageView) vi.findViewById(R.id.cast_image);

                        String imgUrl = response.body().getEmbedded().getCast().get(i).getCharacter().getImage().getMedium();
                        Picasso.with(getContext()).load(imgUrl).into(characterImage);
                        person.setText(response.body().getEmbedded().getCast().get(i).getPerson().getName());
                        character.setText(response.body().getEmbedded().getCast().get(i).getCharacter().getName());

                        topLinearLayout.addView(vi);
                    }
                }

                for (int i=0; i<seasonsSize; i++) {
                    if (response.body().getEmbedded().getSeasons().get(i).getImage()!= null ) {
                        View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                                .inflate(R.layout.season_list_item, null);

                        ImageView seasonImage = (ImageView) vi.findViewById(R.id.season_image);
                        final String seasonId = response.body().getEmbedded().getSeasons().get(i).getId().toString();

                        seasonImage.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startSeasonFragment(seasonId);
                                    }

                                });
                        String imgUrl = response.body().getEmbedded().getSeasons().get(i).getImage().getMedium();
                        Picasso.with(getContext()).load(imgUrl).into(seasonImage);
                        seasonsLinearLayout.addView(vi);
                    }
                }
                scrollView.addView(topLinearLayout);
                seasonsScrollView.addView(seasonsLinearLayout);

                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ShowDetailsWithNextEpisodeResponse> call, Throwable t) {
                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }
            }
        });
    }

    public void startSeasonFragment(String seasonId) {
        Fragment newFragment = new SeasonsFragment();
        Bundle arguments = new Bundle();
        arguments.putString( "seasonId", seasonId);
        arguments.putString("showId", showId);
        newFragment.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
