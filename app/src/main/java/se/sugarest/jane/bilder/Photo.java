package se.sugarest.jane.bilder;

/**
 * Created by jane on 17-10-31.
 */

public class Photo {

    private String photoId;

    private String photoOwner;

    private String photoSecret;

    private String photoServer;

    private int photoFarm;

    private String photoTitle;

    private int photoIsPublic;

    private int photoIsFriend;

    private int photoIsFamily;

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoOwner() {
        return photoOwner;
    }

    public String getPhotoSecret() {
        return photoSecret;
    }

    public String getPhotoServer() {
        return photoServer;
    }

    public int getPhotoFarm() {
        return photoFarm;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public int getPhotoIsPublic() {
        return photoIsPublic;
    }

    public int getPhotoIsFriend() {
        return photoIsFriend;
    }

    public int getPhotoIsFamily() {
        return photoIsFamily;
    }
}
