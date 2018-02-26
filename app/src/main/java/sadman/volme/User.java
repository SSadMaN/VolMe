package sadman.volme;

/**
 * Created by sadman on 12/12/17.
 */

public class User {

    private String name;
    private String email;
    private String userUid;
    private String userImageURL;

    public User(){

    }

    public User(String name, String email, String userUid){
        this.name = name;
        this.email = email;
        this.userUid = userUid;
    }

    public User(String name, String email, String userUid, String userImageURL){
        this.name = name;
        this.email = email;
        this.userUid = userUid;
        this.userImageURL = userImageURL;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }
}
