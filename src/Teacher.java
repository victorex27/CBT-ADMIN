
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private ArrayList<Question> allQuestions;
    // this is the minimum number of options
    private final static int NO_OF_OPTIONS = 3;
    
    private int id;


    public Teacher() {

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
        System.out.println("File Format is valid");
        return true;
    }

    private boolean isFileValid(File file) throws Exception {

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        System.out.println("File is valid");
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
        
        id = course.getId();
        
        
        //addToDatabase();
        return true;
    }

    private PreparedStatement setQueryValues(int id, PreparedStatement pStatement) {

    AtomicInteger atomicInteger = new AtomicInteger(0);
        
        allQuestions.forEach((Question q) -> {

            int a = 7 * atomicInteger.getAndIncrement();

            try {

                pStatement.setInt(a + 1, id);
                pStatement.setString(a + 2, q.getQuestion());
                pStatement.setString(a + 3, q.getA());
                pStatement.setString(a + 4, q.getB());
                pStatement.setString(a + 5, q.getC());
                pStatement.setString(a + 6, q.getD());
                pStatement.setString(a + 7, q.getE());

            } catch (SQLException ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return pStatement;

    }

    public void addToDatabase() throws SQLException {

        
        try {
            int sizeOfQuestion = allQuestions.size();
            if (sizeOfQuestion < 1 ) {
                throw new Exception("Invalid Operation");
            }

            String sqlQuery = "INSERT INTO exam_question(teacher_id, question, a, b, c, d, e) VALUES (?,?,?,?,?,?,?) ";

            while (sizeOfQuestion > 1) {

                sqlQuery += " ,(?,?,?,?,?,?,?) ";

                sizeOfQuestion--;
            }

            connection = SimpleConnection.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

            pStatement = setQueryValues(id, pStatement);
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

    public ArrayList<Question> getAllQuestions() {
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

}
