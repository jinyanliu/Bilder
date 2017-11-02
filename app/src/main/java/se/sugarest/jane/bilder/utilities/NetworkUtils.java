package se.sugarest.jane.bilder.utilities;

/**
 * Created by jane on 17-11-1.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static se.sugarest.jane.bilder.Constants.API_KEY;
import static se.sugarest.jane.bilder.Constants.PARAM_API_KEY;
import static se.sugarest.jane.bilder.Constants.FLICKR_BASE_URL;
import static se.sugarest.jane.bilder.Constants.FORMAT;
import static se.sugarest.jane.bilder.Constants.PARAM_FORMAT;
import static se.sugarest.jane.bilder.Constants.METHOD;
import static se.sugarest.jane.bilder.Constants.PARAM_METHOD;
import static se.sugarest.jane.bilder.Constants.NOJSONCALLBACK;
import static se.sugarest.jane.bilder.Constants.PARAM_NOJSONCALLBACK;
import static se.sugarest.jane.bilder.Constants.PER_PAGE;
import static se.sugarest.jane.bilder.Constants.PARAM_PER_PAGE;
import static se.sugarest.jane.bilder.Constants.PARAM_TEXT;

/**
 * These utilities will be used to communicate with the Flickr API.
 */
public class NetworkUtils {
    final static String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String text) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_METHOD, METHOD)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_TEXT, text)
                .appendQueryParameter(PARAM_PER_PAGE, PER_PAGE)
                .appendQueryParameter(PARAM_FORMAT, FORMAT)
                .appendQueryParameter(PARAM_NOJSONCALLBACK, NOJSONCALLBACK)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "This URL: " + builtUri + " is malformed.");
        }
        return url;
    }

    /**
     * @return The entire result from the HTTP response.
     * @throws IOException Related to network and stream reading.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}



