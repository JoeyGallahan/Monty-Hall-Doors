package com.example.josephgallahan.doors;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by joseph.gallahan on 9/16/2016.
 */
public class DoorsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_door);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.doors_layout);
            if (fragment == null)
            {
                fragment = new DoorsFragment();
                fm.beginTransaction().add(R.id.doors_layout, fragment).commit();
            }
        }

}


