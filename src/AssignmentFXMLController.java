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
public class AssignmentFXMLController implements Initializable {

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

    private String deadline;
    
    private boolean pictureButtonClickedState;
    private double gridPaneLayoutY;

    private Question questionObject;
    @FXML private ImageView imageViewForExamPicture;
    
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

    
    public void setQuestionObject(Question question){
    
        questionObject = question;
    }
    

    public String getQuestion() {
        return questionArea.getText();
    }


    @FXML

    public void pictureButtonClicked() {

          pictureButtonClickedState = !pictureButtonClickedState;

        if (pictureButtonClickedState) {
            
            
      

            //image view change to collapse
            imageView.setImage(new Image(getClass().getResource("image/collapse.png").toString(), true));
            //push gridpane down
            gridPane.setLayoutY(gridPaneLayoutY + 200);
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
                    
                }else{
                
                    String filePath = file.getAbsolutePath();
                
                Image image = new Image(file.toURI().toString());
                imageViewForExamPicture.setImage(image);
                
                questionObject.setFilePath(filePath);
                
                
                }
                                
                
        
    
    
    }
    @FXML
    public void onResetClicked(ActionEvent evt){
    
        Image image = new Image(getClass().getResource("image/book.png").toString(), true);
                imageViewForExamPicture.setImage(image);
                
                questionObject.setFilePath(null);
                
        
    }

   

}
