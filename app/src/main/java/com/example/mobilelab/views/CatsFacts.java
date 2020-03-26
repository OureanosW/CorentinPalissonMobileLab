package com.example.mobilelab.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.mobilelab.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * The CatsFacts class will be the host of two fragments, ListFragment and DetailFragment,
 * where we will find the list of cats' facts in the first one,
 * the second will be the detail of clicked fact
 * A navigation Bar is there to reach also the added Dog part.
 */


public class CatsFacts extends FragmentActivity implements ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {


    private BottomNavigationView navigationBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats);
        navigationBar = findViewById(R.id.bottom_navigation);

        navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_dogs) {
                    Intent intent = new Intent(CatsFacts.this, DogsPictures.class);
                    CatsFacts.this.startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
