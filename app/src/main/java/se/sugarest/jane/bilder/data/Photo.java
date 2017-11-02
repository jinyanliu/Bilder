package se.sugarest.jane.bilder.data;

/**
 * Created by jane on 17-10-31.
 */

public class Photo {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    public Photo(String photoId, String photoOwner, String photoSecret, String photoServer,
                 int photoFarm, String photoTitle, int photoIsPublic, int photoIsFriend, int photoIsFamily) {
        this.id = photoId;
        this.owner = photoOwner;
        this.secret = photoSecret;
        this.server = photoServer;
        this.farm = photoFarm;
        this.title = photoTitle;
        this.ispublic = photoIsPublic;
        this.isfriend = photoIsFriend;
        this.isfamily = photoIsFamily;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", farm=" + farm +
                ", title='" + title + '\'' +
                ", ispublic=" + ispublic +
                ", isfriend=" + isfriend +
                ", isfamily=" + isfamily +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (farm != photo.farm) return false;
        if (ispublic != photo.ispublic) return false;
        if (isfriend != photo.isfriend) return false;
        if (isfamily != photo.isfamily) return false;
        if (!id.equals(photo.id)) return false;
        if (!owner.equals(photo.owner)) return false;
        if (!secret.equals(photo.secret)) return false;
        if (!server.equals(photo.server)) return false;
        return title.equals(photo.title);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + secret.hashCode();
        result = 31 * result + server.hashCode();
        result = 31 * result + farm;
        result = 31 * result + title.hashCode();
        result = 31 * result + ispublic;
        result = 31 * result + isfriend;
        result = 31 * result + isfamily;
        return result;
    }
}

