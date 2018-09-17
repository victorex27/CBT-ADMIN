
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static Teacher getTeacher() {
        return teacher;
    }

    

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label fullName;
    

    @FXML
    StackPane stackPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane scrollAnchorPane;

    
    @FXML
    public Label errorLabel;
    @FXML
    ListView listView;

    @FXML
    Button saveToDbButtuon;
    
    @FXML GridPane gridPane;

   

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
            
            /*
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
            
            */
            
            showTeacherView(teacher);

        } catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This displays the view only for teachers * *
     */
    private void showTeacherView(Teacher teacher) throws ClassNotFoundException, Exception {

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentListFXML.fxml"));
  //      AnchorPane anchorPane = (AnchorPane) loader.load();
    //            StudentListFXMLController controller = loader.getController();
      //          controller.setStudentList(2);
                //controller.setCourseid(1);
        //        vBox.getChildren().add(anchorPane);
        
        AtomicInteger count = new AtomicInteger(1);
        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0);
                
        
        
        teacher.getCourses().forEach(a -> {

            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CardViewFXML.fxml"));

            try {
                AnchorPane anchorPane = (AnchorPane) loader.load();
                CardViewFXMLController controller = loader.getController();
                
                

                controller.setCourse(a);
                //controller.setContents(a.getCourseCode(), a.getCourseTitle());
                
                
                gridPane.add(anchorPane, x.getAndIncrement() / count.get(), y.getAndIncrement() % count.get());

                count.getAndIncrement();

  /*              anchorPane.setOnMouseClicked(e -> {

                    setCurrentCourse(a);
                    dragPane.setVisible(true);
                    vBox.setVisible(false);

                });
*/
            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        
        });
        
        

    }

    public static void setPerson(Teacher _person) {

        teacher = _person;
    }
    
   

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
    }

    
}
