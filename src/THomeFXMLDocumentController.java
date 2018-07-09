
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
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
    private Button fileChooserButton;
    
    @FXML
    private Button submit;
    @FXML
    public Label errorLabel;
    
    private String filePath ;

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
            
            File file = fileChooser.showOpenDialog( (Stage)fileChooserButton.getScene().getWindow());
            
            if(file.isDirectory() || (file == null)){
            
                try {
                    throw new Exception("This is a directory");
                } catch (Exception ex) {
                    errorLabel.setText(ex.getMessage());
                }
            }
            filePath = file.getAbsolutePath();
            
          
            
            
        });
                showTeacherView(teacher);

            
        } 
        catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    /**
     * This displays the view only for teachers * *
     */
    private void showTeacherView(Teacher teacher) throws ClassNotFoundException, Exception {

        teacher.getCourses().forEach(a -> {

            Hyperlink link = new Hyperlink();

            link.setText(a.getCourseCode());
            link.setOnAction(e -> {

                setCurrentCourse(a);
                dragPane.setVisible(true);
                vBox.setVisible(false);
            });

            vBox.getChildren().add(link);

        });

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

                    teacher.setExam(currentCourse.getCourseCode(), filePath);
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
            teacher.setExam(currentCourse.getCourseCode(), this.filePath);
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    
    }

}
