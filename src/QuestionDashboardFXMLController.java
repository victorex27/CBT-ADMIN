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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
    private String filePath;
    
  
    @FXML
    StackPane stackPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;

    
   
    @FXML
    ListView listView;

   
    @FXML GridPane gridPane;
    
    private Teacher teacher = THomeFXMLDocumentController.getTeacher();
    
    private Course course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

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

                    teacher.setExam(course, filePath);
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
            teacher.setExam(course, this.filePath);
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

    public void setCourse(Course course) {
        this.course = course;
    }

}
