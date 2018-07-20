/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class StudentInformationFXMLController implements Initializable {
    
    @FXML Label name,department,courseCode,courseTitle, level;
    
    private Student student;
    private Course course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void setLabels(){
    
        name.setText(student.getFullName());
        department.setText(student.getDepartment());
        courseCode.setText(course.getCourseCode());
        courseTitle.setText(course.getCourseTitle());
        level.setText(String.valueOf(student.getLevel()));
    
    }
    
    
}
