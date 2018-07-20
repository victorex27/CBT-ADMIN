/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ScoreSheetDashboardFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    ListView nameOfStudentsListView;
    @FXML
    ListView eachStudentScript;
    @FXML
    Button submit;
    @FXML
    Label totalScore, courseCode;

    private Student currentStudent;
    /**
     * Initializes the controller class.
     */
    private Map<String, Integer> students;
    private ObservableList<String> studentId;
    private ObservableList<AnchorPane> pane;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        students = new HashMap<>();

        pane = FXCollections.observableArrayList();
        submit.setOnAction(e -> {

            //send total score to the db for that student.
        });

        nameOfStudentsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try {
                    System.out.println("changed");
                    
                    pane.clear();

                    //Get the header information about the student.
                    //FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentInformationFXML.fxml"));
                    //AnchorPane headerPane = (AnchorPane) loader.load();
                    //StudentInformationFXMLController headerController = loader.getController();
                    //headerController.setStudent(currentStudent);

                    //Get the students questions
                    Connection connection = SimpleConnection.getConnection();
                    String sqlQuery = "SELECT text, question "
                            + "FROM course_registration "
                            + "INNER JOIN answer "
                            + "ON course_registration.id = answer.course_registration_id "
                            + "INNER JOIN exam_question "
                            + "ON exam_question.id = answer.exam_question_id "
                            + "WHERE course_registration_id = ? ";

                    PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

                    pStatement.setInt(1, students.get(newValue));

                    //System.out.println("V: "+students.get(newValue));
                    ResultSet resultSet = pStatement.executeQuery();

                    while (resultSet.next()) {
                        FXMLLoader loaderBody = new FXMLLoader(getClass().getResource("StudentAnwserViewFXML.fxml"));
                        AnchorPane bodyPane = (AnchorPane) loaderBody.load();
                        StudentAnwserViewFXMLController bodyController = loaderBody.getController();
                        bodyController.setQuestion(resultSet.getString("question"));
                        bodyController.setAnswer(resultSet.getString("text"));
                        pane.add(bodyPane);

                        
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ScoreSheetDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ScoreSheetDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ScoreSheetDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

public void setStudentList(int teacher_id) throws SQLException, ClassNotFoundException {

        studentId = FXCollections.observableArrayList();

        Connection connection = SimpleConnection.getConnection();

        // change this
        String sqlQuery = "SELECT id, reg_number from course_registration where teacher_id = ?  ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setInt(1, teacher_id);

        ResultSet resultSet = pStatement.executeQuery();

        while (resultSet.next()) {

            studentId.add(resultSet.getString("reg_number"));
            students.put(resultSet.getString("reg_number"), resultSet.getInt("id"));
        }

        connection.close();

        //retrieve students from db and and their names here;
        //students.add(Student);
        nameOfStudentsListView.setItems(studentId);
        eachStudentScript.setItems(pane);
        //courseCode.setText(course.getCourseCode());
    }
    
    
}
