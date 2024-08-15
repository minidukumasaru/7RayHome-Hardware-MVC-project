package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Hardware7RayHome.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLOGIN;

    @FXML
    private Button btnREGISTERNOW;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane rootNodeSmall;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

    private void checkCredential(String username, String password) throws SQLException, IOException {
        String sql = "SELECT UserName,Password FROM User WHERE UserName = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, username);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String dbpassword = resultSet.getString("Password");

            if (password.equals(dbpassword)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user name can't be find!").show();
        }
    }
    @FXML
    void btnLOGINOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            checkCredential(username, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnREGISTERNOWOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/RegisterForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register Form");
    }

    @FXML
    void txtPasswordOnAction(ActionEvent actionEvent) {
        btnLOGINOnAction(actionEvent);

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();

    }

}
