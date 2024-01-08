import java.io.File;
import java.io.PrintStream;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Scanner;

public class DbHandler {

    static Alerts alerts = new Alerts();
    public static ArrayList<Profile> profiles = new ArrayList<>();
    public static ObservableList<CompititorRecord> usersScores = FXCollections.observableArrayList();

    public static void readProfiles() {
        String line;
        String[] parts;
        try {
            Scanner reader = new Scanner(new File("Profiles.csv"));
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                parts = line.split(",");
                profiles.add(new Profile(parts[0], parts[1], parts[2], parts[3]));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Exception in readProfiles method");
        }
    }

    public static boolean addProfile(String uname, String fname, String lname, String pass) {
        profiles.add(new Profile(uname, fname, lname, pass));
        return saveProfiles();
    }

    public static boolean saveProfiles() {
        try {
            PrintStream writer = new PrintStream("Profiles.csv");
            for (Profile p : profiles) {
                writer.println(p.uname + "," + p.fname + "," + p.lname + "," + p.password);
            }
            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception in saveProfiles method");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveRecords() {
        try {
            PrintStream writer = new PrintStream("Scores.csv");
            for (CompititorRecord r : usersScores) {
                writer.println(r.id + "," + r.uname + "," + r.score1 + "," + r.score2 + "," + r.score3 + "," + r.score4
                        + "," + r.date);
            }
            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception in saveScores method");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editprofile(String uname, String fname, String lname) {
        for (int i = 0; i < profiles.size(); i++) {
            if (uname.equals(profiles.get(i).uname)) {
                profiles.get(i).fname = fname;
                profiles.get(i).lname = lname;
                break;
            }
        }
        return saveProfiles();
    }

    public static ObservableList<CompititorRecord> readScores(String uname) {
        usersScores = FXCollections.observableArrayList();
        String line;
        String[] parts;
        try {
            Scanner reader = new Scanner(new File("Scores.csv"));
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                parts = line.split(",");
                usersScores.add(
                        new CompititorRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Exception in getRecords method");
        }
        return usersScores;
    }

    public static boolean saveUserScores(String uname, String s1, String s2, String s3, String s4, Date date) {
        usersScores.add(new CompititorRecord(generateScoresId(), uname, s1, s2, s3, s4, date.toString()));
        return saveRecords();
    }

    public static void updateUserScores(String uname, String s1, String s2, String s3, String s4,
            Date date, String id) {

        for (int i = 0; i < usersScores.size(); i++) {
            if (uname.equals(usersScores.get(i).uname) && id.equals(usersScores.get(i).id)) {
                usersScores.get(i).score1 = s1;
                usersScores.get(i).score2 = s2;
                usersScores.get(i).score3 = s3;
                usersScores.get(i).score4 = s4;
                usersScores.get(i).date = date;
                break;
            }
        }
        saveRecords();
    }

    public static boolean deleteUserScores(String uname, String id) {

        for (int i = 0; i < usersScores.size(); i++) {
            if (uname.equals(usersScores.get(i).uname) && id.equals(usersScores.get(i).id)) {

                usersScores.remove(i);
                saveRecords();
                return true;
            }
        }
        return false;
    }

    public static Profile login(String uname, String pass) {
        for (int i = 0; i < profiles.size(); i++) {
            if (uname.equals(profiles.get(i).uname) && pass.equals(profiles.get(i).password)) {
                return profiles.get(i);
            }
        }
        return null;
    }

    public static String generateScoresId() {
        int n = 8;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        String result = sb.toString();
        return result;
    }
}
