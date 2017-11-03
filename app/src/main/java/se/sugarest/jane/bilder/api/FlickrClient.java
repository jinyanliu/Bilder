package se.sugarest.jane.bilder.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import se.sugarest.jane.bilder.data.types.JSONResponse;

import static se.sugarest.jane.bilder.util.Constants.FLICKR_ENDPOINT_GET_RETROFIT;
import static se.sugarest.jane.bilder.util.Constants.PARAM_API_KEY;
import static se.sugarest.jane.bilder.util.Constants.PARAM_FORMAT;
import static se.sugarest.jane.bilder.util.Constants.PARAM_METHOD;
import static se.sugarest.jane.bilder.util.Constants.PARAM_NOJSONCALLBACK;
import static se.sugarest.jane.bilder.util.Constants.PARAM_PER_PAGE;
import static se.sugarest.jane.bilder.util.Constants.PARAM_TEXT;

/**
 * This interface will be used in MainActivity with external lib Retrofit.
 * Reference: https://github.com/square/retrofit
 * <p>
 * Created by jane on 17-11-2.
 */
public interface FlickrClient {
    @GET(FLICKR_ENDPOINT_GET_RETROFIT)
    Call<JSONResponse> jsonForKey(
            @Query(PARAM_METHOD) String method,
            @Query(PARAM_API_KEY) String api_key,
            @Query(PARAM_TEXT) String text,
            @Query(PARAM_PER_PAGE) String per_page,
            @Query(PARAM_FORMAT) String format,
            @Query(PARAM_NOJSONCALLBACK) String noJsonCallBack);
}
