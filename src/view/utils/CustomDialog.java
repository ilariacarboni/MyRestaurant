package view.utils;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Optional;

public class CustomDialog{

    private AnchorPane content;
    private ImageView dialogIcon;
    private Text dialogTextContent;
    private ArrayList<ButtonType> buttons = new ArrayList<>();

    public CustomDialog(String text, Image img){
        this.content = new AnchorPane();
        this.dialogTextContent = new Text(text);
        if(img != null){
            this.dialogIcon = new ImageView(img);
            this.content.getChildren().add(dialogIcon);
            dialogIcon.setFitWidth(64);
            dialogIcon.setFitHeight(64);
            dialogIcon.setLayoutX(14);
            dialogIcon.setLayoutY(14);
        }

        this.content.getChildren().add(dialogTextContent);
        dialogTextContent.setLayoutX(100);
        dialogTextContent.setLayoutY(45);
        dialogTextContent.wrappingWidthProperty().set(300);
        dialogTextContent.setStyle("-fx-font: 18 system;");
    }

    public void setButtons(ButtonType... types){
        for (ButtonType type:types){
            this.buttons.add(type);
        }
    }

    public Optional<ButtonType> showAndWait(String title){
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        DialogPane dialogPane = dialog.getDialogPane();

        dialog.getDialogPane().getButtonTypes().addAll(this.buttons);
        dialogPane.setContent(this.content);
        dialog.setResizable(false);
        return dialog.showAndWait();
    }
}
