package com.tech4lyf.absolutesolutionscrm;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ScheduledWorkListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;

    public ScheduledWorkListAdapter(Activity context, String[] maintitle) {
        super(context, R.layout.scheduled_work_list, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.maintitle = maintitle;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.scheduled_work_list, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textViewTitle);
        titleText.setText("Hello");

        return rowView;

    };
}
