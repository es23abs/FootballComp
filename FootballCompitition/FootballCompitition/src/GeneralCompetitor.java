public abstract class GeneralCompetitor {
    protected int competitorNumber;
    protected String name;
    protected String email;
    protected String level;
    protected String dob;

    public GeneralCompetitor(int competitorNumber, String name, String email, String level, String dob) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.level = level;
        this.email = email;
        this.dob = dob;
    }

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public String getName() {
        return name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getLevel() {
        return level;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract double getOverallScore();

    public String getFullDetails() {
        return String.format("Competitor number %d, name %s.%n%s has a %s.%nThis gives them an overall score of %.2f.",
                competitorNumber, name, name.split(" ")[0], level, getOverallScore());
    }

    public String getShortDetails() {
        return String.format("CN %d (%s) has an overall score of %.2f.", competitorNumber, getInitials(),
                getOverallScore());
    }

    protected String getInitials() {
        StringBuilder initials = new StringBuilder();
        for (String part : name.split(" ")) {
            initials.append(part.charAt(0));
        }
        return initials.toString().toUpperCase();
    }
}
