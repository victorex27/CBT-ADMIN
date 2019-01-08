
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class Teacher extends Person {

    private ArrayList<String> listOfClasses;
    private ArrayList<String> listOfSubjects;
    private static final ArrayList<String> ALLOWEDEXTENSION = new ArrayList<>();

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    private static Connection connection;

    private static ArrayList<Question> allQuestions;
    // this is the minimum number of options
    private final static int NO_OF_OPTIONS = 3;

    private static int regId;

    public Teacher(String id, String firstName, String lastName, String middleName, String permission) {

        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMiddleName(middleName);

        this.setPermission(permission);

        /**
         * These are the allowed extension for version 1 which is csv
         *
         */
        ALLOWEDEXTENSION.add("csv");
        ALLOWEDEXTENSION.add("doc");
        ALLOWEDEXTENSION.add("docx");
        ALLOWEDEXTENSION.add("pdf");
        ALLOWEDEXTENSION.add("txt");

    }

    private boolean isFormatValid(String uri) throws Exception {

        String ext = FilenameUtils.getExtension(uri);

        //
        if (!ALLOWEDEXTENSION.contains(ext)) {
            throw new Exception("Invalid File Format");
        }

        return true;
    }

    private boolean isFileValid(File file) throws Exception {

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }

        return true;
    }

    public void parseFile(File file) {

    }

    //this is called when the Teacher is about to set the exam questions.
    public boolean setExam(Course course, String uri) throws Exception {

        allQuestions = new ArrayList();

        File file = new File(uri);

        isFormatValid(uri);

        isFileValid(file);

        parseDataObject(uri);

        getCourseRegId(course);

        //addToDatabase();
        return true;
    }

    public int getCourseRegId(Course course) {

        // teacher.id from teacher on database
        try {
            Connection connection = SimpleConnection.getConnection();

            String sqlQuery = "SELECT id "
                    + "FROM teacher "
                    + "WHERE person_id = ? and course_id = ? LIMIT 1";
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

            pStatement.setString(1, this.getId());
            pStatement.setInt(2, CardViewFXMLController.getCourseId());

            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {

                regId = resultSet.getInt("id");

            }

            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);

        }

        return regId;

    }

    private PreparedStatement setQueryValues(int id, PreparedStatement pStatement) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        allQuestions.forEach((Question q) -> {

            int a = 9 * atomicInteger.getAndIncrement();

            try {

                pStatement.setInt(a + 1, id);
                pStatement.setString(a + 2, q.getQuestion());
                pStatement.setString(a + 3, q.getA());
                pStatement.setString(a + 4, q.getB());
                pStatement.setString(a + 5, q.getC());
                pStatement.setString(a + 6, q.getD());
                pStatement.setString(a + 7, q.getE());

                Image image = q.getImage();
                if (image == null) {

                    pStatement.setBinaryStream(a + 8, null);
                    pStatement.setString(a + 9, null);

                } else {

                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", os);
                    InputStream fis = new ByteArrayInputStream(os.toByteArray());

                    pStatement.setBinaryStream(a + 8, fis);
                    pStatement.setString(a + 9, null);
                }

            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return pStatement;

    }

    private PreparedStatement setQueryValues2(int id, PreparedStatement pStatement, String deadline) throws ParseException {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        allQuestions.forEach((Question q) -> {

            int a = 5 * atomicInteger.getAndIncrement();

            Image image = q.getImage();
            try {

                pStatement.setInt(a + 1, id);
                pStatement.setString(a + 2, q.getQuestion());

                if (q.getImage() == null) {

                    pStatement.setBinaryStream(a + 3, null);
                    pStatement.setString(a + 4, null);

                } else {

                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", os);
                    InputStream fis = new ByteArrayInputStream(os.toByteArray());

                    pStatement.setBinaryStream(a + 3, fis);
                    pStatement.setString(a + 4, null);

                }

                pStatement.setString(a + 5, deadline);

            } catch (SQLException | FileNotFoundException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return pStatement;

    }

    public void addToExamDatabase(String duration) throws SQLException {

        Connection conn = null;
        try {
            int sizeOfQuestion = allQuestions.size();
            if (sizeOfQuestion < 1) {
                throw new Exception("Invalid Operation");
            }

            String sqlQuery = "INSERT INTO exam_question(teacher_id, question, a, b, c, d, e, picture, type) VALUES (?,?,?,?,?,?,?,?,?) ";

            while (sizeOfQuestion > 1) {

                sqlQuery += " ,(?,?,?,?,?,?,?,?,?) ";

                sizeOfQuestion--;
            }

            conn = SimpleConnection.getConnection();
            conn.setAutoCommit(false);
            System.out.println("Auto commit value " + conn.getAutoCommit());
            //deleteFromDB(conn);

            deleteFromDB(conn);

            PreparedStatement pStatement = conn.prepareStatement(sqlQuery);

            pStatement = setQueryValues(regId, pStatement);

            System.out.printf("updating value in teachers table. Duration:%s : id:%s ", duration, regId);
            pStatement.executeUpdate();

            String sqlQuery2 = "Update teacher SET duration = ? WHERE id=?";

            PreparedStatement pStatement2 = conn.prepareStatement(sqlQuery2);

            pStatement2.setString(1, duration);
            pStatement2.setInt(2, regId);

            pStatement2.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            connection.close();
        }
    }

    public void addToAssignmentDatabase(String deadline) throws SQLException {

        try {
            int sizeOfQuestion = allQuestions.size();
            if (sizeOfQuestion < 1) {
                throw new Exception("Invalid Operation");
            }

            String sqlQuery = "INSERT INTO assignment_question(teacher_id, question,picture, type, deadline) VALUES (?,?,?,?,?) ";

            while (sizeOfQuestion > 1) {

                sqlQuery += " ,(?,?,?,?,?) ";

                sizeOfQuestion--;
            }

            connection = SimpleConnection.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

            pStatement = setQueryValues2(regId, pStatement, deadline);
            pStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            connection.close();
        }
    }

    public ArrayList<Course> getCourses() throws SQLException, ClassNotFoundException, Exception {

        ArrayList<Course> subjects = new ArrayList<>();
        connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT course.id, course_code, course_title "
                + "FROM course "
                + "INNER JOIN teacher "
                + "ON course.id = teacher.course_id "
                + "where teacher.person_id = ? ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                subjects.add(new Course(resultSet.getInt("id"), resultSet.getString("course_code"), resultSet.getString("course_title")));

            }
        }

        connection.close();

        return subjects;

    }

    public static ArrayList<Question> getAllQuestions() {
        return allQuestions;
    }

    private void parseDataObject(String uri) throws IOException, SAXException, TikaException {

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try (InputStream stream = new FileInputStream(uri)) {

            parser.parse(stream, handler, metadata);

            parseString(handler.toString(), "\n");
            //return handler.toString();

        }
    }

    private ArrayList<String> parseString(String value, String regex) {

        String[] wordsArray;

        wordsArray = value.split(regex);

        boolean flag = true;
        int count = 0;

        String questionText = null;
        String optionA = null;
        String optionB = null;
        String optionC = null;
        String optionD = null;
        String optionE = null;

        System.out.println("Parsing questions.....");

        for (String w : wordsArray) {

            if (!w.trim().isEmpty()) {

                switch (count) {

                    case 0:

                        flag = false;
                        questionText = w;
                        count++;
                        break;
                    case 1:
                        optionA = w;
                        count++;
                        break;
                    case 2:
                        optionB = w;
                        count++;
                        break;
                    case 3:
                        optionC = w;
                        count++;
                        break;
                    case 4:
                        optionD = w;
                        count++;
                        break;
                    case 5:
                        optionE = w;
                        count++;

                        break;

                }

                //System.out.println("A: " + w);
            } else {

                if (!flag) {

                    Question q = new Question.Builder(questionText, optionA, optionB).addC(optionC).addD(optionD).addE(optionE).build();
                    allQuestions.add(q);

                }

                flag = true;
                count = 0;

            }

        }

        return null;

    }

    public boolean retrieveQuestions() throws Exception {

        System.out.println("retrieving questions...");
        allQuestions = new ArrayList();

        // teacher.id from teacher on database
        try {
            Connection conn = SimpleConnection.getConnection();

            String sqlQuery = "SELECT teacher_id, question, a, b,c,d,e,picture,type "
                    + "FROM exam_question INNER JOIN teacher ON exam_question.teacher_id = teacher.id "
                    + "WHERE person_id = ? and course_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sqlQuery);

            pStatement.setString(1, this.getId());
            pStatement.setInt(2, CardViewFXMLController.getCourseId());

            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {

                    Question q = new Question.Builder(resultSet.getString("question"), resultSet.getString("a"), resultSet.getString("b")).addC(resultSet.getString("c")).addD(resultSet.getString("d")).addE(resultSet.getString("e")).build();

                    Blob foto = resultSet.getBlob("picture");

                    if (foto != null) {
                        InputStream is = foto.getBinaryStream();
                        Image img;
                        img = new Image(is);

                        q.setImageProperty(img);
                    }
                    allQuestions.add(q);

                    regId = resultSet.getInt("teacher_id");
                }
            }

            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        //addToDatabase();
        return true;
    }

    public boolean deleteFromDB(Connection conn) throws SQLException, ClassNotFoundException {

        String sqlQuery = "DELETE FROM exam_question WHERE teacher_id = ?";
        PreparedStatement pStatement = conn.prepareStatement(sqlQuery);

        pStatement.setInt(1, regId);

        System.out.println("Deleting regid:" + regId + " from db");
        return pStatement.execute();

    }

}
