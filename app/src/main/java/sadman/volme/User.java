package sadman.volme;

/**
 * Created by sadman on 12/12/17.
 */

public class User {

    private String name;
    private String email;
    private String userUid;

    public User(){

    }

    public User(String name, String email, String userUid){
        this.name = name;
        this.email = email;
        this.userUid = userUid;
    }

    public String getUser_name() {return name;}
    public void setUser_name(String name) {this.name = name;}

    public String getUser_email() {return email;}
    public void setUser_email(String email) {this.email = email;}

    public String getUserUid() {return userUid;}
    public void setUserUid(String userUid) {this.userUid = userUid;}
}
