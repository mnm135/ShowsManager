package com.example.emil.showsmanager.Fragments;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.emil.showsmanager.models.CastAndNextEpisode.Image;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.ShowDetailsEndPoints;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultMoreDetails extends Fragment {

    private static final String SHOW_ID = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    HorizontalScrollView scrollView;


 //   ImageView posterHolder;
   // TextView nameHolder;
  //  TextView statusHolder;
  //  TextView plotHolder;
//    TextView genresHolder;
 //   ImageView imageView;
   ImageView toolbarImage;
    HorizontalScrollView seasonsScrollView;
  ///  TextView episodeTimeHolder;
//    TextView countryHolder;
  //  TextView nextEpisodeNameHolder;
//    TextView nextEpisodeAirdateHolder;
  //  TextView nextEpisodeNumber;
    //TextView nextEpisodeSummary;
    //ImageView nextEpisodePicture;

    LinearLayout topLinearLayout;
    LinearLayout seasonsLinearLayout;

    private String mParam1;
    String showId;



    public SearchResultMoreDetails() {
        // Required empty public constructor
    }


    public static SearchResultMoreDetails newInstance(String param1, String param2) {
        SearchResultMoreDetails fragment = new SearchResultMoreDetails();
        Bundle args = new Bundle();
        args.putString(SHOW_ID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if (getArguments() != null) {
         //   mParam1 = getArguments().getString(SHOW_ID);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        //}
        //((SearchResultDetailsActivity) getActivity()).getSupportActionBar().hide();
        //ButterKnife.bind(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_search_result_more_details, container, false);
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



                if (response.body().getImage().getOriginal() != null) {
                    String pictureUrl = response.body().getImage().getOriginal();
                    String picuteUrlMedium = response.body().getImage().getMedium();
                    Picasso.with(getContext())
                            .load(pictureUrl)
                            //.resize(300,300)
                            //.placeholder(R.drawable.placeholder_image)
                            //.error(R.drawable.placeholder_image)
                            .into(toolbarImage);

                    Picasso.with(getContext()).load(picuteUrlMedium).into(showPoster);
                }

                /*






    @BindView(R.id.show_number_of_episodes) TextView numberOfEpisodes;


    @BindView(R.id.imdb_pic) ImageView imdbPicture;
    @BindView(R.id.tvmaze_pic) ImageView tvmazePicture;
    @BindView(R.id.tvrage_pic) ImageView tvragePicture;
    @BindView(R.id.thetvdb_pic) ImageView thetvdbPicture;
                 */

                String genres = response.body().getGenres().toString();
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
                //int seasonsSize = response.body().getEmbedded().getSeasons().size();


                for (int i=0; i<castSize; i++) {

                    View vi = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.cast_item, null);

                    TextView person = (TextView) vi.findViewById(R.id.cast_person);
                    TextView character = (TextView) vi.findViewById(R.id.cast_character);
                    ImageView characterImage = (ImageView) vi.findViewById(R.id.cast_image);




                  //  final ImageView imageView = new ImageView(getContext());
                   // imageView.setTag(i);
                    String imgUrl = response.body().getEmbedded().getCast().get(i).getCharacter().getImage().getMedium();
                    Picasso.with(getContext()).load(imgUrl).into(characterImage);
                    person.setText(response.body().getEmbedded().getCast().get(i).getPerson().getName());
                    character.setText(response.body().getEmbedded().getCast().get(i).getCharacter().getName());

                    topLinearLayout.addView(vi);


                  //  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                  //  lp.setMargins(7, 0, 7, 0);
                  //  imageView.setLayoutParams(lp);


                    //topLinearLayout.addView(imageView);

                            //  Picasso.with(getContext()).load(picuteUrlMedium).into(showPoster);
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

                    //  Picasso.with(getContext()).load(picuteUrlMedium).into(showPoster);
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
