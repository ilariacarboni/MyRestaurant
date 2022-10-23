package view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    public ImageView dialogIcon;
    public Text dialogTextContent;
    public DialogPane dialog;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setDialogTextContent(String text){
        this.dialogTextContent.setText(text);
    }

    public void setDialogIcon(Image img){
        this.dialogIcon.setImage(img);
    }
}
