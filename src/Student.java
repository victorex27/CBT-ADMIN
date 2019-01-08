
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class Student extends Person {

    private String level;
    private String department;
    private ArrayList<Course> listOfCourses;
    private Connection connection;
    private int currentCourseRegId;

    public Student(String id, String firstName, String lastName, String middleName, String permission) throws ClassNotFoundException, Exception {

        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMiddleName(middleName);

        this.setPermission(permission);

        listOfCourses = new ArrayList<>();
        setDepartment();
        retrieveCourses();

    }

    public String getLevel() {

        return this.level;
    }

    private void retrieveCourses() throws SQLException, ClassNotFoundException, Exception {

        listOfCourses.clear();
        connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT course_registration.id, "
                + "course_code, status, result,course_title, course.id as courseid "
                + "FROM course_registration INNER JOIN teacher "
                + "ON teacher.id = course_registration.teacher_id "
                + "INNER JOIN course "
                + "ON course.id = teacher.course_id "
                + "WHERE reg_number = ? ";
        //   + "WHERE reg_number = ? AND status = 'open'";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            //throw new Exception("No record Found 1");

        } else {

            resultSet.beforeFirst();
            while (resultSet.next()) {

                //System.out.printf("%s    ",resultSet);
                Course c = new Course(resultSet.getInt("courseid"), resultSet.getString("course_code"), resultSet.getString("course_title"));
                c.setRegId(resultSet.getInt("id"));
                listOfCourses.add(c);

            }
        }

        connection.close();

    }

    public ArrayList<Course> getCourses() throws SQLException, ClassNotFoundException, Exception {

        return this.listOfCourses;

    }

    public String getDepartment() {
        return department;
    }

    private void setDepartment() throws SQLException, Exception {

        connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT department FROM student WHERE person_id = ? ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found 2");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                department = resultSet.getString("department");

            }
        }

        connection.close();

    }

    public ArrayList<Question> getAllQuestions(String course_code) throws SQLException, ClassNotFoundException, Exception {

        ArrayList<Question> allQuestions = new ArrayList<>();
        connection = SimpleConnection.getConnection();

        // change this
        String sqlQuery = "SELECT question,a,b,c,d,e,id,picture,type FROM exam_question WHERE teacher_id = ( SELECT id FROM Teacher WHERE course_id = (SELECT id FROM  course WHERE course_code = ? ) ) ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, course_code);

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found 2");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                Question q = new Question.Builder(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)).addC(resultSet.getString(4)).addD(resultSet.getString(5)).addE(resultSet.getString(6)).build();

                if (resultSet.getBinaryStream("picture") != null) {
                    File file = new File("eee." + resultSet.getString("type"));

                    InputStream is = resultSet.getBinaryStream("picture");
                    OutputStream os = new FileOutputStream(file);
                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }

                    os.close();
                    is.close();

                    q.setId(resultSet.getInt(7));
                    q.setImageProperty(new Image(file.toURI().toString()));
                }
                allQuestions.add(q);

            }
        }

        connection.close();

        return allQuestions;
    }

    public ArrayList<Question> getAllAssignmentQuestions() throws SQLException, ClassNotFoundException, Exception {

        ArrayList<Question> allQuestions = new ArrayList<>();
        connection = SimpleConnection.getConnection();
        
        System.out.println("reg id: "+currentCourseRegId);

        // change this
        String sqlQuery = "SELECT assignment_question.question,assignment_question.id,assignment_question.picture,assignment_question.type "
                + "FROM assignment_question  "
                + "WHERE teacher_id = ? "
                + "AND assignment_question.id NOT IN "
                + "(SELECT id FROM assignment_answer WHERE teacher_id = ? AND student_id = ? )";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setInt(1, currentCourseRegId);
        pStatement.setInt(2, currentCourseRegId);
        pStatement.setString(3, getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            //throw new Exception("No record Found 2");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                Question q = new Question.Builder(resultSet.getString("question"), null, null).addC(null).addD(null).addE(null).build();

                q.setId(resultSet.getInt("id"));

                if (resultSet.getBinaryStream("picture") != null) {
                    File file = new File("eee." + resultSet.getString("type"));

                    InputStream is = resultSet.getBinaryStream("picture");
                    OutputStream os = new FileOutputStream(file);
                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }

                    os.close();

                    is.close();

                    q.setImageProperty(new Image(file.toURI().toString()));
                }
                allQuestions.add(q);

            }
        }

        connection.close();

        return allQuestions;
    }
    
    public int getCourseRegId() {
    
        return currentCourseRegId ;
    }
    
    public void setRegId(Course course) throws SQLException, ClassNotFoundException{
    
        try (Connection conn = SimpleConnection.getConnection()) {
            String sqlQuery = "SELECT teacher_id from course_registration "
                    + "INNER JOIN teacher ON teacher.id=course_registration.teacher_id WHERE course_id=? LIMIT 1";
            
            PreparedStatement pStatement = conn.prepareStatement(sqlQuery);
            
            pStatement.setInt(1, course.getId());
            
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while(resultSet.next()){
                currentCourseRegId = resultSet.getInt("teacher_id");
            }
        }

    }

    


}
