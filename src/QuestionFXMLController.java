/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class QuestionFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextArea questionArea;
   
    @FXML
    TextField questionATextField;
  
    @FXML
    TextField questionBTextField;
   
    @FXML
    TextField questionCTextField;
  
    @FXML
    TextField questionDTextField;
    
    @FXML
    TextField questionETextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    public void setQuestion(StringProperty question) {

        questionArea.setText(question.getValue());
        question.bind(questionArea.textProperty());
    }

    public void setOptionA(StringProperty optionA) {

        questionATextField.setText(optionA.getValue());
        optionA.bind(questionATextField.textProperty());
    }

    public void setOptionB(StringProperty optionB) {
        questionBTextField.setText(optionB.getValue());
        optionB.bind(questionBTextField.textProperty());

    }

    public void setOptionC(StringProperty optionC) {

        questionCTextField.setText(optionC.getValue());
        optionC.bind(questionCTextField.textProperty());
    }

    public void setOptionD(StringProperty optionD) {
        questionDTextField.setText(optionD.getValue());
        optionD.bind(questionDTextField.textProperty());
    }

    public void setOptionE(StringProperty optionE) {
        questionETextField.setText(optionE.getValue());
        optionE.bind(questionETextField.textProperty());
    }

    public String getQuestion() {
        return questionArea.getText();
    }

    public String getOptionA() {
        return questionATextField.getText();
    }

    public String getOptionB() {
        return questionBTextField.getText();
    }

    public String getOptionC() {
        return questionCTextField.getText();
    }

    public String getOptionD() {
        return questionDTextField.getText();
    }

    public String getOptionE() {
        return questionETextField.getText();
    }

}
