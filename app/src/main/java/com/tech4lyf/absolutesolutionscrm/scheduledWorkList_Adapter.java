package com.tech4lyf.absolutesolutionscrm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;

import java.util.List;

public class scheduledWorkList_Adapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener {

    private final Activity context;
    List<ScheduledWorkList> scheduledWorkListList;
    int resource;

    public scheduledWorkList_Adapter(Activity context, int resource, List<ScheduledWorkList> scheduledWorkListList) {
        super(context, resource);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.resource=resource;
        this.scheduledWorkListList=scheduledWorkListList;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.scheduledworklist, null,false);

        TextView dateText = (TextView) rowView.findViewById(R.id.tvDate);

        TextView snoText = (TextView) rowView.findViewById(R.id.textViewSno);

        //titleText.setText(maintitle[position]);
        ScheduledWorkList scheduledWorkList= scheduledWorkListList.get(position);
        dateText.setText(scheduledWorkList.getDate());
        //subtitleText.setText(subtitle[position]);

        return rowView;

    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
