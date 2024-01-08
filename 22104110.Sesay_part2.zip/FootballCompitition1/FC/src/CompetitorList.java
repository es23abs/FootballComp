import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompetitorList {
    private ArrayList<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    public void readCompetitorsFromCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
        }
    }

    public void displayFullDetails() {
        for (Competitor competitor : competitors) {
            System.out.println(competitor.getFullDetails());
        }
    }

    public void displayHighestScorer() {
        Competitor highestScorer = competitors.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore())).orElse(null);
        if (highestScorer != null) {
            System.out.println("The competitor with the highest overall score is:");
            System.out.println(highestScorer.getFullDetails());
        }
    }

    public void displaySummaryStatistics() {
        int totalCompetitors = competitors.size();
        double averageOverallScore = competitors.stream().mapToDouble(Competitor::getOverallScore).average().orElse(0);
        System.out.println("STATISTICAL:");
        System.out.println("There are " + totalCompetitors + " competitors");
        System.out.println("The average overall score is: " + averageOverallScore);
    }

    public void displayShortDetailsByCompetitorNumber(int competitorNumber) {
        Competitor competitor = getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            System.out.println("Short details for competitor with number " + competitorNumber + ":");
            System.out.println(competitor.getShortDetails());
        } else {
            System.out.println("Competitor with number " + competitorNumber + " not found.");
        }
    }

    public Competitor getCompetitorByNumber(int competitorNumber) {
        return competitors.stream().filter(c -> c.getCompetitorNumber() == competitorNumber).findFirst().orElse(null);
    }
}
