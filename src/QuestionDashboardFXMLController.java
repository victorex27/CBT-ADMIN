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
    ListView listView;
    @FXML
    StackPane stackPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;

    @FXML
    VBox listViewVBox;

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
        
        BackButtonController.setPrevious("CourseDashboardFXML.fxml");
        // initialize fullName label
        fullName.setText(teacher.getFullName());

        listViewVBox.setVisible(false);
        saveToDbButtuon.setVisible(false);

        choiceBox.getItems().addAll("Exam", "Assignment");
        choiceBox.getSelectionModel().isSelected(0);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(1)) {

                //assignmentLink.setVisible(true);
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
        System.out.println("Custom drag 1");
        if (db.hasFiles()) {
            success = true;

            System.out.println("Custom drag 2");
            for (File file : db.getFiles()) {
                System.out.println("Custom drag 3");
                filePath = file.getAbsolutePath();
                //System.out.println(filePath);

                System.out.println("Custom drag 4");
                try {

                    System.out.println("Custom drag 5");
                    setQuestion(filePath);

                    System.out.println("Custom drag 6");
                } catch (Exception ex) {
                    System.out.println("Custom drag error"+ex.getMessage());
                    errorLabel.setText(ex.getMessage());
                }

                System.out.println("Custom drag 7");
            }
            System.out.println("Custom drag 8");
        }
        System.out.println("Custom drag 9");
        event.setDropCompleted(success);
        event.consume();
        System.out.println("Custom drag 10");

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
        System.out.println("Show scroll pane 1");
        ArrayList<Question> questionsList = teacher.getAllQuestions();

        ObservableList<Pane> data = FXCollections.observableArrayList();
        choiceBox.setVisible(false);
        assignmentLink.setVisible(false);
        listViewVBox.setVisible(true);
        saveToDbButtuon.setVisible(true);
        //dragPane.setVisible(false);

        System.out.println("Show scroll pane 2");
        questionsList.forEach(e -> {

            try {
                AnchorPane anchorPane = null;

                if (choiceType.equals(Type.ASSIGNMENT)) {

                    System.out.println("Show scroll pane 3");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentFXML.fxml"));
                    anchorPane = (AnchorPane) loader.load();
                    AssignmentFXMLController controller = loader.getController();

                    controller.setQuestionObject(e);
                    controller.setQuestion(e.getQuestionProperty());

                } else if (choiceType.equals(Type.EXAM)) {

                    System.out.println("Show scroll pane 4");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionFXML.fxml"));
                    anchorPane = (AnchorPane) loader.load();
                    QuestionFXMLController controller = loader.getController();
                    controller.setListView(listView);

                    controller.setQuestionObject(e);
                    controller.setQuestion(e.getQuestionProperty());

                    controller.setOptionA(e.getAProperty());
                    controller.setOptionB(e.getBProperty());
                    controller.setOptionC(e.getCProperty());
                    controller.setOptionD(e.getDProperty());
                    controller.setOptionE(e.getEProperty());

                }

                System.out.println("Show scroll pane 5");
                //stackPane.getChildren().add(anchorPane);
                //xPane.getChildren().add(anchorPane);
                data.add(anchorPane);
                //listViewVBox.getChildren().add(anchorPane);

            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Show scroll pane 6");
            listView.setItems(data);
            System.out.println("Show scroll pane 7");
        });

        System.out.println("Show scroll pane 8");

    }

    @FXML
    public void saveToDB(ActionEvent evt) {

        try {
            if (choiceType.equals(Type.ASSIGNMENT)) {

                teacher.addToAssignmentDatabase(getDeadline());

            } else if (choiceType.equals(Type.EXAM)) {

                teacher.addToExamDatabase(getDuration());
            }

            listViewVBox.setVisible(false);
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
    
    private String getDuration(){
    
        return selectedHour+":"+selectedMinute+":00";
    }
    
    private String getDeadline(){
    
        return selectedDate+" "+selectedHour+":"+selectedMinute+":00";
    }

}
