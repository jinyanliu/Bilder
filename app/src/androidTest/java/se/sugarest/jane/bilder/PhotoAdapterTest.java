package se.sugarest.jane.bilder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import se.sugarest.jane.bilder.data.PhotoAdapter;

/**
 * Created by jane on 17-11-3.
 */
@RunWith(AndroidJUnit4.class)
public class PhotoAdapterTest {
    private Context instrumentationCtx;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> photoUrlStrings = new ArrayList<>();

    @Before
    public void setUp() {
        //getContext() will only gets unit test's context, have to use getTargetContext()
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
        photoAdapter = new PhotoAdapter(null, instrumentationCtx);
    }

    @Test
    public void getItemCountTest_onePhoto() throws Exception {
        String onePhotoUrl = "https://farm5.staticflickr.com/4551/24280694778_a7ca496a79_n.jpg";
        photoUrlStrings.add(onePhotoUrl);
        photoAdapter.setPhotoData(photoUrlStrings);
        Assert.assertEquals(1, photoAdapter.getItemCount());
    }

    @Test
    public void getItemCountTest_twoPhotos() throws Exception {
        String firstPhotoUrl = "https://farm5.staticflickr.com/4551/24280694778_a7ca496a79_n.jpg";
        String secondPhotoUrl = "https://farm5.staticflickr.com/4563/38078168566_5a25caf1c7_n.jpg";
        photoUrlStrings.add(firstPhotoUrl);
        photoUrlStrings.add(secondPhotoUrl);
        photoAdapter.setPhotoData(photoUrlStrings);
        Assert.assertEquals(2, photoAdapter.getItemCount());
    }
}
