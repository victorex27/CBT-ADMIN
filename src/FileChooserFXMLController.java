/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FileChooserFXMLController implements Initializable {

    @FXML
    private Button button;

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
    private Label timeLabel;

    @FXML
    private ChoiceBox choiceBox;

    private String filePath;

    private Teacher teacher = (Teacher) THomeFXMLDocumentController.person;

    private Course course;

    private String selectedDate, selectedHour, selectedMinute;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onCustomClickEvent(ActionEvent event) throws Exception {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) button.getScene().getWindow());

        if (file != null) {

            filePath = file.getAbsolutePath();
            setQuestion(filePath);
        }

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
                    System.out.println("Custom drag error" + ex.getMessage());
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

    private void setQuestion(String filePath) throws Exception {

        teacher.setExam(course, filePath);
        VBox smallVBox = new VBox();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExamChooserFXML.fxml"));

        AnchorPane xPane = (AnchorPane) loader.load();
        ExamChooserFXMLController controller2 = loader.getController();
        controller2.setQuestion(teacher.getAllQuestions());
        smallVBox.getChildren().add(0, xPane);

        //NextFXMLController.hideNextButton();
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("NextFXML.fxml"));

        AnchorPane nPane = (AnchorPane) loader2.load();
        // NextFXMLController controller2 = loader.getController();

        smallVBox.getChildren().add(1, nPane);

        VBoxController.setTopMenu(TopMenuEnum.TOP);
        VBoxController.changeBox(smallVBox);

    }

}
