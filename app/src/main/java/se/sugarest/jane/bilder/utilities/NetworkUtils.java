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
import static se.sugarest.jane.bilder.Constants.API_KEY_PARAM;
import static se.sugarest.jane.bilder.Constants.FLICKR_BASE_URL;
import static se.sugarest.jane.bilder.Constants.FORMAT;
import static se.sugarest.jane.bilder.Constants.FORMAT_PARAM;
import static se.sugarest.jane.bilder.Constants.METHOD;
import static se.sugarest.jane.bilder.Constants.METHOD_PARAM;
import static se.sugarest.jane.bilder.Constants.NOJSONCALLBACK;
import static se.sugarest.jane.bilder.Constants.NOJSONCALLBACK_PARAM;
import static se.sugarest.jane.bilder.Constants.PER_PAGE;
import static se.sugarest.jane.bilder.Constants.PER_PAGE_PARAM;
import static se.sugarest.jane.bilder.Constants.TEXT_PARAM;

/**
 * These utilities will be used to communicate with the Flickr API.
 */
public class NetworkUtils {

    final static String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Builds the URL used to query Flickr API
     *
     * @return The URL to use to query the Flickr API
     */
    public static URL buildUrl(String text) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(METHOD_PARAM, METHOD)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(TEXT_PARAM, text)
                .appendQueryParameter(PER_PAGE_PARAM, PER_PAGE)
                .appendQueryParameter(FORMAT_PARAM, FORMAT)
                .appendQueryParameter(NOJSONCALLBACK_PARAM, NOJSONCALLBACK)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "This URL: " + builtUri + " is malformed.");
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
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
