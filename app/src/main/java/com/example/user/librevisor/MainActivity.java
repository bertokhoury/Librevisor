package com.example.user.librevisor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {

    TextView appPermsTV;
    TextView appNameListItem;
    String textHolder;
    ListView lView;
    private ImageView logoIV;

    List<ApplicationInfo> packages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //generate list
        List<ApplicationInfo> list = getApplications();


        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, new MyCustomAdapter.IManagePressHandler() {
            @Override
            public void itemPressed(ApplicationInfo app) {
                Intent appManagerIntent = new Intent(MainActivity.this, AppManagerActivity.class);
                appManagerIntent.putExtra("APP_NAME", app.packageName);
                startActivity(appManagerIntent);
            }
        }, this);

        //handle listview and assign adapter
        lView = (ListView)findViewById(R.id.listView_apps);
        lView.setAdapter(adapter);

        //handle image
        logoIV = (ImageView) findViewById(R.id.imageView2);



        //appPermsTV = (TextView)findViewById(R.id.textView1);
        appNameListItem = (TextView) findViewById(R.id.appName);
        textHolder = "";
    }




    public void getOneApplicationName(){
        // Is this the function for when the user clicks "manage"?
    }

    public List<ApplicationInfo> getApplications()
    {
        ArrayList<ApplicationInfo> apps = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        Iterator<ApplicationInfo> itr = packages.iterator();
        while (itr.hasNext()) {
            if (itr.next().name == null) {
                itr.remove();
            }
        }


        return packages;
    }
}