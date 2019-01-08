
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class VBoxController {

    private static VBox vBox;
    private static TopMenuEnum topMenu;
    //private VBox smallVBox;

    private static AnchorPane getTopPane() throws IOException {
        //smallVBox = new VBox();

        FXMLLoader loader = new FXMLLoader(VBoxController.class.getResource("TopMenuFXML.fxml"));
        AnchorPane topMenuPane = (AnchorPane) loader.load();
        //TopMenuFXMLController controller1 = loader.getController();
        //smallVBox.getChildren().add(0, topMenuPane);

        return topMenuPane;
    }

    public static void setTopMenu(TopMenuEnum menu) {

        topMenu = menu;
    }

    public static void setVbox(VBox vBox) {
        VBoxController.vBox = vBox;

    }

    public static void changeBox(VBox smallVBox) throws IOException {
        Pane pane = new Pane();
        vBox.getChildren().clear();
        if (topMenu == TopMenuEnum.TOP) {
            //VBox smallVBox = new VBox();

            //TopMenuFXMLController controller1 = loader.getController();
            

            vBox.getChildren().add(0, getTopPane());

            pane.getChildren().add(smallVBox);

            vBox.getChildren().add(1, pane);

        } else if (topMenu == TopMenuEnum.NO_TOP) {

            pane.getChildren().add(smallVBox);
           
            vBox.getChildren().add(0, pane);

        }

    }

}
