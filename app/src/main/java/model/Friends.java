package model;

/**
 * Created by Developer on 23/5/2016.
 */
public class Friends {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    private String title,profilePic;

    public  Friends(){}
    public  Friends(String title,String profilePic){
        this.title=title;
        this.profilePic=profilePic;
    }
}
