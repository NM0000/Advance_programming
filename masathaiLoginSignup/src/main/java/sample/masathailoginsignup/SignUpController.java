package sample.masathailoginsignup;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_login;

    @FXML
    private Button button_signup;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private RadioButton rb_wittcode;

    @FXML
    private RadioButton rb_someone_else;

    @FXML
    private TextField tf_email;

    @FXML
    private CheckBox cb_malaysia;

    @FXML
    private CheckBox cb_thailand;

    @FXML
    private CheckBox cb_singapore;
    @FXML
    private DatePicker d_date;

    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        rb_wittcode.setToggleGroup(toggleGroup);
        rb_someone_else.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                rb_wittcode.setSelected(true);
            }
        });

        button_signup.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

            if (selectedRadioButton != null &&
                    !tf_username.getText().trim().isEmpty() &&
                    !tf_password.getText().trim().isEmpty() &&
                    !tf_email.getText().trim().isEmpty()) {

                String toggleName = selectedRadioButton.getText();
                String selectedCountry = getSelectedCountry();
                String email = tf_email.getText().trim();
                LocalDate DOB = d_date.getValue();

                if (isEmailValid(email)) {
                    if (isEmailUnique(email)) {
                        DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), toggleName, selectedCountry, email, String.valueOf(DOB));

                    } else {
                        showAlert("Email is already registered!");
                    }
                } else {
                    showAlert("Invalid email format!");
                }

            } else {
                showAlert("Please fill all the information to sign up!");
            }
        });


        button_login.setOnAction(event -> {
            DBUtils.changeScene(event, "sample.fxml", "Login!", null);
        });
    }

    private String getSelectedCountry() {
        if (cb_malaysia.isSelected()) {
            return "Malaysia";
        } else if (cb_thailand.isSelected()) {
            return "Thailand";
        } else if (cb_singapore.isSelected()) {
            return "Singapore";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select your country!");
            alert.show();
            return ""; // Handle the case when no checkbox is selected
        }
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    public static boolean isEmailUnique(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE Email = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginSignup", "root", "Arjun123");
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0; // If count is 0, email is unique
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors here
        }
        return false; // Default to not unique in case of an error
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}