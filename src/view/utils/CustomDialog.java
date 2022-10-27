package view.utils;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CustomDialog{

    private AnchorPane content;
    private ImageView dialogIcon;
    private Text dialogTextContent;
    private ArrayList<ButtonType> buttons = new ArrayList<>();

    public static final String TYPE_INFO      = "INFO";
    public static final String TYPE_WARNING   = "WARNING";
    public static final String TYPE_ERROR     = "ERROR";
    public static final String TYPE_SUCCESS   = "SUCCESS";
    public static final String TYPE_FORBIDDEN = "FORBIDDEN";
    public static final String TYPE_LOCK      = "LOCK";

    private final String TYPE_INFO_ICON      = "/view/style/img/dialog-icons/info.png";
    private final String TYPE_WARNING_ICON   = "/view/style/img/dialog-icons/warning.png";
    private final String TYPE_ERROR_ICON     = "/view/style/img/dialog-icons/error.png";
    private final String TYPE_SUCCESS_ICON   = "/view/style/img/dialog-icons/check.png";
    private final String TYPE_FORBIDDEN_ICON = "/view/style/img/dialog-icons/forbidden.png";
    private final String TYPE_LOCK_ICON      = "/view/style/img/dialog-icons/lock.png";

    private HashMap<String, String> typeIconMapping;

    public CustomDialog(String text, String type){
        createTypeIconMapping();
        this.content = new AnchorPane();
        this.dialogTextContent = new Text(text);
        String icon = this.TYPE_INFO_ICON;
        if(this.typeIconMapping.containsKey(type)){
            icon = this.typeIconMapping.get(type);
        }
        this.dialogIcon = new ImageView(icon);
        this.content.getChildren().add(dialogIcon);
        this.setIconLayout();

        this.content.getChildren().add(dialogTextContent);
        this.setTextLayout();
    }
    public CustomDialog(String text, Image img){
        createTypeIconMapping();
        this.content = new AnchorPane();
        this.dialogTextContent = new Text(text);
        if(img != null){
            this.dialogIcon = new ImageView(img);
            this.content.getChildren().add(dialogIcon);
            this.setIconLayout();
        }

        this.content.getChildren().add(dialogTextContent);
        this.setTextLayout();
    }

    private void createTypeIconMapping(){
        this.typeIconMapping = new HashMap<>();
        typeIconMapping.put(this.TYPE_INFO, this.TYPE_INFO_ICON);
        typeIconMapping.put(this.TYPE_WARNING, this.TYPE_WARNING_ICON);
        typeIconMapping.put(this.TYPE_ERROR, this.TYPE_ERROR_ICON);
        typeIconMapping.put(this.TYPE_SUCCESS, this.TYPE_SUCCESS_ICON);
        typeIconMapping.put(this.TYPE_FORBIDDEN, this.TYPE_FORBIDDEN_ICON);
        typeIconMapping.put(this.TYPE_LOCK, this.TYPE_LOCK_ICON);
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
