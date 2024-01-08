import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class RecordsWindow {
    GUI refc;
    ObservableList<CompititorRecord> records;
    TableView<CompititorRecord> table;
    EventHandler<ActionEvent> btnHandler;

    public RecordsWindow(GUI refc) {
        this.refc = refc;
        records = DbHandler.readScores(refc.user.uname);
        btnHandler = CompBtnAction();
        table = createTableView();
    }

    public void crudWindow() {
        Stage stage = refc.primaryStage;

        Label heading = new Label("Records");
        heading.setPrefWidth(700);
        heading.setAlignment(Pos.CENTER);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        Button[] btns = new Button[4];
        String[] btntxt = { "Add Record", "Edit Record", "Delete Record", "Close" };
        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setSpacing(10);

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new Button(btntxt[i]);
            btnContainer.getChildren().add(btns[i]);
            // add event listener on all btns
            btns[i].setOnAction(btnHandler);
        }

        VBox mainContainer = getVbox();
        mainContainer.getChildren().addAll(heading, table, btnContainer);

        Scene scene = new Scene(mainContainer, 700, 500);

        stage.setTitle("Records");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public void exportWindow() {
        Stage stage = refc.primaryStage;
        // allow multiple row selections
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Label heading = new Label("Records");
        heading.setPrefWidth(700);
        heading.setAlignment(Pos.CENTER);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        Button[] btns = new Button[2];
        String[] btnName = { "Export Records", "Cancel" };
        for (int i = 0; i < btnName.length; i++) {
            btns[i] = new Button(btnName[i]);
            btns[i].setOnAction(btnHandler);
        }

        VBox mainContainer = getVbox();
        mainContainer.getChildren().addAll(heading, table, btns[0], btns[1]);

        Scene scene = new Scene(mainContainer, 700, 500);

        stage.setTitle("Records");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public TableView<CompititorRecord> createTableView() {
        TableView<CompititorRecord> table;

        table = new TableView<>();
        table.setItems(records);

        TableColumn<CompititorRecord, String> column;
        String[] colsHeader = { "User Name", "Date", "Score 1", "Score 2", "Score 3", "Score 4" };
        String[] compFields = { "uname", "date", "score1", "score2", "score3", "score4" };

        for (int i = 0; i < 6; i++) {
            column = new TableColumn<>(colsHeader[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(compFields[i]));
            column.setPrefWidth(120);
            table.getColumns().add(column);
        }

        return table;
    }

    private EventHandler<ActionEvent> CompBtnAction() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Button btn = (Button) event.getSource();
                String btnText = btn.getText();
                CompititorRecord record = table.getSelectionModel().getSelectedItem();
                if ("Delete Record".equalsIgnoreCase(btnText)) {
                    if (!refc.isStaffAcc) {
                        refc.alerts.infoAlert("Only staff is allowed to perform this action!!");
                        return;
                    }

                    if (record == null) {
                        refc.alerts.infoAlert("Please select a record first!!");
                        return;
                    }

                    if (!refc.alerts.confirmationAlert("Are you sure to Delete Selected Record?"))
                        return;

                    DbHandler.deleteUserScores(record.uname, record.id);
                    refc.alerts.infoAlert("Record deleted successfully");
                    refc.primaryStage.close();
                    new RecordsWindow(refc).crudWindow();
                    ;
                } else if ("Edit Record".equalsIgnoreCase(btnText)) {
                    if (!refc.isStaffAcc) {
                        refc.alerts.infoAlert("Only staff is allowed to perform this action!!");
                        return;
                    }

                    if (record == null) {
                        refc.alerts.infoAlert("Please select a record first!!");
                        return;
                    }
                    if (!refc.alerts.confirmationAlert("Are you sure to Edit Selected Record?"))
                        return;

                    editRecord(record.id);
                } else if ("Add Record".equalsIgnoreCase(btnText)) {
                    if (!refc.isStaffAcc) {
                        refc.alerts.infoAlert("Only staff is allowed to perform this action!!");
                        return;
                    }
                    refc.primaryStage.close();
                    addRecord();
                } else if ("Export Records".equalsIgnoreCase(btnText)) {
                    exportRecords();
                } else {
                    refc.primaryStage.close();
                    new HomeWindow(refc);
                }
            }
        };
    }

    public void exportRecords() {
        // get all selected rows from table
        ObservableList<CompititorRecord> selectedRows = table.getSelectionModel().getSelectedItems();

        // if no rows selected return
        if (selectedRows.size() == 0) {
            refc.alerts.infoAlert("Please select some rows first...");
            return;
        }

        // confirm the user for exporting if no then return
        if (!refc.alerts.confirmationAlert("Are you sure to export these records"))
            return;

        Stage stage = new Stage();

        Label[] labels = new Label[3];
        String[] labelsName = { "File Path", "File Name", ".csv" };
        TextField[] fields = new TextField[2];
        Button[] btns = new Button[3];
        String[] btnsName = { "Choose", "Ok", "Cancel" };

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        // add content to grid pane
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label(labelsName[i]);
            btns[i] = new Button(btnsName[i]);
            if (i < fields.length) {
                fields[i] = new TextField();
                gridPane.add(labels[i], 0, i);
                gridPane.add(fields[i], 1, i, 2, 1);
                if (i < 1)
                    gridPane.add(btns[i], 3, 0);
                else {
                    labels[2] = new Label(labelsName[2]);
                    gridPane.add(labels[2], 3, i);
                }
            }
        }
        gridPane.add(btns[1], 2, 2);
        gridPane.add(btns[2], 3, 2);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        for (Button btn : btns) {
            btn.setOnAction(e -> {
                String btnText = ((Button) e.getSource()).getText();
                if ("Ok".equalsIgnoreCase(btnText)) {
                    if (refc.alerts.isEmptyField(fields)) {
                        refc.alerts.infoAlert("Please fill all the fields");
                        return;
                    }
                    // write data to file
                    try {
                        String filePathName = fields[0].getText() + "\\" + fields[1].getText() + ".csv";
                        FileWriter writer = new FileWriter(filePathName);
                        // create a comma separated string
                        String commaSepStr;
                        String headerStr = "User Name,Weight,Temperature,Blood Pressure,Notes\n";
                        writer.write(headerStr);
                        for (int i = 0; i < selectedRows.size(); i++) {
                            commaSepStr = selectedRows.get(i).uname + "," + selectedRows.get(i).score1 + ","
                                    + selectedRows.get(i).score2 + "," + selectedRows.get(i).score3 + ","
                                    + selectedRows.get(i).score4 + "\n";
                            writer.write(commaSepStr);
                        }
                        writer.close();
                        refc.alerts.infoAlert("Records exportes successfully.");
                        stage.close();
                    } catch (Exception exception) {
                        refc.alerts.infoAlert("Error exporting records");
                        exception.printStackTrace();
                    }
                } else if ("Choose".equalsIgnoreCase(btnText)) {
                    File selectedDirectory = directoryChooser.showDialog(new Stage());

                    fields[0].setText(selectedDirectory.getAbsolutePath());
                } else {
                    stage.close();
                }
            });
        }

        Scene scene = new Scene(gridPane, 400, 250);

        stage.setTitle("Choose path");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public void addRecord() {
        Stage stage = refc.primaryStage;
        stage.setTitle("Add Record");
        stage.setResizable(true);
        stage.setScene(getRecordForm("Add", ""));
        stage.show();
    }

    public void editRecord(String recordId) {
        Stage stage = refc.primaryStage;
        stage.setTitle("Edit Record");
        stage.setResizable(true);
        stage.setScene(getRecordForm("Edit", recordId));
        stage.show();
    }

    public Scene getRecordForm(String operation, String recordId) {
        Label[] labels = new Label[6];
        String[] labelsTxt = { "Date", "Score 1", "Score 2", "Score 3", "Score 4", "Competitor Name" };
        TextField[] fields = new TextField[6];

        GridPane gridPane = new GridPane();

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label(labelsTxt[i]);
            fields[i] = new TextField();
            gridPane.add(labels[i], 0, i);
            gridPane.add(fields[i], 1, i, 4, 1);

        }
        fields[0].setText("helel");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        gridPane.add(datePicker, 1, 0, 4, 1);

        Button[] btns = new Button[2];
        String[] btnName = { "Ok", "Cancel" };
        for (int i = 0; i < btnName.length; i++) {
            btns[i] = new Button(btnName[i]);
            btns[i].setOnAction(event -> {
                String btnText = ((Button) event.getSource()).getText();
                if (btnText.equalsIgnoreCase("Cancel")) {
                    refc.primaryStage.close();
                    new RecordsWindow(refc).crudWindow();
                    return;
                }
                if (refc.alerts.isEmptyField(fields)) {
                    refc.alerts.infoAlert("Please fill all the fields");
                    return;
                }

                LocalDate localDate = datePicker.getValue();

                System.out.println(fields[5].getText());

                if (operation.equals("Add")) {
                    DbHandler.saveUserScores(fields[5].getText(), fields[1].getText(), fields[2].getText(),
                            fields[3].getText(), fields[4].getText(),
                            java.sql.Date.valueOf(localDate));
                    refc.alerts.infoAlert("Record has been added successfully)");
                } else {
                    DbHandler.updateUserScores(fields[5].getText(), fields[1].getText(), fields[2].getText(),
                            fields[3].getText(), fields[4].getText(),
                            java.sql.Date.valueOf(localDate), recordId);
                    refc.alerts.infoAlert("Record has been edited successfully)");
                }
            });
        }

        gridPane.add(btns[0], 0, 6, 2, 1);
        gridPane.add(btns[1], 1, 6, 2, 1);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(30, 30, 30, 30));

        Scene scene = new Scene(gridPane, 610, 300);
        return scene;
    }

    public VBox getVbox() {
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(30, 30, 30, 30));
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.CENTER);
        return mainContainer;
    }
}
