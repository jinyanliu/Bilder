package se.sugarest.jane.bilder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.sugarest.jane.bilder.Photo;
import se.sugarest.jane.bilder.PhotoAdapter;
import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.utilities.FlickrJsonUtils;
import se.sugarest.jane.bilder.utilities.NetworkUtils;

import static se.sugarest.jane.bilder.Constants.FLICKR_SEARCH_LOADER;
import static se.sugarest.jane.bilder.Constants.PHOTO_SIZE_MAIN_ACTIVITY;
import static se.sugarest.jane.bilder.Constants.SEARCH_QUERY_URL_TEXT;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Photo>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private EditText mEditText;

    private Button mButton;

    private PhotoAdapter mPhotoAdapter;

    private ProgressBar mProgressBar;

    private TextView mEmptyTextView;

    public PhotoAdapter getmPhotoAdapter() {
        return mPhotoAdapter;
    }

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    public TextView getmEmptyTextView() {
        return mEmptyTextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerViewWithAdapter();

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) findViewById(R.id.tv_empty_result);

        getSupportLoaderManager().initLoader(FLICKR_SEARCH_LOADER, null, this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                makeFlickrSearchQuery();
                Log.i(LOG_TAG, "Adapter Debug: Button Clicked.");
            }
        });
    }

    private void makeFlickrSearchQuery() {
        String editTextString = mEditText.getText().toString();
        if (editTextString.trim().isEmpty()) {
            Toast.makeText(MainActivity.this, getText(R.string.toast_message_no_search_key_word_provided), Toast.LENGTH_SHORT).show();
        } else {
            URL photoRequestUrl = NetworkUtils.buildUrl(editTextString);
            Bundle queryBundle = new Bundle();
            queryBundle.putString(SEARCH_QUERY_URL_TEXT, photoRequestUrl.toString());

            Log.i(LOG_TAG, "Adapter Debug: complete url to request is: " + photoRequestUrl.toString());

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<List<Photo>> flickrSearchLoader = loaderManager.getLoader(FLICKR_SEARCH_LOADER);
            if (flickrSearchLoader == null) {
                loaderManager.initLoader(FLICKR_SEARCH_LOADER, queryBundle, this);
                Log.i(LOG_TAG, "Adapter Debug: initLoader.");
            } else {
                loaderManager.restartLoader(FLICKR_SEARCH_LOADER, queryBundle, this);
                Log.i(LOG_TAG, "Adapter Debug: restartLoader.");
            }
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Log.i(LOG_TAG, "Adapter Debug: Hide Key Board.");

    }

    private void setUpRecyclerViewWithAdapter() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_photos);
        final GridLayoutManager layoutManager
                = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter(this, this);
            Log.i(LOG_TAG, "Adapter Debug: create a new mPhotoAdapter.");
        }
        mRecyclerView.setAdapter(mPhotoAdapter);
        Log.i(LOG_TAG, "Adapter Debug: set adapter to recyclerview");
    }

    @Override
    public void onClick(String photoUrl) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("photoUrl", photoUrl);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public Loader<List<Photo>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Photo>>(this) {

            List<Photo> mFlickrPhotoList;

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                if (mFlickrPhotoList != null) {
                    deliverResult(mFlickrPhotoList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Photo> loadInBackground() {

                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_TEXT);
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                try {
                    URL flickrUrl = new URL(searchQueryUrlString);
                    String jsonFlickrResponse = NetworkUtils
                            .getResponseFromHttpUrl(flickrUrl);
                    mFlickrPhotoList = FlickrJsonUtils
                            .extractResultsFromJson(jsonFlickrResponse);
                    return mFlickrPhotoList;
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                    return Collections.emptyList();
                }
            }

            @Override
            public void deliverResult(List<Photo> data) {
                super.deliverResult(data);
                mFlickrPhotoList = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (null == data) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            Log.i(LOG_TAG, "No Photo data comes back.");
        } else {
            ArrayList<String> photoUrlStrings = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                Photo currentPhoto = data.get(i);
                int farm_id = currentPhoto.getPhotoFarm();
                String server_id = currentPhoto.getPhotoServer();
                String photo_id = currentPhoto.getPhotoId();
                String secret = currentPhoto.getPhotoSecret();
                String currentPhotoUrl = "https://farm" + String.valueOf(farm_id)
                        + ".staticflickr.com/" + server_id + "/" + photo_id + "_" + secret + "_" + PHOTO_SIZE_MAIN_ACTIVITY + ".jpg";
                Log.i(LOG_TAG, "CurrentPhotoUrl = " + currentPhotoUrl);
                photoUrlStrings.add(currentPhotoUrl);

            }
            mPhotoAdapter.setPhotoData(photoUrlStrings);
            Log.i(LOG_TAG, "Adapter Debug: set photo date to adapter.");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {
    }
}
