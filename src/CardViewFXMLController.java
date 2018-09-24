/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CardViewFXMLController implements Initializable {

    @FXML
    private Course course;

    private static int courseId;

    @FXML
    private Button button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onClick(ActionEvent evt) {

        try {
            System.out.println("Mouse click detected");
            courseId = course.getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            CourseDashboardFXMLController controller = loader.getController();
            controller.setCourse(course);

            ScreenController.changeScreen(pane);

        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setCourse(Course course) {
        this.course = course;

        button.setText(course.getCourseCode() + "\n" + course.getCourseTitle());
    }

    public static int getCourseId() {

        return courseId;
    }

}
