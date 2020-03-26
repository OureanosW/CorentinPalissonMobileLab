package com.example.mobilelab.data_layer;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Class that access APIs to collect Data.
 *
 * This model is used for two different things: get some cats' fact,
 * and get some dogs' image.
 * Two different models could have been done, but since the name was general,
 * I considered the purpose of this model could be general
 */



public class DataViewModel extends ViewModel {

    private MutableLiveData<String> imageAdress = null;
    private MutableLiveData<ArrayList<String>> factsList = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public LiveData<ArrayList<String>> getFact()
    {
        if(factsList == null)
        {
            factsList = new MutableLiveData<>();
            loadFacts();
        }
        return factsList;
    }

    public LiveData<String> getImage()
    {
        if(imageAdress == null)
        {
            imageAdress = new MutableLiveData<>();

        }
        loadImage();
        return imageAdress;
    }


    void runFacts(OkHttpClient client, String url) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<String> facts = new ArrayList<String>();
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    try {
                        JSONObject obj = new JSONObject(response.body().string());

                        JSONArray array = obj.getJSONArray("data");
                        for(int i = 0; i < array.length(); i++)
                        {
                            facts.add(array.getJSONObject(i).getString("fact"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    factsList.postValue(facts);
                }
            }
        });
    }

    void runImage(OkHttpClient client, String url) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String image = new String();
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        image = obj.getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    imageAdress.postValue(image);
                }
            }
        });
    }

    private void loadFacts() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            runFacts(client, "https://catfact.ninja/facts?limit=10");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImage()
    {
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            runImage(client, "https://dog.ceo/api/breeds/image/random");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
