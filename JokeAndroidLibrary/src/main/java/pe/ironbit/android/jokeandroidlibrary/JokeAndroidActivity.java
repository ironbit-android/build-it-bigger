package pe.ironbit.android.jokeandroidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JokeAndroidActivity extends AppCompatActivity {
    public static String JOKE_ANDROID_LIBRARY_KEY = JokeAndroidActivity.class.getSimpleName();

    @BindView(R2.id.joke_data)
    TextView jokeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_android);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_ANDROID_LIBRARY_KEY);

        if (!TextUtils.isEmpty(joke)) {
            jokeData.setText(joke);
        }
    }
}
