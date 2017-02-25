package com.example.emil.showsmanager.subscribedshows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubscribedShowsGridAdapter extends BaseAdapter {
    private Context context;
    private List<FirebaseShow> showList;

    public SubscribedShowsGridAdapter(Context context, List<FirebaseShow> showList) {
        this.context = context;
        this.showList = showList;
    }

    public int getCount() {
        return showList.size();
    }

    public FirebaseShow getItem(int position) {
        return showList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.single_grid_item_subscribed_shows, null);
            TextView nameTextView = (TextView) gridView.findViewById(R.id.subscribed_grid_show_name);
            ImageView posterImageView = (ImageView) gridView.findViewById(R.id.subscribed_grid_image);
            gridView.setPadding(8, 8, 8, 8);
            posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            nameTextView.setText(showList.get(position).getName());
            if (showList.get(position).getImageUrl() != null) {
                Picasso.with(context).load(showList.get(position).getImageUrl()).into(posterImageView);
            } else {
                Picasso.with(context)
                        .load(R.mipmap.ic_no_image)
                        .resize(200, 300)
                        .into(posterImageView);
            }
        } else {
            gridView = (View) convertView;
        }

        return gridView;

    }

}
