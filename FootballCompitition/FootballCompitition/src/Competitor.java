import java.util.Arrays;

public class Competitor extends GeneralCompetitor {
    private int[] scores;

    public Competitor(int competitorNumber, String name, String email, String level, String dob, int... scores) {
        super(competitorNumber, name, email, level, dob);
        this.scores = scores;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    @Override
    public double getOverallScore() {
        int sum = Arrays.stream(scores).sum();
        return (double) sum / scores.length;
    }
}
