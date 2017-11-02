package se.sugarest.jane.bilder.data;

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

    public Photo(String photoId, String photoOwner, String photoSecret, String photoServer,
                 int photoFarm, String photoTitle, int photoIsPublic, int photoIsFriend, int photoIsFamily) {
        this.photoId = photoId;
        this.photoOwner = photoOwner;
        this.photoSecret = photoSecret;
        this.photoServer = photoServer;
        this.photoFarm = photoFarm;
        this.photoTitle = photoTitle;
        this.photoIsPublic = photoIsPublic;
        this.photoIsFriend = photoIsFriend;
        this.photoIsFamily = photoIsFamily;
    }

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

    @Override
    public String toString() {
        return "Photo{" +
                "photoId='" + photoId + '\'' +
                ", photoOwner='" + photoOwner + '\'' +
                ", photoSecret='" + photoSecret + '\'' +
                ", photoServer='" + photoServer + '\'' +
                ", photoFarm=" + photoFarm +
                ", photoTitle='" + photoTitle + '\'' +
                ", photoIsPublic=" + photoIsPublic +
                ", photoIsFriend=" + photoIsFriend +
                ", photoIsFamily=" + photoIsFamily +
                '}';
    }
}
