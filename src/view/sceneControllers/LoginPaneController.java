package view.sceneControllers;

import business.AdminManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.utils.LocatedImage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;

public class LoginPaneController extends BaseView implements Initializable {

    private final String WRONG_USERNAME_WARNING = "Username errato";
    private final String WRONG_PASSWORD_WARNING = "Password errata";
    private final String BACKGROUND_PATH  = "view/style/img/background/grey.jpeg";
    private AdminManager adminManager = new AdminManager();
    private HashMap<String, Object> currentUser = null;
    private boolean userWarnedForUsername = false;
    private Button sourceBtn = null;
    private boolean usernameLabelMoved = false;
    private boolean passwordLabelMoved = false;

    public AnchorPane loginForm;
    public VBox pane;
    public AnchorPane usernameContainer;
    public TextField usernameTextField;
    public Label usernameLabel;
    public AnchorPane passwordContainer;
    public PasswordField passwordField;
    public Label passwordLabel;
    public Label wrongUsernameLabel;
    public Label wrongPasswordLabel;
    public AnchorPane loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.commController.setLoginPaneController(this);
        setUsernameTextFieldListeners();
        setPasswordTextFieldListeners();
        pane.setBackground(new Background(new BackgroundImage(new LocatedImage(BACKGROUND_PATH), REPEAT, NO_REPEAT, CENTER, DEFAULT)));
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);
    }

    public void setSource(Button menuBtn){
        sourceBtn = menuBtn;
    }
    private void setUsernameTextFieldListeners(){
        usernameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(userWarnedForUsername){
                    userWarnedForUsername = false;
                    wrongUsernameLabel.setText(null);
                }
                if(usernameContainer.getStyleClass().contains("error")){
                    usernameContainer.getStyleClass().remove("error");
                }
            }
        });
        usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (!newPropertyValue){
                    //quando il campo username non è selezionato controllo che lo username inserito sia valido
                    String username = usernameTextField.getText();
                    if(!username.isEmpty()){
                        HashMap<String, Object> admin = adminManager.getFromUsername(username);
                        if(admin != null){
                            wrongUsernameLabel.setText(null);
                            currentUser = admin;
                        }else{
                            wrongUsernameLabel.setText(WRONG_USERNAME_WARNING);
                            userWarnedForUsername = true;
                        }
                    }else if(usernameLabelMoved){
                        moveInLabel(usernameLabel);
                        usernameLabelMoved = false;
                    }
                }else{
                    if(userWarnedForUsername){
                        userWarnedForUsername = false;
                        wrongUsernameLabel.setText(null);
                    }
                }
            }
        });
    }
    private void setPasswordTextFieldListeners(){
        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                String password = passwordField.getText();
                if (!newPropertyValue){
                    //quando il campo username non è selezionato controllo che lo username inserito sia valido
                    if(password.isEmpty() && passwordLabelMoved){
                        moveInLabel(passwordLabel);
                        passwordLabelMoved = false;
                    }
                }else{
                    if(!passwordLabelMoved){
                        moveOutLabel(passwordLabel, passwordField);
                    }
                }
            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(passwordContainer.getStyleClass().contains("error")){
                    passwordContainer.getStyleClass().remove("error");
                }
            }
        });
    }
    public void doLogin(MouseEvent mouseEvent) {
        String unencryptedPsw = passwordField.getText();
        String encrypted = adminManager.cryptPassword(unencryptedPsw);
        if(currentUser != null){
            String currentUserPassword = currentUser.get("password").toString();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            String content = null;
            if(encrypted.equals(currentUserPassword)){
                this.commController.setLoggedUser(currentUser);
                content = "Login effettuato";
                a.setContentText(content);
                Optional<ButtonType> result = a.showAndWait();
                if((!result.isPresent() || result.get() == ButtonType.OK)){
                    commController.getDashboardController().setUsername(currentUser.get("username").toString());
                    if(sourceBtn != null){
                        sourceBtn.fire();
                    }else{

                    }
                }
            }else{
                this.wrongLoginAnimation();
            }
        }
    }

    public void moveOutUsernameLabel(MouseEvent mouseEvent) {
        moveOutLabel(usernameLabel, usernameTextField);
        usernameLabelMoved = true;
    }
    public void moveOutPasswordLabel(MouseEvent mouseEvent) {
        moveOutLabel(passwordLabel, passwordField);
        passwordLabelMoved = true;
    }
    private void moveOutLabel(Label label, TextField textField){
        TranslateTransition t = new TranslateTransition(Duration.millis(150), label);
        t.setToX(label.getScaleX() -10);
        t.setToY(label.getScaleY() -20);
        t.play();
        t.setOnFinished(e -> {
            textField.requestFocus();
            label.setTextFill(Color.GRAY);
        });
    }
    private void moveInLabel(Label label){
        TranslateTransition t = new TranslateTransition(Duration.millis(150), label);
        t.setToX(label.getScaleX());
        t.setToY(label.getScaleY());
        t.play();
        t.setOnFinished(e -> {
            label.setTextFill(Color.BLACK);
        });
    }

    public void removeFocus(MouseEvent mouseEvent) {
        if(usernameTextField.isFocused() || passwordField.isFocused()){
            pane.requestFocus();
        }
    }

    private void wrongLoginAnimation(){
        Timeline t1 = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(loginForm.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100),new KeyValue(loginForm.translateXProperty(), -10)),
                new KeyFrame(Duration.millis(200),new KeyValue(loginForm.translateXProperty(), 10)),
                new KeyFrame(Duration.millis(300),new KeyValue(loginForm.translateXProperty(), -10)),
                new KeyFrame(Duration.millis(400),new KeyValue(loginForm.translateXProperty(), 10)),
                new KeyFrame(Duration.millis(500),new KeyValue(loginForm.translateXProperty(), -10))
        );
        t1.play();
        AudioClip clip = new AudioClip(new File("src/view/audio/error.mp3").toURI().toString());
        clip.play();
        passwordField.setText("");
        passwordContainer.getStyleClass().add("error");
        if(userWarnedForUsername || usernameTextField.getText().isEmpty()){
            usernameContainer.getStyleClass().add("error");
        }
        if(usernameTextField.getText().isEmpty()){
            moveInLabel(usernameLabel);
            usernameLabelMoved = false;
        }
        if(passwordField.getText().isEmpty()){
            moveInLabel(passwordLabel);
            passwordLabelMoved = false;
        }
    }

}
