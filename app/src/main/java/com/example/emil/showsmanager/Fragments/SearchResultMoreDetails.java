package com.example.emil.showsmanager.Fragments;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.SearchResultDetailsActivity;

import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.ShowDetailsEndPoints;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultMoreDetails extends Fragment {


    String nextEpisodeId;

    @BindView(R.id.show_name) TextView showName;
    @BindView(R.id.show_years_first_section) TextView showYears;
    @BindView(R.id.next_ep_date) TextView nextEpisodeDate;
    @BindView(R.id.next_ep_number) TextView nextEpisodeNumber;
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

    @BindView(R.id.fab_subscribe)
    FloatingActionButton fabSubscribe;

    @BindView(R.id.next_episode) LinearLayout nextEpisode;

    @OnClick(R.id.next_episode)
    public void startNextEpisodeFragment(View view) {

        Fragment newFragment = new NextEpisodeDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString( "episodeId" , nextEpisodeId);
        newFragment.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }

    @OnClick(R.id.fab_subscribe)
    public void SnackbarNotification(View view) {
        Snackbar.make(view, "Great, it works with Material Design", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }



    HorizontalScrollView scrollView;


   ImageView toolbarImage;
    HorizontalScrollView seasonsScrollView;


    LinearLayout topLinearLayout;
    LinearLayout seasonsLinearLayout;

    String showId;



    public SearchResultMoreDetails() {

    }


    public static SearchResultMoreDetails newInstance(String param1, String param2) {
        SearchResultMoreDetails fragment = new SearchResultMoreDetails();



        return fragment;
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


        getShowDetails(showId);


        return view;
    }

    public void getShowDetails(String showId) {
        ShowDetailsEndPoints apiService = ApiClient.getClient().create(ShowDetailsEndPoints.class);
        Call<ShowDetailsWithNextEpisodeResponse> call = apiService.getResponse(showId, "cast", "nextepisode", "seasons");

        call.enqueue(new Callback<ShowDetailsWithNextEpisodeResponse>() {
            @Override
            public void onResponse(Call<ShowDetailsWithNextEpisodeResponse> call, Response<ShowDetailsWithNextEpisodeResponse> response) {


                if(response.body().getEmbedded().getNextepisode() != null) {
                    nextEpisodeId = response.body().getEmbedded().getNextepisode().getId().toString();
                }


                if (response.body().getImage().getOriginal() != null) {
                    String pictureUrl = response.body().getImage().getOriginal();
                    String picuteUrlMedium = response.body().getImage().getMedium();
                    Picasso.with(getContext())
                            .load(pictureUrl)
                            .into(toolbarImage);

                    Picasso.with(getContext()).load(picuteUrlMedium).into(showPoster);
                }


                showName.setText(response.body().getName());

                // @TODO zmienić years
                String startYear = response.body().getPremiered();

                int seasonsSize = response.body().getEmbedded().getSeasons().size();



                if (response.body().getStatus().equals("Running")) {
                    showYears.setText("(" + startYear.substring(0, 4) + " -)");
                } else {
                    String endyear = response.body().getEmbedded().getSeasons().get(seasonsSize-1).getEndDate();
                    showYears.setText("(" + startYear.substring(0, 4) + " - " + endyear.substring(0, 4) + ")");
                }



                if (response.body().getEmbedded().getNextepisode() != null){
                    nextEpisodeDate.setText(response.body().getEmbedded().getNextepisode().getAirdate());
                    String number = response.body().getEmbedded().getNextepisode().getSeason().toString() + "x" +
                            response.body().getEmbedded().getNextepisode().getNumber().toString();
                    nextEpisodeNumber.setText(number);
                } else {
                    nextEpisodeNumber.setText("No info about next episode");
                }
                showDescription.setText(response.body().getSummary());
                showGenres.setText(response.body().getGenres().toString());
                showCountry.setText(response.body().getNetwork().getCountry().getName());
                showNetwork.setText(response.body().getNetwork().getName());
                showLanguage.setText(response.body().getLanguage());
                showAirdate.setText(response.body().getPremiered());
                showRuntime.setText(response.body().getRuntime().toString());
                numberOfSeasons.setText(String.valueOf(response.body().getEmbedded().getSeasons().size()));
                showStatus.setText(response.body().getStatus());


                int castSize = response.body().getEmbedded().getCast().size();

                for (int i=0; i<castSize; i++) {

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

                for (int i=0; i<seasonsSize; i++) {
                    final ImageView imageView = new ImageView(getContext());
                    imageView.setTag(i);

                    if (response.body().getEmbedded().getSeasons().get(i).getImage()!= null ) {
                        String imgUrl = response.body().getEmbedded().getSeasons().get(i).getImage().getMedium();
                        Picasso.with(getContext()).load(imgUrl).into(imageView);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(7, 0, 7, 0);
                        imageView.setLayoutParams(lp);


                        seasonsLinearLayout.addView(imageView);
                    }
                }

                scrollView.addView(topLinearLayout);

                seasonsScrollView.addView(seasonsLinearLayout);

            }

            @Override
            public void onFailure(Call<ShowDetailsWithNextEpisodeResponse> call, Throwable t) {

            }
        });

    }


}
