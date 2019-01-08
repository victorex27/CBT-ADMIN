/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class NextFXMLController implements Initializable {

    @FXML
    private static Button saveButton;
    @FXML
    private static Button nextButton;
    private static String fxmlString;
    private static boolean isExam;
    private Teacher teacher = (Teacher) THomeFXMLDocumentController.person;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public static void setIsExam(boolean state) {

        isExam = state;
    }

    public static void setNextFrame(String _fxmlString) {

        fxmlString = _fxmlString;
    }

    public static void hideSaveButton() {
        saveButton.setVisible(false);
    }

    public static void hideNextButton() {
        nextButton.setVisible(false);
    }

    @FXML
    public void onNextClicked(ActionEvent evt) throws IOException {

        VBox smallVBox = new VBox();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlString));

        AnchorPane xPane = (AnchorPane) loader.load();

        smallVBox.getChildren().add(0, xPane);

        loader = new FXMLLoader(getClass().getResource("NextFXML.fxml"));

        AnchorPane nPane = (AnchorPane) loader.load();

        smallVBox.getChildren().add(1, nPane);

        VBoxController.setTopMenu(TopMenuEnum.TOP);
        VBoxController.changeBox(smallVBox);

    }

    @FXML
    public void onSaveClicked(ActionEvent evt) throws IOException, SQLException, ClassNotFoundException {

        //teacher.deleteFromDB(SimpleConnection.getConnection());
        saveToDb();
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlString));
        VBox smallVBox = new VBox();
        AnchorPane xPane = (AnchorPane) loader.load();
        
        smallVBox.getChildren().add(0, xPane);
        
            Pane pane = new Pane();
            pane.getChildren().add(smallVBox);
            VBoxController.changeBox(pane);
         */
        
        
    }

    private void saveToDb() throws SQLException, IOException {

        if (isExam) {
            teacher.addToExamDatabase("1:09:00");
            VBoxController.setTopMenu(TopMenuEnum.TOP);
        } else {
            teacher.addToAssignmentDatabase("1:09:00");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
        }
        //ArrayList<Question> q = ExamChooserFXMLController.getQuestion();
        

            
            AnchorPane next = new AnchorPane();
            Label label = new Label("Operation successful...");

            next.getChildren().add(label);
            VBox smallVBox = new VBox();

            smallVBox.getChildren().add(0, next);

            VBoxController.changeBox(smallVBox);

    }

}
