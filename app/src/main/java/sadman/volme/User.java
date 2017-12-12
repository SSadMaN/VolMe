package sadman.volme;

/**
 * Created by sadman on 12/12/17.
 */

public class User {

    private String name;
    private String email;

    public User(){

    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getUser_name() {return name;}
    public void setUser_name(String name) {this.name = name;}

    public String getUser_email() {return email;}
    public void setUser_email(String email) {this.email = email;}
}
