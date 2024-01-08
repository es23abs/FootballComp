import java.io.IOException;

public class ConsoleManager {
    public static void main(String[] args) {
        try {
            CompetitorList competitorList = new CompetitorList();
            competitorList.readCompetitorsFromCSV("competitors.csv");

            competitorList.displayFullDetails();

            competitorList.displayHighestScorer();

            competitorList.displaySummaryStatistics();

            competitorList.displayShortDetailsByCompetitorNumber(101);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
