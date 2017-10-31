package se.sugarest.jane.bilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static se.sugarest.jane.bilder.Constants.MY_API_KEY;

/**
 * Created by jane on 17-10-31.
 */


// To search pics, the complete url example is :
// https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=[YOUR_API_KEY]&text=[KEY]&format=json&nojsoncallback=1

public interface FlickrsClient {

    // The endpoint for the request
    @GET("/rest/?method=flickr.photos.search&api_key="+ MY_API_KEY +"&text={key}&format=json&nojsoncallback=1")
    Call<List<Photo>> picForKey(@Path("key") String key);

}
