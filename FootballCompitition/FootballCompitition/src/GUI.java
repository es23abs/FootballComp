import java.util.ArrayList;

import javafx.stage.Stage;

public class GUI {
    Stage primaryStage;
    Welcome welcome;
    Alerts alerts;
    FileHandler handler;
    ArrayList<Competitor> competitors;

    public GUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initGUI();
    }

    public void initGUI() {
        alerts = new Alerts();
        handler = new FileHandler();

        competitors = handler.readUsers();

        welcome = new Welcome(this);
    }
}
