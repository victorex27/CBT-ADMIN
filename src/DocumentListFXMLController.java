/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class DocumentListFXMLController implements Initializable {

    @FXML private Label label;
    private static ObservableList<Pane> data ;
  
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    public void SetDocumentName(String name){
    
        label.setText(name);
    }
    
    @FXML
    public void onDelete(ActionEvent evt){
    
        
        data.remove((Pane)label.getParent());
        
    }
    
    public static void setData(ObservableList<Pane> _data){
    
        data = _data;
    }
}
