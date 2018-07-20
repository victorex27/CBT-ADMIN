
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * FXML Controller class
 *
 * @author USER
 */

/**
 * The Vbox is on a stackpane with a Pane
 * The Pane contains the upload button and the drag and drop
 * the vbox contains the list of the subjects which is clickable to take you to the Pane
 * 
 * */
public class THomeFXMLDocumentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label fullName;
    @FXML
    private Pane rightPane;
    @FXML
    private VBox vBox;
    @FXML
    private Pane dragPane;

    @FXML
    StackPane stackPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;

    @FXML
    private Button fileChooserButton;

    @FXML
    private Button submit;
    @FXML
    public Label errorLabel;
    @FXML
    ListView listView;

    @FXML
    Button saveToDbButtuon;

    private String filePath;

    private static Teacher teacher;
    private static Course currentCourse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // initialize fullName label

            fullName.setText(teacher.getFullName());

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
            
            
            
            showTeacherView(teacher);

        } catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This displays the view only for teachers * *
     */
    private void showTeacherView(Teacher teacher) throws ClassNotFoundException, Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentListFXML.fxml"));
        AnchorPane anchorPane = (AnchorPane) loader.load();
                StudentListFXMLController controller = loader.getController();
                controller.setStudentList(2);
                //controller.setCourseid(1);
                vBox.getChildren().add(anchorPane);
                
        
        /*
        teacher.getCourses().forEach(a -> {

            Hyperlink link = new Hyperlink();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CardViewFXML.fxml"));

            try {
                AnchorPane anchorPane = (AnchorPane) loader.load();
                CardViewFXMLController controller = loader.getController();

                controller.setContents(a.getCourseCode(), a.getCourseTitle());
                vBox.getChildren().add(anchorPane);

                anchorPane.setOnMouseClicked(e -> {

                    setCurrentCourse(a);
                    dragPane.setVisible(true);
                    vBox.setVisible(false);

                });

            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        
        });
        
        */

    }

    public static void setPerson(Teacher _person) {

        teacher = _person;
    }

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
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

                    teacher.setExam(currentCourse, filePath);
                    showScrollPane();
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
            teacher.setExam(currentCourse, this.filePath);
            showScrollPane();
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }

    }

    public void showScrollPane() {

        ArrayList<Question> questionsList = teacher.getAllQuestions();

        ObservableList<Pane> data = FXCollections.observableArrayList();
        listView.setVisible(true);
        saveToDbButtuon.setVisible(true);
        //dragPane.setVisible(false);

        questionsList.forEach(e -> {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionFXML.fxml"));
                AnchorPane anchorPane = (AnchorPane) loader.load();
                QuestionFXMLController controller = loader.getController();

                controller.setQuestionObject(e);
                controller.setQuestion(e.getQuestionProperty());
                controller.setOptionA(e.getAProperty());
                controller.setOptionB(e.getBProperty());
                controller.setOptionC(e.getCProperty());
                controller.setOptionD(e.getDProperty());
                controller.setOptionE(e.getEProperty());

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
            teacher.addToDatabase();
            listView.setVisible(false);
            saveToDbButtuon.setVisible(false);
            dragPane.setVisible(false);
            vBox.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
