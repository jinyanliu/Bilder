package se.sugarest.jane.bilder.util;

/**
 * Created by jane on 17-10-31.
 */
public class Constants {
    public static final String FLICKR_BASE_URL_RETROFIT = "https://api.flickr.com/";
    public static final String FLICKR_ENDPOINT_GET_RETROFIT = "/services/rest/";

    // Query Parameters and Values.
    // The value of PARAM_TEXT is user's input from EditText on MainActivity.
    public static final String PARAM_METHOD = "method";
    public static final String METHOD = "flickr.photos.search";

    public static final String PARAM_API_KEY = "api_key";
    public static final String API_KEY = "YOUR_API_KEY";

    public static final String PARAM_TEXT = "text";

    public static final String PARAM_PER_PAGE = "per_page";
    public static final String PER_PAGE = "52";

    public static final String PARAM_FORMAT = "format";
    public static final String FORMAT = "json";

    public static final String PARAM_NOJSONCALLBACK = "nojsoncallback";
    public static final String NOJSONCALLBACK = "1";

    public static final String PHOTO_SIZE_MAIN_ACTIVITY = "n";
    public static final String PHOTO_SIZE_DETAIL_ACTIVITY = "h";

    // The intent extra title passing between MainActivity and DetailActivity.
    public static final String INTENT_EXTRA_TITLE = "photoUrl";

    // Use with onSaveInstanceState method in MainActivity to save photos results on configuration
    // change on main screen.
    public static final String CONFIGURATION_KEY = "photos_list";

    // All the constants to create a single photo's url using this photo's farm, id, server
    // and secret information.
    // Example: https://farm5.staticflickr.com/4551/24280694778_a7ca496a79_n.jpg
    public static final String SINGLE_PHOTO_URL_FARM_PART_ONE = "https://farm";
    public static final String SINGLE_PHOTO_URL_STATIC_FLICKR_PART_TWO = ".staticflickr.com/";
    public static final String SINGLE_PHOTO_URL_SLASH = "/";
    public static final String SINGLE_PHOTO_URL_UNDER_SCORE = "_";
    public static final String SINGLE_PHOTO_URL_PHOTO_TYPE_LAST_PART = ".jpg";
}
