package com.example.user.librevisor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Berto on 12/1/2017.
 */



public class AppManagerActivity extends Activity {

    private TextView appNameTV;
    private TextView packageNameTV;
    private ImageView uninstallIV;
    private ImageView extractApkIV;
    private ImageView settingsIV;
    private ImageView undoIV;
    private ImageView androidIconIV;
    private String extractAPK_string;
    private String goToDeviceSettings_string;
    private String return_string;
    private String deleteapk_string;



    private Application selectedApp;
    private File aPKDropLocation;
    TextView outputText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_manager);

        appNameTV = (TextView) findViewById(R.id.textView2);
        packageNameTV = (TextView) findViewById(R.id.textView4);
        uninstallIV = (ImageView) findViewById(R.id.uninstall_image);
        extractApkIV = (ImageView) findViewById(R.id.extractapk_image);
        settingsIV = (ImageView) findViewById(R.id.settings_image);
        undoIV = (ImageView) findViewById(R.id.undo_image);
        androidIconIV = (ImageView) findViewById(R.id.imageView);

        //  receive value of selectedApp here from previous activity
        // set text
       // appNameTV.setText();
       // packageNameTV.setText();


        String appName = getIntent().getStringExtra("APP_NAME");
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(appName, 0);
            selectedApp = new Application(info, AppManagerActivity.this);
            appNameTV.setText(info.applicationInfo.name);
            packageNameTV.setText(info.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeMessages(){
        extractAPK_string = (String) getResources().getText(R.string.extract_string);
        goToDeviceSettings_string = (String) getResources().getText(R.string.devicesettings_string);
        return_string = (String) getResources().getText(R.string.return_string);
        deleteapk_string = (String) getResources().getText(R.string.deleteapk_stringt);
    }


    private static File getAPKDropLocation()
    {
        return new File(Environment.getExternalStorageDirectory() + "/LibrevisorAPKs");
    }


    public void goToMainActivity(View view){
        Toast.makeText(this, return_string, Toast.LENGTH_LONG).show();
        finish();
    }

    public void extractAPK(View view){
        aPKDropLocation = getAPKDropLocation();
        selectedApp.copyFile(aPKDropLocation);
        Toast.makeText(this, extractAPK_string, Toast.LENGTH_LONG).show();
    }

    public void uninstallApp(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Uninstall");
        alertDialogBuilder.setMessage("Are you sure you want to uninstall this application?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                selectedApp.uninstallPackage(AppManagerActivity.this);

                return;
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public void goToDeviceSettings(View view){
        Toast.makeText(this, goToDeviceSettings_string, Toast.LENGTH_LONG).show();
        selectedApp.navigateToPermissionsManager(this);

    }

    public void deleteExtractedAPK(View view){
        Toast.makeText(this, deleteapk_string, Toast.LENGTH_LONG).show();


    }

}
