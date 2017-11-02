package se.sugarest.jane.bilder.utilities;

/**
 * Created by jane on 17-11-1.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.sugarest.jane.bilder.data.Photo;

/**
 * Utility functions to handle FlickrAPI JSON data.
 */
public class FlickrJsonUtils {

    private final static String LOG_TAG = FlickrJsonUtils.class.getSimpleName();

    /**
     * @return a list of {@link Photo} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Photo> extractResultsFromJson(String flickrJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(flickrJSON)) {
            return null;
        }

        // Create an empty ArrayList that can start adding photos to
        List<Photo> photos = new ArrayList<>();

        /**
         * Try to parse the JSON response string. If there's a problem with the way the JSON
         * is formatted, a JSONException object will be thrown.
         * Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(flickrJSON);

            // Extract the JSONObject associated with the key called "photos".
            JSONObject photosObject = baseJsonResponse.getJSONObject("photos");

            // Extract the JSONArray associated with the key called "photo".
            JSONArray photosArray = photosObject.getJSONArray("photo");

            // For each photo in the movieArray, create a Photo object
            for (int i = 0; i < photosArray.length(); i++) {

                // Get a single photo at position i within the list of photos
                JSONObject currentPhoto = photosArray.getJSONObject(i);

                String id = currentPhoto.getString("id");
                String owner = currentPhoto.getString("owner");
                String secret = currentPhoto.getString("secret");
                String server = currentPhoto.getString("server");
                int farm = currentPhoto.getInt("farm");
                String title = currentPhoto.getString("title");
                int isPublic = currentPhoto.getInt("ispublic");
                int isFriend = currentPhoto.getInt("isfriend");
                int isFamily = currentPhoto.getInt("isfamily");

                Photo photo = new Photo(id, owner, secret, server, farm, title, isPublic, isFriend
                        , isFamily);

                photos.add(photo);

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed to pass Flickr JSON.");
        }
        return photos;
    }
}
