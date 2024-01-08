import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    public boolean registerUser(int competitorNumber, String name, String email, String dob, String level) {
        try (FileWriter writer = new FileWriter("competitors.csv", true)) {
            writer.write(String.format("%d,%s,%s,%s,%s%n", competitorNumber, name, email, dob, level));
            return true;
        } catch (IOException e) {
            System.out.println("Error in registering user");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Competitor> readUsers() {
        ArrayList<Competitor> competitors = new ArrayList<>();
        String filePath = "competitors.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int competitorNumber = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String level = data[3];
                String dob = data[4];

                int[] scores = new int[data.length - 5];
                for (int i = 5; i < data.length; i++) {
                    scores[i - 5] = Integer.parseInt(data[i]);
                }
                Competitor competitor = new Competitor(competitorNumber, name, email, level, dob, scores);
                competitors.add(competitor);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Exception in reading users");
        }
        return competitors;
    }
}
