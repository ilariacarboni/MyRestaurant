package view.sceneControllers;

import business.AdminManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginPaneController extends BaseView implements Initializable {

    private final String WRONG_USERNAME_WARNING = "Username errato";
    private final String WRONG_PASSWORD_WARNING = "Password errata";
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

    private MediaPlayer errorMediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.commController.setLoginPaneController(this);

        setUsernameTextFieldListeners();
        setPasswordTextFieldListeners();

        pane.setBackground(imagesProvider.getBackground());
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);

        loginBtn.setOnMouseClicked((e) -> {
            this.doLogin();
        });

        this.sourceBtn = (commController.getDashboardController()).getDashboardBtn();
        this.initializeErrorMediaPlayer();
    }

    private void initializeErrorMediaPlayer(){
        try {
            Media hit = new Media(getClass().getResource("/view/audio/error.mp3").toURI().toString());
            errorMediaPlayer = new MediaPlayer(hit);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSource(Button menuBtn){
        sourceBtn = menuBtn;
    }
    private void setUsernameTextFieldListeners(){
        usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(userWarnedForUsername){
                userWarnedForUsername = false;
                wrongUsernameLabel.setText(null);
            }
            if(usernameContainer.getStyleClass().contains("error")){
                usernameContainer.getStyleClass().remove("error");
            }
        });
        usernameTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue){
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
        });
    }
    private void setPasswordTextFieldListeners(){
        passwordField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            String password = passwordField.getText();
            if (!newPropertyValue){
                if(password.isEmpty() && passwordLabelMoved){
                    moveInLabel(passwordLabel);
                    passwordLabelMoved = false;
                }
            }else{
                if(!passwordLabelMoved){
                    moveOutLabel(passwordLabel, passwordField);
                }
            }
        });
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(passwordContainer.getStyleClass().contains("error")){
                passwordContainer.getStyleClass().remove("error");
            }
        });
        passwordField.setOnKeyPressed((e) -> {
            if(e.getCode() == KeyCode.ENTER){
                this.doLogin();
            }
        });
    }
    public void doLogin() {
        String unencryptedPsw = passwordField.getText();
        String encrypted = adminManager.cryptPassword(unencryptedPsw);
        DashboardController dashboardController = commController.getDashboardController();
        if(currentUser != null){
            String currentUserPassword = currentUser.get("password").toString();
            if(encrypted.equals(currentUserPassword)){
                this.commController.setLoggedUser(currentUser);
                dashboardController.setUsername(currentUser.get("username").toString());
                if(sourceBtn != null){
                    sourceBtn.fire();
                }
                dashboardController.removeLoginButton();
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
        errorMediaPlayer.play();
        errorMediaPlayer.seek(Duration.ZERO);
        passwordField.setText("");
        passwordContainer.getStyleClass().add("error");
        if(userWarnedForUsername || usernameTextField.getText().isEmpty()){
            usernameContainer.getStyleClass().add("error");
        }
        if(usernameTextField.getText().isEmpty()){
            moveInLabel(usernameLabel);
            usernameLabelMoved = false;
        }
        if(!passwordField.isFocused()){
            moveInLabel(passwordLabel);
            passwordLabelMoved = false;
        }
    }
}
