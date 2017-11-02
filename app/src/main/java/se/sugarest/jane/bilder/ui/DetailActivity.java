package se.sugarest.jane.bilder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import se.sugarest.jane.bilder.R;

import static se.sugarest.jane.bilder.Constants.INTENT_EXTRA_TITLE;
import static se.sugarest.jane.bilder.Constants.PHOTO_SIZE_DETAIL_ACTIVITY;
import static se.sugarest.jane.bilder.Constants.PHOTO_SIZE_MAIN_ACTIVITY;

/**
 * Created by jane on 17-10-31.
 */

public class DetailActivity extends AppCompatActivity {
    private final static String LOG_TAG = DetailActivity.class.getSimpleName();

    /**
     * External Lib: PhotoView
     * Reference: https://github.com/chrisbanes/PhotoView
     */
    PhotoView mPhotoView;
    String mCurrentPhotoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPhotoView = (PhotoView) findViewById(R.id.iv_full_size_photo);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(INTENT_EXTRA_TITLE)) {
                mCurrentPhotoUrl = getIntent().getExtras().getString(INTENT_EXTRA_TITLE);
                mCurrentPhotoUrl = mCurrentPhotoUrl.replace("_" + PHOTO_SIZE_MAIN_ACTIVITY,
                        "_" + PHOTO_SIZE_DETAIL_ACTIVITY);
                Log.i(LOG_TAG, "Current Photo url is: " + mCurrentPhotoUrl);

                // Set photo using its url with Picasso Lib
                // Reference: https://github.com/square/picasso
                Picasso.with(this)
                        .load(mCurrentPhotoUrl)
                        .into(mPhotoView);
            } else {
                Log.e(LOG_TAG, "Missing intent extra value associated with extra title: " + INTENT_EXTRA_TITLE);
            }
        } else {
            Log.e(LOG_TAG, "Missing intent.");
        }
    }
}
