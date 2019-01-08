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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private static Course course;
    private VBox smallVBox;
    private static THomeFXMLDocumentController parentController;

    private static Teacher teacher = (Teacher) THomeFXMLDocumentController.person;

    @FXML
    private Label courseTitle;
    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        smallVBox = new VBox();
        BackButtonController.setPrevious("THomeFXMLDocument.fxml");
        
        courseTitle.setText(course.getCourseTitle());
    }

    public static void setParentController(THomeFXMLDocumentController _parentController) {

        parentController = _parentController;

    }

    public static void setCourse(Course _course) {

        course = _course;

    }

    public static Course getCourse() {
        return course;
    }

    @FXML
    public void onScoresheetCliked(ActionEvent evt) {

        try {

            parentController.changeThirdLinkName("Scoresheet");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreSheetDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            ScoreSheetDashboardFXMLController controller = loader.getController();
            
            controller.setStudentList(teacher.getCourseRegId(course));

            smallVBox.getChildren().add(0, pane);

            VBoxController.changeBox(smallVBox);

            //VBoxController.changeBox(pane);
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
            
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            ExamChooserFXMLController.setIsExam(false);

            
            NextFXMLController.setIsExam(false);
            TopMenuFXMLController.setIsExam(false);
            
            setUpload("Upload Assignment");
            
            VBoxController.changeBox(smallVBox);
        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void onUploadExamQuestionCliked(ActionEvent evt) throws Exception {

        try {
            // TODO

            // for the exam
            ExamChooserFXMLController.setIsExam(true);
            
            NextFXMLController.setIsExam(true);
            TopMenuFXMLController.setIsExam(true);

            if (course.isExamSet(
                    teacher.getId())) {

                parentController.changeThirdLinkName("Update Exam");
                Logger.getLogger(CourseDashboardFXMLController.class.getName()).log(Level.SEVERE, "Exam is already set set ", "Exam is already set set ");
                loader = new FXMLLoader(getClass().getResource("ExamChooserFXML.fxml"));
                AnchorPane examChooserPane = (AnchorPane) loader.load();
                ExamChooserFXMLController controller2 = loader.getController();
                teacher.retrieveQuestions();
                // teacher.getCourseRegId(course);
                controller2.setQuestion(Teacher.getAllQuestions());
                VBoxController.setTopMenu(TopMenuEnum.TOP);

                Pane emptyPane = new Pane();

                smallVBox.getChildren().add(0, examChooserPane);

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("NextFXML.fxml"));

                AnchorPane nPane = (AnchorPane) loader2.load();

                smallVBox.getChildren().add(1, nPane);

            } else {
                
                setUpload("Upload Exam");

            }

            VBoxController.changeBox(smallVBox);
        } catch (IOException ex) {
            Logger.getLogger(CourseDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setUpload(String nameOfPage) throws IOException {

        parentController.changeThirdLinkName(nameOfPage);
        Logger.getLogger(CourseDashboardFXMLController.class.getName()).log(Level.SEVERE, "Exam is not set ", "Exam is not set ");
        loader = new FXMLLoader(getClass().getResource("FileChooserFXML.fxml"));
        AnchorPane fileChooserPane = (AnchorPane) loader.load();
        //ExamTimeFXMLController controller3 = loader.getController();
        smallVBox.getChildren().add(0, fileChooserPane);

        /*
        NextFXMLController.setNextFrame("FileChooserFXML.fxml");
        loader = new FXMLLoader(getClass().getResource("NextFXML.fxml"));
        AnchorPane next = (AnchorPane) loader.load();
        //NextFXMLController controller4 = loader.getController();
*/
      //  smallVBox.getChildren().add(1, next);

    }

    @FXML
    public void onUploadDocumentCliked(ActionEvent evt) {

        try {

            parentController.changeThirdLinkName("Upload Document");
            VBoxController.setTopMenu(TopMenuEnum.NO_TOP);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DocumentDashboardFXML.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            DocumentDashboardFXMLController controller = loader.getController();

            controller.setCourseid(CardViewFXMLController.getCourseId());
            smallVBox.getChildren().add(0, pane);

            VBoxController.changeBox(smallVBox);

        } catch (IOException ex) {
            Logger.getLogger(CardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
