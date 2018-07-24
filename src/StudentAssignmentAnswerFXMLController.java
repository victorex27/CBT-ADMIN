/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class StudentAssignmentAnswerFXMLController implements Initializable {

    @FXML
    private TextField score;
    @FXML
    private Label question;

    private int questionId, teacherId;
    private String studentId;

    private ObservableList<AnchorPane> paneData;
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void download(ActionEvent evt) throws SQLException,ClassNotFoundException {

        Connection connection = SimpleConnection.getConnection();
        
        String sqlQuery = "SELECT file,type,name "
                + "FROM assignment_answer "
                + "WHERE teacher_id = ? and student_id = ? and question_id = ? and marked <> 'true' ";
        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setInt(1, teacherId);
        pStatement.setString(2, studentId);
        pStatement.setInt(3, questionId);

        ResultSet resultSet = pStatement.executeQuery();

        while (resultSet.next()) {
        }
    }

    @FXML
    public void submit(ActionEvent evt) throws SQLException, ClassNotFoundException {

        Connection connection = SimpleConnection.getConnection();
        
        String sqlQuery = "UPDATE assignment_answer SET score = ?,marked='true' "
                + "WHERE teacher_id = ? "
                + " AND student_id = ? "
                + " AND question_id = ? ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setInt(1, Integer.parseInt(score.getText()));
        pStatement.setInt(2, teacherId);
        pStatement.setString(3, studentId);
        pStatement.setInt(4, questionId);

        System.out.println(pStatement.execute());

        pStatement.close();
        connection.close();

        paneData.remove(pane);

    }

    public void setQuestion(String text) {
        question.setText(text);
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setPaneData(ObservableList<AnchorPane> paneData) {
        this.paneData = paneData;
    }

}
