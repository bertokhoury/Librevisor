package com.example.user.librevisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Berto on 12/1/2017.
 */


public class ListItem extends Activity {
    private Button showPermmisionsB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        showPermmisionsB = (Button) findViewById(R.id.show_permissions);

        showPermmisionsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAppManager(v);
            }
        });
    }


    public void goToAppManager(View view){
        Intent appManagerIntent = new Intent(ListItem.this, AppManagerActivity.class);
        startActivity(appManagerIntent);
    }
}
