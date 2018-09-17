/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class DocumentDashboardFXMLController implements Initializable {
@FXML
    private Button fileChooserButton;
    @FXML
    private ListView listView;

    private static ObservableList<Pane> data;
    
    // this is the id that points to the teacher table for a particular course.

    private int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        data = FXCollections.observableArrayList();
        
        
        DocumentListFXMLController.setData(data);
        
        BackButtonController.setPrevious("CourseDashboardFXML.fxml");
    }

    public void setCourseid(int _id) {

        id = _id;

    }

    @FXML
    public void fileChooserButtonClicked(ActionEvent evt) throws Exception {
    
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt", "*.pdf", "*.docx", "*.doc");
        fileChooser.getExtensionFilters().add(extFilter);
        
        
        List<File> fileList = new ArrayList<>();
        
        fileList = fileChooser.showOpenMultipleDialog((Stage) fileChooserButton.getScene().getWindow());

        if (fileList == null ) {

            throw new Exception("Invalid Operation");

        } else {

            for (File file : fileList) {

                if (file.isDirectory() || (file == null)) {

                    fileList = null;

                    throw new Exception("Invalid Operation");

                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("DocumentListFXML.fxml"));
                Pane pane = (Pane) loader.load();
                DocumentListFXMLController controller = loader.getController();

                controller.SetDocumentName(file.getAbsolutePath());

                data.add(pane);

            }

            listView.setItems(data);

        }

    }

    @FXML
    public void onSubmit(ActionEvent evt) throws SQLException, ClassNotFoundException, FileNotFoundException {

        Connection connection = SimpleConnection.getConnection();

        String sqlQuery = " INSERT INTO document(teacher_id, name, file, type) VALUES (?,?,?,?) ";

        int dataSize = data.size();
        int count = dataSize;
        if (dataSize > 1) {

            while (count > 1) {
                sqlQuery += " ,(?,?,?,?)";

                count--;
            }
        }

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        data.forEach((e) -> {

            InputStream stream = null;
            try {
                int a = 4 * atomicInteger.getAndIncrement();
                String text = ((Label) e.getChildren().get(0)).getText();
                File file = new File(text);
                stream = new FileInputStream(file);
                String ext = FilenameUtils.getExtension(text);
                pStatement.setInt(a + 1, id);
                pStatement.setString(a + 2, file.getName());
                pStatement.setBinaryStream(a + 3, stream, (int) (file.length()));
                pStatement.setString(a + 4, ext);
            } catch (FileNotFoundException | SQLException ex) {
                Logger.getLogger(DocumentDashboardFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        pStatement.execute();

        connection.close();

    }    
}
