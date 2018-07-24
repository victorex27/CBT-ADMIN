/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class QuestionDashboardFXMLController implements Initializable {

    @FXML
    private Button fileChooserButton;

    @FXML
    private Button submit;

    @FXML
    private Pane rightPane;
    @FXML
    private VBox vBox;
    @FXML
    private Pane dragPane;
    @FXML
    public Label errorLabel;
    @FXML
    Button saveToDbButtuon;
    @FXML
    private Label fullName;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;

    @FXML
    private ChoiceBox choiceBox;
    @FXML 
    private Pane datePane;

    private String filePath;

    @FXML
    StackPane stackPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;

    @FXML
    ListView listView;

    @FXML
    GridPane gridPane;

    //assignment link without file upload.
    @FXML
    Hyperlink assignmentLink;

    private enum Type {
        EXAM, ASSIGNMENT;
    }

    private Type choiceType;
    private Teacher teacher = THomeFXMLDocumentController.getTeacher();

    private Course course;

    private String selectedDate,selectedHour, selectedMinute;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // TODO
        // initialize fullName label
        fullName.setText(teacher.getFullName());

        listView.setVisible(false);
        saveToDbButtuon.setVisible(false);

        choiceBox.getItems().addAll("Exam", "Assignment");
        choiceBox.getSelectionModel().isSelected(0);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(1)) {

                assignmentLink.setVisible(true);
                datePane.setVisible(true);
                choiceType = Type.ASSIGNMENT;
            } else {

                assignmentLink.setVisible(false);
                datePane.setVisible(false);
                choiceType = Type.EXAM;

            }
        });
        //set to todays date
        //datePicker.setValue();
        datePicker.setOnAction( e->{
        
            selectedDate = datePicker.getValue().toString();
            //System.out.println("Selected Date= "+selectedDate);
        
        });
        
        
        //selectedHour
        hourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            
                selectedHour = newValue;
        });
        selectedHour = "00";
        selectedMinute = "00";
        minuteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            
                selectedMinute = newValue;
        });
        
        /**
         * Clean this up*
         */
        FileChooser fileChooser = new FileChooser();

        fileChooserButton.setOnAction((ActionEvent e) -> {

            File file = fileChooser.showOpenDialog((Stage) fileChooserButton.getScene().getWindow());

            if (file.isDirectory() || (file == null)) {

                try {
                    throw new Exception("This is a directory");
                } catch (Exception ex) {
                    errorLabel.setText(ex.getMessage());
                }
            }
            filePath = file.getAbsolutePath();

        });

    }

    private void setQuestion(String filePath) throws Exception {

        teacher.setExam(course, filePath);
        showScrollPane();

    }

    @FXML
    public void onCustomDragEvent(DragEvent event) {

        Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, "File Upload Starting....", "");

        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;

            for (File file : db.getFiles()) {
                filePath = file.getAbsolutePath();
                //System.out.println(filePath);

                try {

                    setQuestion(filePath);

                } catch (Exception ex) {
                    errorLabel.setText(ex.getMessage());
                }

            }
        }
        event.setDropCompleted(success);
        event.consume();

    }

    @FXML
    public void onCustomDragOver(DragEvent event) {

        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }

    }

    @FXML
    public void onSubmiButtonClicked(ActionEvent evt) {

        try {
            setQuestion(filePath);
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }

    }

    public void showScrollPane() {

        ArrayList<Question> questionsList = teacher.getAllQuestions();

        ObservableList<Pane> data = FXCollections.observableArrayList();
        choiceBox.setVisible(false);
        assignmentLink.setVisible(false);
        listView.setVisible(true);
        saveToDbButtuon.setVisible(true);
        //dragPane.setVisible(false);

        questionsList.forEach(e -> {

            try {
                AnchorPane anchorPane = null;

                if (choiceType.equals(Type.ASSIGNMENT)) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentFXML.fxml"));
                    anchorPane = (AnchorPane) loader.load();
                    AssignmentFXMLController controller = loader.getController();

                    controller.setQuestionObject(e);
                    controller.setQuestion(e.getQuestionProperty());

                } else if (choiceType.equals(Type.EXAM)) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionFXML.fxml"));
                    anchorPane = (AnchorPane) loader.load();
                    QuestionFXMLController controller = loader.getController();

                    controller.setQuestionObject(e);
                    controller.setQuestion(e.getQuestionProperty());

                    controller.setOptionA(e.getAProperty());
                    controller.setOptionB(e.getBProperty());
                    controller.setOptionC(e.getCProperty());
                    controller.setOptionD(e.getDProperty());
                    controller.setOptionE(e.getEProperty());

                }

                //stackPane.getChildren().add(anchorPane);
                //xPane.getChildren().add(anchorPane);
                data.add(anchorPane);

            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        listView.setItems(data);

    }

    @FXML
    public void saveToDB(ActionEvent evt) {

        try {
            if (choiceType.equals(Type.ASSIGNMENT)) {

                teacher.addToAssignmentDatabase(getDeadline());

            } else if (choiceType.equals(Type.EXAM)) {

                teacher.addToExamDatabase();
            }

            listView.setVisible(false);
            saveToDbButtuon.setVisible(false);
            dragPane.setVisible(false);
            vBox.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    private String getDeadline(){
    
        return selectedDate+" "+selectedHour+":"+selectedMinute+":00";
    }

}
