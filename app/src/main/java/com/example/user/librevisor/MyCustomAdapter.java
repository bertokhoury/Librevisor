package com.example.user.librevisor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Berto on 11/25/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {


    private List<ApplicationInfo> list;
    private Context context;
    private IManagePressHandler pressHandler;

    public interface IManagePressHandler {
        void itemPressed(ApplicationInfo app);
    }


    public MyCustomAdapter(List<ApplicationInfo> list, IManagePressHandler handler, Context context) {
        this.list = list;
        this.context = context;
        this.pressHandler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.appName);
        listItemText.setText(list.get(position).name);


        //Handle buttons and add onClickListeners
        //
        Button showPermissions = (Button)view.findViewById(R.id.show_permissions);

        //perform action if permissions button is clicked
        showPermissions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ApplicationInfo val = list.get(position);
                pressHandler.itemPressed(val);
                //list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }


}
