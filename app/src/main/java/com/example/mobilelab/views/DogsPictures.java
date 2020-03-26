package com.example.mobilelab.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilelab.R;
import com.example.mobilelab.data_layer.DataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * I added the Dog class just to show I could handle different activities,
 * and switch from one to another with a navigation bar.
 *
 * The class display only a picture of a dog, that can be changed by clicking
 * on a button.
 *
 */

public class DogsPictures extends AppCompatActivity {

    private ImageView imageDog;
    private ProgressBar progressBarImageDog;
    private BottomNavigationView navigationBar;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        Button changeImageButton = findViewById(R.id.buttonChangeDog);
        imageDog = findViewById(R.id.imageDog);
        progressBarImageDog = findViewById(R.id.progressBarDog);
        navigationBar = findViewById(R.id.bottom_navigation);

        setImage();
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });

        navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_cats) {
                    Intent intent = new Intent(DogsPictures.this, CatsFacts.class);
                    DogsPictures.this.startActivity(intent);
                }
                return true;
            }
        });




    }

    private void setImage()
    {
        final DataViewModel model = new ViewModelProvider(this).get(DataViewModel.class);
        progressBarImageDog.setVisibility(View.VISIBLE);
        imageDog.setVisibility(View.INVISIBLE);
        model.getImage().observe(this,new Observer<String>() {
            @Override
            public void onChanged(String image) {
                Picasso.get().load(image)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                        .into(imageDog, new Callback() {
                            @Override
                            public void onSuccess() {
                                if (progressBarImageDog != null) {
                                    progressBarImageDog.setVisibility(View.GONE);
                                    imageDog.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }

        });

    }
}
