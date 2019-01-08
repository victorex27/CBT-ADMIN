/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class TopMenuFXMLController implements Initializable {

    private static boolean isExam;

    private VBox smallVBox;
    private static Teacher teacher = (Teacher) THomeFXMLDocumentController.person;

    private FXMLLoader loader;
    
    @FXML 
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        smallVBox = new VBox();
        if (!isExam) {
        
            pane.setVisible(false);
        }
    }

    @FXML
    private void onDeleteClicked(ActionEvent evt) throws IOException, SQLException, ClassNotFoundException {

        if (isExam) {
            VBoxController.setTopMenu(TopMenuEnum.TOP);

            teacher.deleteFromDB(SimpleConnection.getConnection());
            AnchorPane next = new AnchorPane();
            Label label = new Label("Questions have been deleted...");

            next.getChildren().add(label);

            smallVBox.getChildren().add(0, next);

            VBoxController.changeBox(smallVBox);
        }

    }

    @FXML
    private void onUploadClicked(ActionEvent evt) throws IOException {

        VBoxController.setTopMenu(TopMenuEnum.TOP);

        loader = new FXMLLoader(getClass().getResource("FileChooserFXML.fxml"));
        AnchorPane fileChooser = (AnchorPane) loader.load();
        //NextFXMLController controller4 = loader.getController();
        smallVBox.getChildren().add(0, fileChooser);

        VBoxController.changeBox(smallVBox);

    }

    @FXML
    private void onEditClicked(ActionEvent evt) throws IOException, Exception {

        if (isExam) {
            VBoxController.setTopMenu(TopMenuEnum.TOP);

            loader = new FXMLLoader(getClass().getResource("ExamChooserFXML.fxml"));
            AnchorPane examChooserPane = (AnchorPane) loader.load();
            ExamChooserFXMLController controller2 = loader.getController();
            teacher.retrieveQuestions();
            // teacher.getCourseRegId(course);
            controller2.setQuestion(Teacher.getAllQuestions());
            smallVBox.getChildren().add(0, examChooserPane);

            loader = new FXMLLoader(getClass().getResource("NextFXML.fxml"));

            AnchorPane nPane = (AnchorPane) loader.load();

            smallVBox.getChildren().add(1, nPane);

            VBoxController.changeBox(smallVBox);

        }
    }

    public static void setIsExam(boolean state) {

        isExam = state;
    }

}
