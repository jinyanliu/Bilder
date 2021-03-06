package se.sugarest.jane.bilder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import se.sugarest.jane.bilder.R;

import static se.sugarest.jane.bilder.util.Constants.INTENT_EXTRA_TITLE;
import static se.sugarest.jane.bilder.util.Constants.PHOTO_SIZE_DETAIL_ACTIVITY;
import static se.sugarest.jane.bilder.util.Constants.PHOTO_SIZE_MAIN_ACTIVITY;

/**
 * This class handles the requests towards a single photo, e.g., rendering, zoom in and zoom out.
 * <p>
 * Created by jane on 17-10-31.
 */
public class DetailActivity extends AppCompatActivity {
    private final static String LOG_TAG = DetailActivity.class.getSimpleName();

    // Use external lib PhotoView to implement photo on touch zoom in and zoom out.
    // Reference: https://github.com/chrisbanes/PhotoView
    private PhotoView mPhotoView;
    private String mCurrentPhotoUrl;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPhotoView = (PhotoView) findViewById(R.id.iv_full_size_photo);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        showLoadingIndicator();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity == null) {
            Log.e(LOG_TAG, "Missing intent.");
            return;
        }
        if (!intentThatStartedThisActivity.hasExtra(INTENT_EXTRA_TITLE)) {
            Log.e(LOG_TAG, "Missing intent extra value associated with extra title: " + INTENT_EXTRA_TITLE);
            return;
        }
        setUpPhoto();
    }

    private void setUpPhoto() {
        mCurrentPhotoUrl = getIntent().getExtras().getString(INTENT_EXTRA_TITLE);
        mCurrentPhotoUrl = mCurrentPhotoUrl.replace("_" + PHOTO_SIZE_MAIN_ACTIVITY,
                "_" + PHOTO_SIZE_DETAIL_ACTIVITY);
        Log.i(LOG_TAG, "Current Photo url is: " + mCurrentPhotoUrl);
        showPhotoView();
        // Set photo using its url with Picasso Lib
        // Reference: https://github.com/square/picasso
        Picasso.with(this)
                .load(mCurrentPhotoUrl)
                .placeholder(R.drawable.blackbg_picasso)
                .error(R.drawable.blackbg_picasso)
                .into(mPhotoView);
    }

    // Used while loading data
    private void showLoadingIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
        mPhotoView.setVisibility(View.INVISIBLE);
    }

    // Used while data is loaded
    private void showPhotoView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mPhotoView.setVisibility(View.VISIBLE);
    }
}
