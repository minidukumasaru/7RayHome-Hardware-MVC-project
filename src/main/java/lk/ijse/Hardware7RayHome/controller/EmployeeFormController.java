package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Hardware7RayHome.model.Employee;
import lk.ijse.Hardware7RayHome.model.tm.EmployeeTm;
import lk.ijse.Hardware7RayHome.repository.EmployeeRepo;
import lk.ijse.Hardware7RayHome.repository.UserRepo;
import lk.ijse.Hardware7RayHome.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private JFXButton btnCLEAR;

    @FXML
    private JFXButton btnDELETE;

    @FXML
    private JFXButton btnDETAILS;

    @FXML
    private JFXButton btnSAVE;

    @FXML
    private JFXButton btnSERACH;

    @FXML
    private JFXButton btnUPDATE;

    @FXML
    private TableColumn<?, ?> colADDRESS;

    @FXML
    private TableColumn<?, ?> colATTENDENCE;

    @FXML
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colEMPLOYEEID;

    @FXML
    private TableColumn<?, ?> colEMPLOYEENAME;

    @FXML
    private TableColumn<?, ?> colPOSITION;

    @FXML
    private TableColumn<?, ?> colSALARY;

    @FXML
    private TableColumn<?, ?> colUSERID;

    @FXML
    private TableColumn<?, ?> colWORKHOURS;

    @FXML
    private ComboBox<String> combUSERID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEMPLOYEE;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtATTENDENCE;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtEMPLOYEEID;

    @FXML
    private TextField txtEMPLOYEENAME;

    @FXML
    private TextField txtPOSITION;

    @FXML
    private TextField txtSALARY;

    @FXML
    private TextField txtWORKHOURS;

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllEmployees();
        setDate();
        getUserIds();
        getCurrentEmployeeId();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colEMPLOYEEID.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colEMPLOYEENAME.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colSALARY.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colWORKHOURS.setCellValueFactory(new PropertyValueFactory<>("WorkHours"));
        colATTENDENCE.setCellValueFactory(new PropertyValueFactory<>("Attendence"));
        colPOSITION.setCellValueFactory(new PropertyValueFactory<>("Position"));
        colUSERID.setCellValueFactory(new PropertyValueFactory<>("UserId"));

    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> emList = FXCollections.observableArrayList();

        try {
            List<Employee> employeesList = EmployeeRepo.getAll();
            for (Employee employee : employeesList) {
                EmployeeTm tm = new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getAddress(),
                        employee.getContact(),
                        employee.getDate(),
                        employee.getSalary(),
                        employee.getWorkHours(),
                        employee.getAttendence(),
                        employee.getPosition(),
                        employee.getUserId()

                );

                emList.add(tm);
            }

            tblEMPLOYEE.setItems(emList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void btnCLEAROnAction(ActionEvent event) {
            clearFields();
        }

    private void clearFields() {
        txtEMPLOYEEID.setText("");
        txtEMPLOYEENAME.setText("");
        txtADDRESS.setText("");
        txtCONTACT.setText("");
        txtDATE.setText("");
        txtSALARY.setText("");
        txtWORKHOURS.setText("");
        txtATTENDENCE.setText("");
        txtPOSITION.setText("");
        combUSERID.setValue("");
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();

        try {
            boolean isDeleted = EmployeeRepo.DELETE(EmployeeId);
            initialize();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDETAILSOnAction(ActionEvent event) {

    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        String Address = txtADDRESS.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        Date date = java.sql.Date.valueOf(txtDATE.getText());
        double Salary = Double.parseDouble(txtSALARY.getText());
        String WorkHours = txtWORKHOURS.getText();
        String Attendence = txtATTENDENCE.getText();
        String Position = txtPOSITION.getText();
        String userId = combUSERID.getValue();

        try {
            if (isValied()) {
                boolean isSave = EmployeeRepo.save(EmployeeId, EmployeeName, Address, Contact, date, Salary, WorkHours, Attendence, Position, userId);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Save Successfully!", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Save Unsuccessfully!", ButtonType.OK).show();
                }
            }else {
                return;
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        loadAllEmployees();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String EmployeeId = txtEMPLOYEEID.getText();

        Employee employee = EmployeeRepo.SEARCH(EmployeeId);
        if (employee != null) {
            txtEMPLOYEEID.setText(employee.getEmployeeId());
            txtEMPLOYEENAME.setText(employee.getEmployeeName());
            txtADDRESS.setText(employee.getAddress());
            txtCONTACT.setText(String.valueOf(employee.getContact()));
            txtDATE.setText(String.valueOf(employee.getDate()));
            txtSALARY.setText(String.valueOf(employee.getSalary()));
            txtWORKHOURS.setText(employee.getWorkHours());
            txtATTENDENCE.setText(employee.getAttendence());
            txtPOSITION.setText(employee.getPosition());
            combUSERID.setValue(employee.getUserId());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
        }
    }


    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        String Address = txtADDRESS.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        Date date = java.sql.Date.valueOf(txtDATE.getText());
        double Salary = Double.parseDouble(txtSALARY.getText());
        String WorkHours = txtWORKHOURS.getText();
        String Attendence = txtATTENDENCE.getText();
        String Position = txtPOSITION.getText();
        String UserId = combUSERID.getValue();

        Employee employee = new Employee(EmployeeId, EmployeeName, Address, Contact, date, Salary, WorkHours, Attendence, Position, UserId);

        try {
            boolean isUpdated = EmployeeRepo.UPDATE(employee);
            if (isUpdated) {
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                clearFields();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployees();
    }

    public  boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtEMPLOYEENAME)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE, txtSALARY)) return false;

        return true;
    }


    public void txtEMPLOYEENAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME,txtEMPLOYEENAME);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE,txtDATE);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT,txtCONTACT);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS,txtADDRESS);
    }

    public void txtSALARYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE,txtSALARY);
    }

    public void tblEMPLOYEEOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblEMPLOYEE.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtEMPLOYEEID.setText(colEMPLOYEEID.getCellData(index).toString());
        txtEMPLOYEENAME.setText(colEMPLOYEENAME.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtSALARY.setText(colSALARY.getCellData(index).toString());
        txtWORKHOURS.setText(colWORKHOURS.getCellData(index).toString());
        txtATTENDENCE.setText(colATTENDENCE.getCellData(index).toString());
        txtPOSITION.setText(colPOSITION.getCellData(index).toString());
        combUSERID.setValue(colUSERID.getCellData(index).toString());

    }


    public void txtEMPLOYEEIDOnAction(ActionEvent actionEvent) {
        txtEMPLOYEENAME.requestFocus();
    }

    public void txtEMPLOYEENAMEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtSALARY.requestFocus();
    }

    public void txtSALARYOnAction(ActionEvent actionEvent) {
        txtWORKHOURS.requestFocus();
    }

    public void txtWORKHOURSOnAction(ActionEvent actionEvent) {
        txtATTENDENCE.requestFocus();
    }

    public void txtATTENDENCEOnAction(ActionEvent actionEvent) {
        txtPOSITION.requestFocus();
    }

    public void txtPOSITIONOnAction(ActionEvent actionEvent) {
        combUSERID.requestFocus();
    }

    private void getUserIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> userList = UserRepo.getIds();
            for (String id : userList) {
                obList.add(id);
            }
            combUSERID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentEmployeeId() throws SQLException {
        String currentId = EmployeeRepo.getCurrentId();

        String nextEmployeeId = generateNextEmployeeId(currentId);
        txtEMPLOYEEID.setText(nextEmployeeId);
    }

    private String generateNextEmployeeId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("E00");
            int idNum = Integer.parseInt(split[1]);
            return "E00" + ++idNum;
        }
        return "E001";
    }
}
