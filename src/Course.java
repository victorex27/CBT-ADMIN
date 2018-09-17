
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Course {
    
    private int id;
    private String courseCode;
    private String courseTitle;

    public Course(int id, String courseCode, String courseTitle) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
    
    public static Course getCourse(int id){
    
        Course course = null;
        try {
            
            
            Connection connection = SimpleConnection.getConnection();
            
            PreparedStatement pStmt = connection.prepareStatement("SELECT  course_code, course_title "
                + "FROM course WHERE id=? LIMIT 1");
            
            pStmt.setInt(1, id);
            
            ResultSet res = pStmt.executeQuery();
            
            
            while(res.next()){
                
                course = new Course(id, res.getString("course_code"), res.getString("course_title"));
            
            }
            
            
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return course;
    }
    
}
