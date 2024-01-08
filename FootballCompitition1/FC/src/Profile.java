public class Profile {
    public String uname;
    public String fname;
    public String lname;
    public String password;

    public Profile(String uname, String fname, String lname, String pass) {
        this.uname = uname;
        this.fname = fname;
        this.lname = lname;
        this.password = pass;
    }

    public Profile(String uname, String pass) {
        this.uname = uname;
        this.password = pass;
    }
}
