package com.example.mobilelab;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import com.example.mobilelab.views.DetailFragment;
import com.example.mobilelab.views.ListFragment;



public class MainActivity extends FragmentActivity implements ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
