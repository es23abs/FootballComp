
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HomeWindow {
    GUI refc;

    public HomeWindow(GUI refc) {
        this.refc = refc;
        showWindow();
    }

    public void showWindow() {
        Stage stage = refc.primaryStage;

        // menu items
        MenuItem[] menuItems = new MenuItem[9];
        menuItems[0] = new MenuItem("Add Record");
        menuItems[1] = new MenuItem("Edit Record");
        menuItems[2] = new MenuItem("Delete Record");
        menuItems[3] = new MenuItem("View Records");
        menuItems[4] = new MenuItem("View Profile");
        menuItems[5] = new MenuItem("Update Profile");
        menuItems[6] = new MenuItem("Export Records");
        menuItems[7] = new MenuItem("About Us");
        menuItems[8] = new MenuItem("Log Out");

        // add action listener on menuitems
        EventHandler<ActionEvent> action = menuItemAction();
        for (MenuItem item : menuItems)
            item.setOnAction(action);

        // menues and add menuItems to them
        Menu[] menus = new Menu[5];
        menus[0] = new Menu("Records");
        menus[0].getItems().addAll(menuItems[0], menuItems[1], menuItems[2], menuItems[3]);
        menus[1] = new Menu("Profile");
        menus[1].getItems().addAll(menuItems[4], menuItems[5]);
        menus[2] = new Menu("Export");
        menus[2].getItems().addAll(menuItems[6]);
        menus[3] = new Menu("About");
        menus[3].getItems().addAll(menuItems[7]);
        menus[4] = new Menu("Account");
        menus[4].getItems().addAll(menuItems[8]);

        // menubar and add menues to it
        MenuBar menuBar = new MenuBar();
        for (Menu menu : menus)
            menuBar.getMenus().add(menu);

        Label name = new Label("Hello, " + refc.user.fname);
        name.setMinWidth(710);
        name.setMinHeight(200);
        name.setAlignment(Pos.CENTER);
        name.setBackground(
                new Background(
                        new BackgroundFill(Color.rgb(0, 0, 0, .2), new CornerRadii(0), new Insets(0))));
        name.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        name.setTextFill(Color.WHITE);

        VBox mainContainer = new VBox();
        mainContainer.getChildren().add(menuBar);

        Scene scene = new Scene(mainContainer, 700, 500);

        stage.setTitle("Football Compitition");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private EventHandler<ActionEvent> menuItemAction() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String text = mItem.getText();
                if ("Log Out".equalsIgnoreCase(text)) {
                    if (!refc.alerts.confirmationAlert("Are you sure to log out?"))
                        return;
                    refc.primaryStage.close();
                    refc.welcome.signIn();
                } else if ("View Profile".equalsIgnoreCase(text)) {
                    showProfileDetails();
                } else if ("Update Profile".equalsIgnoreCase(text)) {
                    updateProfile();
                } else if ("Export Records".equalsIgnoreCase(text)) {
                    refc.primaryStage.close();
                    new RecordsWindow(refc).exportWindow();
                } else if ("About Us".equalsIgnoreCase(text)) {
                    aboutWindow();
                } else {
                    refc.primaryStage.close();
                    new RecordsWindow(refc).crudWindow();
                }
            }
        };
    }

    public void aboutWindow() {
        Stage stage = new Stage();

        Label myCompLabel = new Label("Premium Football Compitition");
        myCompLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label version = new Label("Version 1.2.34");

        VBox mainContainer = getVbox();
        mainContainer.getChildren().addAll(myCompLabel, version);

        Scene scene = new Scene(mainContainer, 500, 200);

        stage.setTitle("About");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private void showProfileDetails() {
        Stage stage = new Stage();
        Label[] labels = new Label[3];
        String[] labelsVal = { "Username:              " + refc.user.uname,
                "First Name:              " + refc.user.fname,
                "Last Name:              " + refc.user.lname };
        Button okBtn = new Button("Ok");
        okBtn.setOnAction(event -> {
            stage.close();
        });

        VBox mainContainer = getVbox();

        for (int i = 0; i < labelsVal.length; i++) {
            labels[i] = new Label(labelsVal[i]);
            mainContainer.getChildren().add(labels[i]);
        }
        mainContainer.getChildren().addAll(okBtn);
        Scene scene = new Scene(mainContainer, 500, 200);

        stage.setTitle("Your Profile");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    private void updateProfile() {
        Stage stage = new Stage();
        VBox mainContainer = getVbox();
        Label[] labels = new Label[2];
        String[] labelsVal = { "First Name", "Last Name" };

        TextField[] fields = new TextField[2];
        for (int i = 0; i < fields.length; i++) {
            labels[i] = new Label(labelsVal[i]);
            fields[i] = new TextField();
            fields[i].setMaxWidth(300);
            mainContainer.getChildren().add(labels[i]);
            mainContainer.getChildren().add(fields[i]);
        }

        Button[] btns = new Button[2];
        String[] btnName = { "Ok", "Cancel" };
        for (int i = 0; i < btnName.length; i++) {
            btns[i] = new Button(btnName[i]);
            btns[i].setOnAction(event -> {
                String btnText = ((Button) event.getSource()).getText();
                if (btnText.equalsIgnoreCase("Cancel")) {
                    stage.close();
                    return;
                }
                // if ok button is pressed and any field is empty
                if (refc.alerts.isEmptyField(fields)) {
                    refc.alerts.infoAlert("Please fill all fields!!");
                    return;
                }
                boolean result = DbHandler.editprofile(refc.user.uname, fields[0].getText(),
                        fields[1].getText());
                // if error updating profile
                if (!result) {
                    refc.alerts.infoAlert(("The profile cannot be updated try again..."));
                    return;
                }
                refc.alerts.infoAlert(("The profile updated successfully"));
                stage.close();
            });
        }

        mainContainer.getChildren().add(0, new Label(refc.user.uname));
        mainContainer.getChildren().add(btns[0]);
        mainContainer.getChildren().add(btns[1]);

        Scene scene = new Scene(mainContainer, 600, 400);

        stage.setTitle("Update Profile");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public VBox getVbox() {
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(30, 30, 30, 30));
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.CENTER);
        return mainContainer;
    }
}
