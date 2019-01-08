/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ExamChooserFXMLController implements Initializable {

    private static ArrayList<Question> questionsList = new ArrayList<>();
    private Question prevQuestion = null;

    private static boolean isExam;

    private Question q;
    @FXML
    private TextArea question;
    @FXML
    private TextField optionA;
    @FXML
    private TextField optionB;
    @FXML
    private TextField optionC;
    @FXML
    private TextField optionD;
    @FXML
    private TextField optionE;

    @FXML
    private Pane answerPane;

    @FXML
    private ImageView imageViewForPicture;
    @FXML
    private Button fileChooserButton;

    private int count = 0;

    private int numOfQuestions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        System.out.println("isExam: "+isExam);
        if (!isExam) {
            answerPane.setVisible(false);
        } else {
            answerPane.setVisible(true);
        }

    }

    public static void setIsExam(boolean state) {
        isExam = state;
    }

    public void setQuestion(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
        numOfQuestions = questionsList.size();

        changeQuestion();

    }

    private void doUnbind() {

        if (prevQuestion.getQuestionProperty().isBound()) {
            prevQuestion.getQuestionProperty().unbind();
        }
        if (prevQuestion.getAProperty().isBound()) {
            prevQuestion.getAProperty().unbind();
        }
        if (prevQuestion.getBProperty().isBound()) {
            prevQuestion.getBProperty().unbind();
        }
        if (prevQuestion.getCProperty().isBound()) {
            prevQuestion.getCProperty().unbind();
        }
        if (prevQuestion.getDProperty().isBound()) {
            prevQuestion.getDProperty().unbind();
        }
        if (prevQuestion.getEProperty().isBound()) {
            prevQuestion.getEProperty().unbind();
        }if (prevQuestion.getImageProperty().isBound()) {
            prevQuestion.getImageProperty().unbind();
        }

    }

    // used with the next/ prev button
    private void changeQuestion() {

        
        q = (Question) questionsList.get(count);

        if (prevQuestion != null) {
            doUnbind();
        }

      
        question.setText(q.getQuestionProperty().getValue());
        optionA.setText(q.getA());
        optionB.setText(q.getB());
        optionC.setText(q.getC());
        optionD.setText(q.getD());
        optionE.setText(q.getE());
        
        imageViewForPicture.setImage(q.getImage());

      
        q.getImageProperty().bind(imageViewForPicture.imageProperty());
        

        q.getQuestionProperty().bind(question.textProperty());
        q.getAProperty().bind(optionA.textProperty());

        q.getBProperty().bind(optionB.textProperty());

        q.getCProperty().bind(optionC.textProperty());

        q.getDProperty().bind(optionD.textProperty());

        q.getEProperty().bind(optionE.textProperty());

    
        prevQuestion = q;

    }

    @FXML
    private void onPrevClicked(ActionEvent evt) {

        if (count > 0) {

            count--;
            changeQuestion();
        }
    }

    @FXML
    private void onNextClicked(ActionEvent evt) {

        if (count < numOfQuestions - 1) {

            count++;
            changeQuestion();
        }
    }

    @FXML
    private void fileChooserButtonClicked(ActionEvent evt) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog((Stage) fileChooserButton.getScene().getWindow());

        if (file != null) {

            //filePath = file.getAbsolutePath();
            String imagepath = file.toURI().toURL().toString();
            System.out.println("file:" + imagepath);
            Image image = new Image(imagepath);
            
            imageViewForPicture.setImage(image);
            
         
            
        } else {
            imageViewForPicture.setImage(null);
            
            q.setImageProperty(null);

        }

    }

    @FXML
    private void onResetClicked(ActionEvent evt) {

    }

    public static ArrayList<Question> getQuestion() {

        return questionsList;
    }

}
