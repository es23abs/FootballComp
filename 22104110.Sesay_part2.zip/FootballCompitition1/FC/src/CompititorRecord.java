import java.sql.Date;

public class CompititorRecord {
    public String id;
    public String uname;
    public String score1;
    public String score2;
    public String score3;
    public String score4;
    public Date date;

    public CompititorRecord(String id, String nm, String s1, String s2, String s3, String s4, String d) {
        this.id = id;
        this.uname = nm;
        this.score1 = s1;
        this.score2 = s2;
        this.score3 = s3;
        this.score4 = s4;
        this.date = java.sql.Date.valueOf(d);
    }

    public String getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public Date getDate() {
        return date;
    }

    public String getScore2() {
        return score2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore4() {
        return score4;
    }

    public String getScore3() {
        return score3;
    }
}
