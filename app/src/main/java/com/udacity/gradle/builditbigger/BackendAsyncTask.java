package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import pe.ironbit.android.jokeandroidlibrary.JokeAndroidActivity;
import pe.ironbit.cloud.backend.myApi.MyApi;

public class BackendAsyncTask extends AsyncTask<Void, Void, String> {
    public interface BackendListener {
        void onAction();
    }

    private Context context;

    private static MyApi myApiService = null;

    private BackendListener listener = null;

    public BackendAsyncTask(Context context) {
        this.context = context;
    }

    public void setBackendListener(BackendListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null) {
            MyApi.Builder builder;

            builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
            builder.setRootUrl("http://10.0.2.2:8080/_ah/api");
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        if (listener != null) {
            listener.onAction();
        }

        Intent intent = new Intent(context, JokeAndroidActivity.class);
        intent.putExtra(JokeAndroidActivity.JOKE_ANDROID_LIBRARY_KEY, joke);

        context.startActivity(intent);
    }
}
