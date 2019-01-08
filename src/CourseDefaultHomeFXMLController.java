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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CourseDefaultHomeFXMLController implements Initializable {
    
    
    @FXML private Label result;
    
    @FXML private Label courseTitle;
    @FXML private Label courseCode;

    private static Course course;
    private static String courseCodeString;
    private static String courseTitleString;
    
    private VBox smallVBox ;
    private static THomeFXMLDocumentController parentController;
    
     public static void setParentController(THomeFXMLDocumentController _parentController){
        
        parentController = _parentController;
        System.out.println("controller is set already");
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        BackButtonController.setPrevious("THomeFXMLDocument.fxml");
        courseCode.setText( course.getCourseCode());
        courseTitle.setText( course.getCourseTitle());
        smallVBox = new VBox();
    }    

    public static void setCourse(Course c){
    
        course = c;
    }
    @FXML
    public void onAssessmentClick(ActionEvent evt){
    
        try {
            AssessmentFXMLController.setParentController(parentController);
            parentController.changeThirdLinkName("Assessment");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssessmentFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            AssessmentFXMLController con = loader.getController();
            con.setCourse(course);
           
            
            
            
            smallVBox.getChildren().add(0, anchor);

            VBoxController.changeBox(smallVBox);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    public void onLibraryClick(ActionEvent evt){
        
        try {
            ReadingListFXMLController.setParentController(parentController);
            parentController.changeThirdLinkName("Library");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingListFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            ReadingListFXMLController con = loader.getController();
            con.setDocumentId(course.getRegId());
            //con.setCourse(course);
           smallVBox.getChildren().add(0, anchor);

            VBoxController.changeBox(smallVBox);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML
    public void onAssinmentClick(ActionEvent event){
    
        try {
            AssignmentUploadFXMLController.setParentController(parentController);
            parentController.changeThirdLinkName("Assignment");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentUploadFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            AssignmentUploadFXMLController con = loader.getController();
            con.setCourse(course);
            smallVBox.getChildren().add(0, anchor);

            VBoxController.changeBox(smallVBox);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Course getCourse() {
        return course;
    }

   
    
}
