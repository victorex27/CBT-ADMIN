/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CourseDashboardFXMLController implements Initializable {

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher aTeacher) {
        teacher = aTeacher;
    }

    /**
     * Initializes the controller class.
     */
    private Course course;

    private static Teacher teacher;

    @FXML
    Label courseTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCourse(Course course) {

        this.course = course;
        courseTitle.setText(course.getCourseTitle());
    }

    @FXML
    public void onScoresheetCliked(ActionEvent evt) {

        try {
          
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreSheetDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            ScoreSheetDashboardFXMLController controller = loader.getController();
            controller.setStudentList(course.getId());

            ScreenController.changeScreen(pane);

        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CourseDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void onUploadQuestionCliked(ActionEvent evt) {

        try {
            System.out.println("Mouse click detected");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            QuestionDashboardFXMLController controller = loader.getController();
            controller.setCourse(course);

            ScreenController.changeScreen(pane);

        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void onUploadDocumentCliked(ActionEvent evt) {

        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DocumentDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            DocumentDashboardFXMLController controller = loader.getController();
            controller.setCourseid(course.getId());
            

            ScreenController.changeScreen(pane);

        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
