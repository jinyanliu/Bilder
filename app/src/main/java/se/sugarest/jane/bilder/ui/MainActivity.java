package se.sugarest.jane.bilder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.sugarest.jane.bilder.R;
import se.sugarest.jane.bilder.api.FlickrClient;
import se.sugarest.jane.bilder.data.JSONResponse;
import se.sugarest.jane.bilder.data.Photo;
import se.sugarest.jane.bilder.data.PhotoAdapter;

import static se.sugarest.jane.bilder.Constants.API_KEY;
import static se.sugarest.jane.bilder.Constants.CONFIGURATION_KEY;
import static se.sugarest.jane.bilder.Constants.FLICKR_BASE_URL_RETROFIT;
import static se.sugarest.jane.bilder.Constants.FORMAT;
import static se.sugarest.jane.bilder.Constants.INTENT_EXTRA_TITLE;
import static se.sugarest.jane.bilder.Constants.METHOD;
import static se.sugarest.jane.bilder.Constants.NOJSONCALLBACK;
import static se.sugarest.jane.bilder.Constants.PER_PAGE;
import static se.sugarest.jane.bilder.Constants.PHOTO_SIZE_MAIN_ACTIVITY;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoAdapterOnClickHandler {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mButton;
    private PhotoAdapter mPhotoAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;
    private Toast mToast;
    private String mEditTextString;
    ArrayList<String> mPhotoUrlStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) findViewById(R.id.tv_empty_result);

        setUpRecyclerViewWithAdapter();
        setUpSearchButtonClick();

        if (savedInstanceState != null && savedInstanceState.containsKey(CONFIGURATION_KEY)) {
            mPhotoUrlStrings = savedInstanceState.getStringArrayList(CONFIGURATION_KEY);
            mPhotoAdapter.setPhotoData(mPhotoUrlStrings);
        }
    }

    /**
     * Override onSaveInstanceState, so the photos search results will survive while configuration
     * change.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPhotoUrlStrings != null && mPhotoUrlStrings.size() > 0) {
            outState.putStringArrayList(CONFIGURATION_KEY, mPhotoUrlStrings);
        }
    }

    private void setUpSearchButtonClick() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                makeFlickrSearchQuery();
                Log.i(LOG_TAG, "Search Button Clicked.");
            }
        });
    }

    private void makeFlickrSearchQuery() {
        showLoadingIndicator();
        mEditTextString = mEditText.getText().toString();
        if (mEditTextString.trim().isEmpty()) {
            showToast();
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, getString(R.string.toast_message_no_search_key_word_provided), Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.show();
        } else {
            setUpRetrofitGet();
        }
    }

    /**
     * Use External Library Retrofit to GET photos list,
     * according to user input key word as a parameter.
     * Reference: https://github.com/square/retrofit
     */
    private void setUpRetrofitGet() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(FLICKR_BASE_URL_RETROFIT)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        FlickrClient client = retrofit.create(FlickrClient.class);
        Call<JSONResponse> call = client.jsonForKey(METHOD, API_KEY, mEditTextString, PER_PAGE, FORMAT, NOJSONCALLBACK);

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.i(LOG_TAG, "Complete url to request is: " + response.raw().request().url().toString());
                Log.i(LOG_TAG, "response.body().toString == " + response.body().toString());
                // Get the list of photos from response.
                List<Photo> photoLists = response.body().getPhotos().getPhoto();
                if (photoLists != null && photoLists.size() > 0) {
                    setPhotoListDataToRecyclerView(photoLists);
                } else {
                    showEmptyView();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                showEmptyView();
                Log.i(LOG_TAG, "No Photo data comes back.");
            }
        });
    }

    private void setPhotoListDataToRecyclerView(List<Photo> photoLists) {
        mPhotoUrlStrings = new ArrayList<>();
        for (int i = 0; i < photoLists.size(); i++) {
            Photo currentPhoto = photoLists.get(i);
            int farm_id = currentPhoto.getFarm();
            String server_id = currentPhoto.getServer();
            String photo_id = currentPhoto.getId();
            String secret = currentPhoto.getSecret();
            String currentPhotoUrl = "https://farm" + String.valueOf(farm_id)
                    + ".staticflickr.com/" + server_id + "/" + photo_id + "_" + secret + "_" + PHOTO_SIZE_MAIN_ACTIVITY + ".jpg";
            Log.i(LOG_TAG, "CurrentPhotoUrl = " + currentPhotoUrl);
            mPhotoUrlStrings.add(currentPhotoUrl);
        }
        showRecyclerView();
        mPhotoAdapter.setPhotoData(mPhotoUrlStrings);
        Log.i(LOG_TAG, "Set photo date to adapter.");
    }

    private void hideKeyboard() {
        // Check if no view has focus
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Log.i(LOG_TAG, "Hide Key Board.");
    }

    private void setUpRecyclerViewWithAdapter() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_photos);
        final GridLayoutManager layoutManager
                = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter(this, this);
            Log.i(LOG_TAG, "Create a new mPhotoAdapter.");
        }
        mRecyclerView.setAdapter(mPhotoAdapter);
        Log.i(LOG_TAG, "Set adapter to recyclerview");
    }

    // Click one photo, opens up DetailActivity with this photo full screen.
    @Override
    public void onClick(String photoUrl) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(INTENT_EXTRA_TITLE, photoUrl);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * Use these 4 helper methods to take good care of the visibility of RecyclerView, ProgressBar,
     * EmptyTextView and Toast. Because only one should be visible on the screen at one time.
     */
    private void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mEmptyTextView.setVisibility(View.INVISIBLE);
    }

    private void showEmptyView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyTextView.setVisibility(View.INVISIBLE);
    }

    private void showToast() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mEmptyTextView.setVisibility(View.INVISIBLE);
    }
}
