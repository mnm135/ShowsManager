package com.mnm135.emil.showsmanager.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.search.SearchActivity;
import com.mnm135.emil.showsmanager.showdetails.ShowDetailsActivity;
import com.mnm135.emil.showsmanager.subscribedshows.SubscribedShowsActivity;
import com.mnm135.emil.showsmanager.upcomingepisodes.UpcomingEpisodesActivity;

import butterknife.BindView;


public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FrameLayout frameLayout;

    @BindView(R.id.action_subscribed)
    View subscribeNavItem;

    @BindView(R.id.action_search)
    View searchNavItem;

    @BindView(R.id.action_upcoming)
    View upcomingNavItem;

    //protected Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


       // toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //   getSupportActionBar().setDisplayShowHomeEnabled(true);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

     /*   toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_subscribed:
                            startActivity(new Intent(getApplicationContext(), SubscribedShowsActivity.class));
                            break;
                        case R.id.action_search:
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            break;
                        case R.id.action_upcoming:
                            startActivity(new Intent(getApplicationContext(), UpcomingEpisodesActivity.class));
                            break;
                    }
                    return true;
                });


    }

    protected void setToolbar(String title) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //toolbar.setTitle(title);
        getSupportActionBar().setTitle(title);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressWarnings("deprecation")
    protected String summaryFromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(source).toString();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, ShowDetailsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}































