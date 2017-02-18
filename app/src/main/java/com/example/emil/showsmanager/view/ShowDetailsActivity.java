package com.example.emil.showsmanager.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.FullShowInfoResponse.Cast;
import com.example.emil.showsmanager.models.FullShowInfoResponse.Season;
import com.example.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;
import com.example.emil.showsmanager.presenter.ShowDetailsPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsMvpView {

    private ShowDetailsPresenter presenter;

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
    @BindView(R.id.next_episode_bar) TextView nextEpisodeBar;
    @BindView(R.id.image_toolbar) ImageView toolbarImage;
    @BindView(R.id.imdb_pic) ImageView imdbPicture;
    @BindView(R.id.tvmaze_pic) ImageView tvmazePicture;
    @BindView(R.id.thetvdb_pic) ImageView thetvdbPicture;
    @BindView(R.id.next_episode) LinearLayout nextEpisodeLinear;

    @BindView(R.id.cast_gallery_section) LinearLayout castGallerySection;
    @BindView(R.id.seasons_gallery_section) LinearLayout seasonsGallerySection;

    @BindView(R.id.fab_subscribe) FloatingActionButton fabSubscribe;


    @OnClick(R.id.fab_subscribe)
    public void showSubscription(View view) {
        presenter.onSubscribeClick();
    }

    HorizontalScrollView castScrollView;
    HorizontalScrollView seasonsScrollView;
    LinearLayout castLinearLayout;
    LinearLayout seasonsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ShowDetailsPresenter();
        presenter.attachView(this);

        setContentView(R.layout.fragment_search_result_more_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String showId = intent.getStringExtra("showId");

        presenter.loadShow(showId);
        presenter.startFabIconListener(showId);
    }

    public void bindShowData(final FullShowInfo show) {
        showDescription.setText(show.getSummary());
        showGenres.setText(show.getGenres().toString());
        showCountry.setText(show.getNetwork().getCountry().getName());
        showNetwork.setText(show.getLanguage());
        showLanguage.setText(show.getLanguage());
        showAirdate.setText(show.getPremiered());
        showStatus.setText(show.getStatus());
        showName.setText(show.getName());
        showRuntime.setText(String.valueOf(show.getRuntime()));

        if (show.getEmbedded().getNextepisode() != null) {
            Resources resources = getResources();

            String nextEpisodeAirdate = show.getEmbedded().getNextepisode().getAirdate();
            String nextEpisodeId = show.getEmbedded().getNextepisode().getId().toString();
            String nextEpisdeSeasonNumber = show.getEmbedded().getNextepisode().getSeason().toString();
            String nextEpisodeNumber = show.getEmbedded().getNextepisode().getNumber().toString();
            String date = show.getEmbedded().getNextepisode().getAirdate();
            String network = show.getNetwork().getName();
            String nextEpisodeInfo = String.format(resources.getString(R.string.show_section_next_episode_link), nextEpisdeSeasonNumber, nextEpisodeNumber, date, nextEpisodeAirdate, network);
            nextEpisodeBar.setText(nextEpisodeInfo);
        } else {
            nextEpisodeLinear.setVisibility(View.GONE);
        }

        if (show.getImage() != null) {
            Picasso.with(getContext())
                    .load(show.getImage().getOriginal())
                    .into(toolbarImage);
        }

        showSeasonsGallery(show.getEmbedded().getSeasons());
        showCastGallery(show.getEmbedded().getCast());
    }

    private void showSeasonsGallery(List<Season> seasons) {
        seasonsScrollView = (HorizontalScrollView) findViewById(R.id.srollview_seasons_gallery);
        seasonsLinearLayout = new LinearLayout(this);

        int picsAdded = 0;

        if (seasons != null) {
            int size = seasons.size();

            for (int i=0; i<size; i++) {
                if (seasons.get(i) != null && seasons.get(i).getImage() != null) {
                    View vi = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.season_list_item, null);

                    ImageView seasonImage = (ImageView) vi.findViewById(R.id.season_image);
                    final String seasonId = seasons.get(i).getId().toString();

                    seasonImage.setOnClickListener(
                            view -> startSeasonFragment(seasonId));

                    String imgUrl = seasons.get(i).getImage().getMedium();
                    Picasso.with(getContext()).load(imgUrl).into(seasonImage);
                    seasonsLinearLayout.addView(vi);

                    picsAdded++;
                }
            }
            seasonsScrollView.addView(seasonsLinearLayout);
            if (picsAdded < 1) {
                seasonsGallerySection.setVisibility(View.GONE);
            }
        }
    }


    private void showCastGallery(List<Cast> cast) {
        castScrollView = (HorizontalScrollView) findViewById(R.id.srollview_cast_gallery);
        castLinearLayout = new LinearLayout(this);

        int picsAdded = 0;

        if (cast != null) {
            int size = cast.size();

            for (int i=0; i<size; i++) {
                if (cast.get(i) != null && cast.get(i).getCharacter().getImage() != null) {
                    View view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.cast_item, null);

                    TextView person = (TextView) view.findViewById(R.id.cast_person);
                    TextView character = (TextView) view.findViewById(R.id.cast_character);
                    ImageView characterImage = (ImageView) view.findViewById(R.id.cast_image);

                    String imageUrl = cast.get(i).getCharacter().getImage().getMedium();

                    Picasso.with(getContext()).load(imageUrl).into(characterImage);
                    person.setText(cast.get(i).getPerson().getName());
                    character.setText(cast.get(i).getCharacter().getName());

                    castLinearLayout.addView(view);
                    picsAdded++;
                }
            }
            castScrollView.addView(castLinearLayout);
            if (picsAdded < 1) {
                castGallerySection.setVisibility(View.GONE);
            }
        }
    }

    private void startSeasonFragment(String seasonId) {
        /*Fragment fragment = new SeasonsFragment();
        Bundle bundle = new Bundle();
        bundle.putString( "seasonId", seasonId);
        bundle.putString("showId", showId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

    public void changeIcon(boolean subscribed) {
        if (subscribed) {
            fabSubscribe.setImageResource(R.drawable.ic_clear_black_24dp);
        } else {
            fabSubscribe.setImageResource(R.drawable.ic_add_black_24dp);
        }
    }

    public void showSnackbar(boolean subscribed) {
        final String message;
        final String undoMessage;
        View parentLayout = findViewById(R.id.parent_layout);

        if(subscribed) {
            message = getResources().getString(R.string.snackbar_unsubscribe);
            undoMessage = getResources().getString(R.string.snackbar_subscribe);
        } else {
            message = getResources().getString(R.string.snackbar_subscribe);
            undoMessage = getResources().getString(R.string.snackbar_unsubscribe);
        }
        Snackbar snackbar = Snackbar
                .make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setAction("UNDO", view -> {
                    presenter.onSubscribeClick();
                    Snackbar undoSnackbar = Snackbar.make(view, undoMessage, Snackbar.LENGTH_SHORT);
                    undoSnackbar.show();
                });
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
