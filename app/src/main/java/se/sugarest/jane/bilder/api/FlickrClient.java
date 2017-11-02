package se.sugarest.jane.bilder.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import se.sugarest.jane.bilder.data.JSONResponse;

import static se.sugarest.jane.bilder.Constants.PARAM_API_KEY;
import static se.sugarest.jane.bilder.Constants.PARAM_FORMAT;
import static se.sugarest.jane.bilder.Constants.PARAM_METHOD;
import static se.sugarest.jane.bilder.Constants.PARAM_NOJSONCALLBACK;
import static se.sugarest.jane.bilder.Constants.PARAM_PER_PAGE;
import static se.sugarest.jane.bilder.Constants.PARAM_TEXT;

/**
 * Created by jane on 17-11-2.
 */

public interface FlickrClient {
    @GET("/services/rest/")
    Call<JSONResponse> jsonForKey(
            @Query(PARAM_METHOD) String method,
            @Query(PARAM_API_KEY) String api_key,
            @Query(PARAM_TEXT) String text,
            @Query(PARAM_PER_PAGE) String per_page,
            @Query(PARAM_FORMAT) String format,
            @Query(PARAM_NOJSONCALLBACK) String noJsonCallBack);
}
