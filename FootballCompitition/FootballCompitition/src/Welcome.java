import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Welcome {
    GUI refc;

    public Welcome(GUI refc) {
        this.refc = refc;
        signIn();
    }

    public void signIn() {
        Stage stage = refc.primaryStage;

        // labels
        Label nameLabel = new Label("Username");
        Label passLabel = new Label("Password");

        // textfields for input
        TextField[] textFields = new TextField[2];
        textFields[0] = new TextField();
        textFields[1] = new PasswordField();

        // define buttons
        HBox btnContainer = new HBox();
        btnContainer.setPadding(new Insets(20, 0, 0, 55));
        btnContainer.setSpacing(10);

        Button[] btns = new Button[3];
        btns[0] = new Button("Staff signin");
        btns[1] = new Button("Register for compitition");
        btns[2] = new Button("Close");
        // add actionListner on btns
        for (Button btn : btns) {
            btnContainer.getChildren().add(btn);
            btn.setOnAction(event -> {
                String btnText = ((Button) event.getSource()).getText();
                if (btnText.equalsIgnoreCase("Staff signin")) {
                    if (refc.alerts.isEmptyField(textFields)) {
                        refc.alerts.infoAlert("Please fill all fields!");
                        return;
                    }
                    // refc.user = refc.dbhHandler.login(textFields[0].getText(),
                    // textFields[1].getText());
                    // if (refc.user != null) {
                    // stage.close();
                    // new HomeWindow(refc);
                    // return;
                    // }
                    refc.alerts.infoAlert("Login failed");
                } else if (btnText.equalsIgnoreCase("Register for compitition")) {
                    stage.close();
                    signUn();
                } else if (btnText.equalsIgnoreCase("Close")) {
                    stage.close();
                }
            });
        }

        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(30, 60, 0, 60));
        mainContainer.setSpacing(10);
        mainContainer.getChildren().addAll(nameLabel, textFields[0], passLabel, textFields[1], btnContainer);

        Scene scene = new Scene(mainContainer, 500, 250);

        stage.setTitle("Welcome to Football Compitition App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void signUn() {
        Stage stage = refc.primaryStage;

        // labels
        Label[] labels = new Label[4];
        labels[0] = new Label("Username");
        labels[1] = new Label("Email");
        labels[2] = new Label("Level");
        labels[3] = new Label("DOB");

        // textfields for input
        TextField[] textFields = new TextField[4];

        // define buttons and actionListner on them
        HBox btnContainer = new HBox();
        btnContainer.setPadding(new Insets(20, 0, 0, 110));
        btnContainer.setSpacing(10);

        Button[] btns = new Button[2];
        btns[0] = new Button("Register");
        btns[1] = new Button("Close");
        for (Button btn : btns) {
            btnContainer.getChildren().add(btn);
            btn.setOnAction(event -> {
                String btnText = ((Button) event.getSource()).getText();
                if (btnText.equalsIgnoreCase("Register")) {
                    if (refc.alerts.isEmptyField(textFields)) {
                        refc.alerts.infoAlert("Please fill all fields!");
                        return;
                    }

                    if (userPresent(textFields[1].getText())) {
                        refc.alerts.infoAlert("Error registering user. User email already present!!");
                        return;
                    }

                    refc.handler.registerUser(refc.competitors.size() + 1, textFields[0].getText(),
                            textFields[1].getText(),
                            textFields[2].getText(), textFields[3].getText());

                    refc.alerts.infoAlert("You have succesfully registered for the compitition");
                    stage.close();
                    signIn();

                } else if (btnText.equalsIgnoreCase("Close")) {
                    System.out.println("close");
                    stage.close();
                    signIn();
                }
            });
        }

        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(50, 60, 0, 60));
        mainContainer.setSpacing(10);

        // add labels and textfield in container
        for (int i = 0; i < 4; i++) {
            textFields[i] = new TextField();
            mainContainer.getChildren().add(labels[i]);
            mainContainer.getChildren().add(textFields[i]);
        }
        mainContainer.getChildren().add(btnContainer);

        Scene scene = new Scene(mainContainer, 500, 400);

        stage.setTitle("Welcome to the football compitition App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private boolean userPresent(String email) {
        for (Competitor competitor : refc.competitors) {
            if (competitor.getEmail().equals(email))
                return true;
        }
        return false;
    }
}
