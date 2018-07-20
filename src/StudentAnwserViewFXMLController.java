/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class StudentAnwserViewFXMLController implements Initializable {

    
    @FXML
    private TextField textField;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;

    
    private int score;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            score = Integer.parseInt(textField.getText());
        });
    }

    public void setAnswer(String text) {
        answerLabel.setText(text);
    }


    public void setQuestion(String text) {
        questionLabel.setText(text);
    }

}
