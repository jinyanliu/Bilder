package se.sugarest.jane.bilder;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import se.sugarest.jane.bilder.utilities.FlickrJsonUtils;
import se.sugarest.jane.bilder.utilities.NetworkUtils;

/**
 * Created by jane on 17-11-1.
 */

public class GetPhotosTask extends AsyncTask<String, Void, List<Photo>> {

    private final static String LOG_TAG = GetPhotosTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {

        if (strings.length == 0) {
            return Collections.emptyList();
        }

        String textToSearch = strings[0];
        URL photoRequestUrl = NetworkUtils.buildUrl(textToSearch);
        Log.i(LOG_TAG, "photoRequestUrl: " + photoRequestUrl);

        try {
            String jsonFlickrResponse = NetworkUtils
                    .getResponseFromHttpUrl(photoRequestUrl);
            List<Photo> jsonPhotoData = FlickrJsonUtils
                    .extractResultsFromJson(jsonFlickrResponse);
            return jsonPhotoData;
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        super.onPostExecute(photos);
        Log.i(LOG_TAG, "There are: " + photos.size() + " available.");
    }
}
