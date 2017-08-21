package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pe.ironbit.android.JokeJavaService;

public class MainActivity extends AppCompatActivity {
    private BackendAsyncTask.BackendListener listener = null;

    public void setBackendListener(BackendAsyncTask.BackendListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
    }

    public void tellJokeFromGoogleCloud(View view) {
        BackendAsyncTask task = new BackendAsyncTask(getApplicationContext());
        task.setBackendListener(listener);
        task.execute();
    }

    public void tellJokeFromJavaLibrary(View view) {
        FragmentManager manager = getSupportFragmentManager();
        MainActivityFragment fragment = (MainActivityFragment)manager.findFragmentById(R.id.fragment);
        fragment.tellJokeFromJavaLibrary(view);
    }

    public void showJavaJoke() {
        Toast.makeText(this, JokeJavaService.getJoke(), Toast.LENGTH_SHORT).show();
    }
}
