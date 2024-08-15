package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Hardware7RayHome.repository.UserRepo;
import lk.ijse.Hardware7RayHome.util.Regex;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterFormController {

    public AnchorPane rootNode;
    public JFXButton btnBACK;
    public TextField txtUSERNAME;
    public TextField txtPassword;
    public TextField txtUSERID;
    public TextField txtDATE;
    @FXML
    private JFXButton btnREGISTERNOW;

    @FXML
    private AnchorPane rootNodeSmall;


    public void initialize() throws SQLException {
        setDate();
        getCurrentUserId();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    @FXML
    void btnREGISTERNOWOnAction(ActionEvent event) throws IOException {
        String UserId = txtUSERID.getText();
        String UserName = txtUSERNAME.getText();
        String Password = txtPassword.getText();
        Date date = Date.valueOf(txtDATE.getText());
        try {
            UserRepo.RegisterNow(UserId, UserName, Password,date);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Main Form");
        stage.centerOnScreen();
    }


    public void btnBACKOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void txtUSERNAMEOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        txtUSERID.requestFocus();

    }

    @FXML
    void txtUSERIDOnAction(ActionEvent event) {
        txtDATE.requestFocus();

    }

    @FXML
    void txtDATEOnAction(ActionEvent actionEvent) throws IOException {
        btnREGISTERNOWOnAction(actionEvent);

    }

    public void txtUSERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.USERNAME, txtUSERNAME);
    }

    public void txtUSERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtPassword);

    }

    public void txtPASSWORDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PASSWORD, txtPassword);

    }
    public void txtDATEONKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE);
    }

    private void getCurrentUserId() throws SQLException {
        String currentId = UserRepo.getCurrentId();

        String nextuserId = generateNextUserId(currentId);
        txtUSERID.setText(nextuserId);
    }

    private String generateNextUserId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("U");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "U00" + ++idNum;
        }
        return "U001";
    }


}



