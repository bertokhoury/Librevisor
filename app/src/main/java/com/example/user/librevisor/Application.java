package com.example.user.librevisor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vicki on 11/26/2017.
 */

public class Application {

    PackageInfo packageInfo;
    private String name;
    private String apk;
    private String version;
    private String source;
    private String data;
    private Drawable icon;
    private Boolean system;


    public Application(PackageInfo packageInfo, Context context)
    {
        this.packageInfo = packageInfo;
        PackageManager packageManager = context.getPackageManager();
        name = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
        apk = packageInfo.packageName;
        version = packageInfo.versionName;
        source = packageInfo.applicationInfo.publicSourceDir;
        data = packageInfo.applicationInfo.dataDir;
        icon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
        system = true;
        if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            system = false;
    }

    public String getName() {
        return name;
    }

    public String getAPK() {
        return apk;
    }

    public String getVersion() {
        return version;
    }

    public String getSource() {
        return source;
    }

    public String getData() {
        return data;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Boolean isSystem() {
        return system;
    }

    public String[] getApplicationPermissions(Context context)
    {
        PackageManager pm = context.getPackageManager();
        String[] requestedPermissions = new String[0];
        requestedPermissions = packageInfo.requestedPermissions;

        return requestedPermissions;
    }

    private File getOutputFilename(File destination) {
        return new File(destination.getPath() + "//" + getAPK() + ".apk");
    }

    public void navigateToPermissionsManager(Context context)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageInfo.packageName, null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public Boolean copyFile(File destination) {
        File initialFile = new File(getSource());
        try {
            FileUtils.copyFileToDirectory(initialFile, destination);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void uninstallPackage(Context context) {
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);

        try {
            intent.setData(Uri.fromParts("package", packageInfo.packageName, null));
            intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
            context.startActivity(intent);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
