/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private Pane uploadPane;

    @FXML
    private GridPane gridPane;
    
    @FXML 
    private ImageView imageView;
    
    @FXML
    private Button fileChooserButton;
    
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

    private boolean pictureButtonClickedState;
    private double gridPaneLayoutY;

    private Question questionObject;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pictureButtonClickedState = false;
        gridPaneLayoutY = gridPane.getLayoutY();
        
    }

    public void setQuestion(StringProperty question) {

        questionArea.setText(question.getValue());
        question.bind(questionArea.textProperty());
    }
 /*   
    public void setQuestionPicture(StringProperty filePath) {

        fileChooserButton.setText(filePath.getValue());
        file
    }
*/

    
    public void setQuestionObject(Question question){
    
        questionObject = question;
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

    @FXML

    public void pictureButtonClicked() {

          pictureButtonClickedState = !pictureButtonClickedState;

        if (pictureButtonClickedState) {
            
            
      

            //image view change to collapse
            imageView.setImage(new Image(getClass().getResource("image/collapse.png").toString(), true));
            //push gridpane down
            gridPane.setLayoutY(gridPaneLayoutY + 100);
            //upload picture to be visible
            uploadPane.setVisible(true);
        } else {

            //System.out.println(imageView);
            imageView.setImage(new Image(getClass().getResource("image/expand.png").toString(), true));
            gridPane.setLayoutY(gridPaneLayoutY);
            uploadPane.setVisible(false);
        }
        
    }
    
    @FXML
    public void fileChooserButtonClicked(ActionEvent evt) throws Exception{
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) fileChooserButton.getScene().getWindow());

                if (file.isDirectory() || (file == null)) {

                   
                        throw new Exception("Invalid Operation");
                    
                }
                String filePath = file.getAbsolutePath();
                
              
                questionObject.setFilePath(filePath);
                
                
        
    
    
    }

}
