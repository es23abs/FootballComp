import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RegistrationScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Screen");

        // Create UI components
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Registration");
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField dobField = new TextField();
        TextField categoryField = new TextField();
        TextField levelField = new TextField();

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> registerCompetitor(nameField.getText(), emailField.getText(),
                dobField.getText(), categoryField.getText(), levelField.getText()));

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> primaryStage.close());

        // Add components to the layout
        root.getChildren().addAll(titleLabel, nameField, emailField, dobField, categoryField, levelField,
                registerButton, backButton);

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void registerCompetitor(String name, String email, String dob, String category, String level) {
        // Validate input fields (add your own validation logic)
        if (name.isEmpty() || email.isEmpty() || dob.isEmpty() || category.isEmpty() || level.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Create competitor number based on the number of competitors already present
        int competitorNumber = getCompetitorCount() + 1;

        // Save competitor information to CSV file
        saveToCSV(competitorNumber, name, email, dob, category, level);

        System.out.println("Registration successful!");
    }

    private int getCompetitorCount() {
        // For simplicity, assume competitors are counted by reading the existing CSV
        // file
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream("competitors.csv"))) {
            int count = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void saveToCSV(int competitorNumber, String name, String email, String dob, String category, String level) {
        try (FileWriter writer = new FileWriter("competitors.csv", true)) {
            writer.write(String.format("%d,%s,%s,%s,%s,%s%n", competitorNumber, name, email, dob, category, level));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
