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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CardViewFXMLController implements Initializable {

    @FXML
    private Label courseCode;
    @FXML
    private Label courseTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setContents( String courseCode,String courseTitle){
    
        this.courseCode.setText(courseCode);
        this.courseTitle.setText(courseTitle);
    
    }
    
    @FXML 
    public void onMouseClicked(MouseEvent evt){
    
        System.out.println("Mouse click detected");
    
    }

}
