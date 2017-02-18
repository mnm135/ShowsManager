package com.example.emil.showsmanager.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context).load(showList.get(position).getImageUrl()).into(imageView);
        return imageView;
    }

}
