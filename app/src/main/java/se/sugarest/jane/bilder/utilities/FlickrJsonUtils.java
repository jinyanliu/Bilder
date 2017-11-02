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
        if (TextUtils.isEmpty(flickrJSON)) {
            return null;
        }

        List<Photo> photos = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(flickrJSON);
            JSONObject photosObject = baseJsonResponse.getJSONObject("photos");
            JSONArray photosArray = photosObject.getJSONArray("photo");
            for (int i = 0; i < photosArray.length(); i++) {
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
