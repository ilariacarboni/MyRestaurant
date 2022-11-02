package view.utils;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import view.utils.imageManagers.ImagesProvider;

import java.util.ArrayList;
import java.util.Optional;

public class CustomDialog extends Dialog{

    public static final String TYPE_INFO      = "INFO";
    public static final String TYPE_WARNING   = "WARNING";
    public static final String TYPE_ERROR     = "ERROR";
    public static final String TYPE_SUCCESS   = "SUCCESS";
    public static final String TYPE_FORBIDDEN = "FORBIDDEN";
    public static final String TYPE_LOCK      = "LOCK";

    private static final CustomDialog customDialog = new CustomDialog();
    private AnchorPane content;
    private ImageView dialogIcon;
    private Text dialogTextContent;
    private ArrayList<ButtonType> buttons = new ArrayList<>();

    private ImagesProvider imagesProvider = ImagesProvider.getInstance();

    public static CustomDialog getInstance(){ return customDialog;};

    public void setInfo(String text, String type){
        this.content = new AnchorPane();
        this.dialogTextContent = new Text(text);
        String iconType = (type != null) ? type : this.TYPE_INFO;
        this.dialogIcon = new ImageView(imagesProvider.getDialogIcon(iconType));
        this.content.getChildren().add(dialogIcon);
        this.setIconLayout();

        this.content.getChildren().add(dialogTextContent);
        this.setTextLayout();
    }

    private void setTextLayout(){
        dialogTextContent.setLayoutX(100);
        dialogTextContent.setLayoutY(45);
        dialogTextContent.wrappingWidthProperty().set(300);
        dialogTextContent.setStyle("-fx-font: 18 system;");
    }

    private void setIconLayout(){
        dialogIcon.setFitWidth(64);
        dialogIcon.setFitHeight(64);
        dialogIcon.setLayoutX(14);
        dialogIcon.setLayoutY(20);
    }

    public void setButtons(ButtonType... types){
        this.buttons.clear();
        for (ButtonType type:types){
            this.buttons.add(type);
        }
    }

    public Optional<ButtonType> showAndWait(String title){
        this.setTitle(title);
        DialogPane dialogPane = this.getDialogPane();
        this.getDialogPane().getButtonTypes().clear();
        this.getDialogPane().getButtonTypes().addAll(this.buttons);
        if(dialogPane.getContent() != null){
            dialogPane.setContent(null);
        }
        dialogPane.setContent(this.content);
        this.setResizable(false);
        return this.showAndWait();
    }
}
