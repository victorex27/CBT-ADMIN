
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private Label errorLabel;
    @FXML
    private ListView listView;

    @FXML
    private Button saveToDbButtuon;

    private VBox smallVBox;

    @FXML
    private VBox vBox;

    @FXML
    private Hyperlink h2;
    @FXML
    private Hyperlink h3;
    @FXML
    private Hyperlink h4;
    @FXML private Pane paneHome;
    @FXML private Pane paneFourth;
    @FXML private Pane paneSecond;
    @FXML private Pane paneThird;

    private GridPane gridPane;

    public static Person person;
    private static Course currentCourse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBoxController.setVbox(vBox);
        gridPane = new GridPane();
        smallVBox = new VBox();

        try {
            // TODO

            System.out.println("permission of the current user" + person.getPermission());

            if (person.getPermission() == PermissionLevel.LECTURER) {
                showTeacherView(Person.getTeacher());
            } else if (person.getPermission() == PermissionLevel.STUDENT) {

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
        if (teacher.getCourses().size() < 1) {

        } else {
            teacher.getCourses().forEach(a -> {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardViewFXML.fxml"));

                try {
                    AnchorPane anchorPane = (AnchorPane) loader.load();
                    CardViewFXMLController controller = loader.getController();
                    CardViewFXMLController.setParentController(this);

                    controller.setCourse(a);
                    //controller.setContents(a.getCourseCode(), a.getCourseTitle());

                    gridPane.add(anchorPane, x.get() % count.get(), y.get());
                    System.out.println("x: "+x.get() % count.get()+", y:"+y.get());

                    count.getAndIncrement();
                    
                    x.incrementAndGet();

                    if (x.get() == 1) {
                        
                        x.set(0);

                        y.getAndIncrement();
                    }
                    
                    
                    

                } catch (IOException ex) {
                    Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            vBox.getChildren().add(0, gridPane);

        }
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
            System.out.println("hello ");

            try {

                //System.out.println("Student course"+a.getCourseCode());
                AnchorPane anchor;
                CourseCardViewFXMLController.setParentController(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCardViewFXML.fxml"));
                anchor = (AnchorPane) loader.load();
                CourseCardViewFXMLController con = loader.getController();

                //CourseCardViewFXMLController.setCourse(a);
                con.setCourse(a);

                gridPane.add(anchor, x.get() % count.get(), y.get());

                //System.out.println(x.get()+" : "+y.get());
                //gridPane.add(anchor, x.getAndIncrement() / count.get(), y.getAndIncrement() % count.get());
                
                System.out.println("x: "+x.get() % count.get()+", y:"+y.get());

                    count.getAndIncrement();
                    
                    x.incrementAndGet();

                    if (x.get() == 1) {
                        
                        x.set(0);

                        y.getAndIncrement();
                    }
            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        vBox.getChildren().add(0, gridPane);

    }

    public static void setPerson(Person _person) {

        person = _person;
    }

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
    }

    @FXML
    private void logout(ActionEvent evt) throws IOException {

        ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("LoginFXMLDocument.fxml")));
    }

    public void changeSecondLinkName(String name) {
        paneSecond.setVisible(true);
        h2.setText(name);
    }

    public void changeThirdLinkName(String name) {
        paneThird.setVisible(true);
        h3.setText(name);
    }

    public void changeFourthLinkName(String name) {
        paneFourth.setVisible(true);
        h4.setText(name);
    }

    @FXML
    private void firstLinkClicked(ActionEvent evt) throws IOException {

        paneSecond.setVisible(false);
        paneThird.setVisible(false);
        paneFourth.setVisible(false);
        ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("THomeFXMLDocument.fxml")));

    }

    @FXML
    private void secondLinkClicked(ActionEvent evt) throws IOException {
        paneThird.setVisible(false);
        paneFourth.setVisible(false);

        if (person.getPermission() == PermissionLevel.LECTURER) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDashboardFXML.fxml"));

            AnchorPane pane = (AnchorPane) loader.load();
            smallVBox.getChildren().clear();

            Pane emptyPane = new Pane();
            smallVBox.getChildren().add(0, emptyPane);
            smallVBox.getChildren().add(1, pane);

            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            VBoxController.changeBox(smallVBox);
        }else if (person.getPermission() == PermissionLevel.STUDENT) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDefaultHomeFXML.fxml"));

            AnchorPane pane = (AnchorPane) loader.load();
            smallVBox.getChildren().clear();

            Pane emptyPane = new Pane();
            smallVBox.getChildren().add(0, emptyPane);
            smallVBox.getChildren().add(1, pane);

            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            VBoxController.changeBox(smallVBox);
        }
    }

    @FXML
    private void thirdLinkClicked(ActionEvent evt) {
        paneFourth.setVisible(false);
    }

    @FXML
    private void fourthLinkClicked(ActionEvent evt) {
    }
}
