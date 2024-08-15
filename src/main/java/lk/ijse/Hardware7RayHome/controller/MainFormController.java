package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFormController {

    public Label lblTIME;
    public Label lblDATE;
    @FXML
    private AnchorPane AnpMain;

    @FXML
    private AnchorPane anpmain;

    @FXML
    private AnchorPane anpmain1;

    @FXML
    private AnchorPane anpmain2;

    @FXML
    private JFXButton btnCUSTOMER;

    @FXML
    private JFXButton btnDASHBOARD;

    @FXML
    private JFXButton btnEMPLOYEE;

    @FXML
    private JFXButton btnITEM;

    @FXML
    private JFXButton btnLOGOUT;

    @FXML
    private JFXButton btnORDERS;

    @FXML
    private JFXButton btnSUPPLIER;

    public void initialize() throws IOException {
        loadDashboardForm();
        setDate();
        setCurrentTime();
    }

    private void setCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        lblTIME.setText(formattedTime);
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDATE.setText(String.valueOf(now));
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(dashboardPane);
    }

    @FXML
    void btnCUSTOMEROnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(customerPane);

    }



    @FXML
    void btnDASHBOARDOnAction(ActionEvent event) throws IOException {
        AnchorPane dashbordPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(dashbordPane);

    }

    @FXML
    void btnEMPLOYEEOnAction(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(employeePane);

    }

    @FXML
    void btnITEMOnAction(ActionEvent event) throws IOException {
        AnchorPane orderPane = FXMLLoader.load(this.getClass().getResource("/view/ItemForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(orderPane);

    }

    @FXML
    void btnLOGOUTOnAction(ActionEvent event) throws IOException {
        AnchorPane placePane = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));


        AnpMain.getChildren().clear();
        AnpMain.getChildren().add(placePane);

    }

    @FXML
    void btnORDERSOnAction(ActionEvent event) throws IOException {
        AnchorPane placePane = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(placePane);

    }

    @FXML
    void btnSUPPLIEROnAction(ActionEvent event) throws IOException {
        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/SupplierForm.fxml"));


        anpmain.getChildren().clear();
        anpmain.getChildren().add(supplierPane);

    }

}
