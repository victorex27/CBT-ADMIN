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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CourseCardViewFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button button;
    
    private VBox smallVBox;

    private Student student;
   
    private Course course;
   
    private static THomeFXMLDocumentController parentController;
    
    public static void setParentController(THomeFXMLDocumentController _parentController){
        
        parentController = _parentController;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        smallVBox = new VBox();
        
    }
    
    

    @FXML
    public void onClick(ActionEvent evt) {

        try {

            CourseDefaultHomeFXMLController.setParentController(parentController);
            CourseDefaultHomeFXMLController.setCourse(course);
            parentController.changeSecondLinkName(course.getCourseCode());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDefaultHomeFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            
            
            
            Pane emptyPane = new Pane();
            smallVBox.getChildren().add(0, emptyPane);
            smallVBox.getChildren().add(1, pane);

            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            VBoxController.changeBox(smallVBox);

        } catch (IOException ex) {
            Logger.getLogger(CourseCardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public  void setCourse(Course course) {
        this.course = course;
        String text = course.getCourseCode()+"\n"+course.getCourseTitle();
        button.setText(text);
        
    }

   

}
