
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
 * The Vbox is on a stackpane with a Pane The Pane contains the upload button
 * and the drag and drop the vbox contains the list of the subjects which is
 * clickable to take you to the Pane
 *
 *
 */
public class THomeFXMLDocumentController implements Initializable {

   

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

    @FXML
    GridPane gridPane;

    public static Person person;
    private static Course currentCourse;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            System.out.println("permission of the current user"+person.getPermission());

           if(person.getPermission() == PermissionLevel.LECTURER){ 
            showTeacherView(Person.getTeacher());
           }else if(person.getPermission() == PermissionLevel.STUDENT){
               
               //student.setDepartment();
                //student.retrieveCourses();

                showStudentView(Person.getStudent());
           }
        } catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This displays the view only for teachers * *
     */
    private void showTeacherView(Teacher teacher) throws ClassNotFoundException, Exception {

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

                gridPane.add(anchorPane,x.get() % count.get(), y.get());

                count.getAndIncrement();
                
                if(x.get() == 1){
                    
                    y.getAndIncrement();
                }
                x.incrementAndGet();
                

            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }
    
    
    private void showStudentView(Student student) throws ClassNotFoundException, Exception {

        if (student.getCourses().size() < 1) {
            throw new Exception("No Registered Course for student ");
        }

        AtomicInteger count = new AtomicInteger(1);
        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0);

        //gridPane.getChildren().clear();
        student.getCourses().forEach(a -> {

            try {

                //System.out.println("Student course"+a.getCourseCode());
                
                AnchorPane anchor;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCardViewFXML.fxml"));
                anchor = (AnchorPane) loader.load();
                CourseCardViewFXMLController con = loader.getController();
                //CourseCardViewFXMLController.setCourse(a);
                con.setCourse(a);
                
                
                
                gridPane.add(anchor,x.get() % count.get(), y.get());
       
                //System.out.println(x.get()+" : "+y.get());
                //gridPane.add(anchor, x.getAndIncrement() / count.get(), y.getAndIncrement() % count.get());

                count.getAndIncrement();
                
                if(x.get() == 1){
                    
                    y.getAndIncrement();
                }
                x.incrementAndGet();
                
            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public static void setPerson(Person _person) {

        person = _person;
    }

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
    }

    @FXML private void logout(ActionEvent evt) throws IOException{
        
        ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("LoginFXMLDocument.fxml")));
    }
}
