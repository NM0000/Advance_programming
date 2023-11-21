package sample.masathailoginsignup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    @FXML
    private PasswordField tf_password;

    @FXML
private Button button_login;

@FXML

private Button button_signup;

@FXML
private TextField tf_username;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                String inputUsername = tf_username.getText();
                String inputText = tf_password.getText(); // Get text from the password field
//                System.out.println("Input Text: " + inputText + inputUsername);

                if (inputText.isEmpty()) {
                    System.out.println("Password is empty!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Password is empty!");
                    alert.showAndWait();
                } else {
                    // Call your login method here with username and password
                    String username = tf_username.getText();
                    DBUtils.logInUser(event,username, inputText);
                }
            }


    });

    button_signup.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
             DBUtils.changeScene(event, "sign-up.fxml", "Sign Up!", null);
        }
    });
    }
}