import javafx.stage.Stage;

public class GUI {
    Stage primaryStage;
    Welcome welcome;
    Alerts alerts;
    Profile user;
    boolean isStaffAcc = false;

    public GUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initGUI();
    }

    public void initGUI() {
        alerts = new Alerts();
        DbHandler.readProfiles();
        welcome = new Welcome(this);
    }
}
