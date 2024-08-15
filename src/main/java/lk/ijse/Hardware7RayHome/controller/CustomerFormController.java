package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Hardware7RayHome.model.Customer;
import lk.ijse.Hardware7RayHome.model.tm.CustomerTm;
import lk.ijse.Hardware7RayHome.repository.CustomerRepo;
import lk.ijse.Hardware7RayHome.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class CustomerFormController {

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
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colCUSTOMERID;

    @FXML
    private TableColumn<?, ?> colCUSTOMERNAME;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CustomerTm> tblCUSTOMER;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtCUSTOMERID;

    @FXML
    private TextField txtCUSTOMERNAME;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtNIC;

    ObservableList<CustomerTm> obList;

    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllCustomers();
        getCurrentCustomerId();
        searchFilter();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colCUSTOMERID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colCUSTOMERNAME.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    //ObservableList<CustomerTm> obList;
    private void loadAllCustomers() {
        obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getCustomerId(),
                        customer.getCustomerName(),
                        customer.getContact(),
                        customer.getAddress(),
                        customer.getNic(),
                        customer.getDate()
                );

                obList.add(tm);
            }

            tblCUSTOMER.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtCUSTOMERID.setText("");
        txtCUSTOMERNAME.setText("");
        txtADDRESS.setText("");
        txtCONTACT.setText("");
        txtNIC.setText("");
        txtDATE.setText("");
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String CustomerId = txtCUSTOMERID.getText();

        try {
            boolean isDeleted = CustomerRepo.DELETE(CustomerId);
            initialize();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully!").show();
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
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        String Address = txtADDRESS.getText();
        String Nic = txtNIC.getText();
        Date date = Date.valueOf(LocalDate.now());

        try {
            if (isValied()) {
                boolean isSave = CustomerRepo.SAVE(CustomerId, CustomerName, Contact, Address,Nic, date);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer save successfully!!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Can't save this customer").show();
                }
            }else {
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        loadAllCustomers();

    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String CustomerId = txtCUSTOMERID.getText();

        Customer customer = CustomerRepo.SEARCH(CustomerId);
        if (customer != null) {
            txtCUSTOMERID.setText(customer.getCustomerId());
            txtCUSTOMERNAME.setText(customer.getCustomerName());
            txtCONTACT.setText(String.valueOf(customer.getContact()));
            txtADDRESS.setText(customer.getAddress());
            txtNIC.setText(customer.getNic());
            txtDATE.setText(String.valueOf(customer.getDate()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
        }

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        String Address = txtADDRESS.getText();
        String Nic = txtNIC.getText();
        Date date = java.sql.Date.valueOf(txtDATE.getText());

        Customer customer = new Customer(CustomerId, CustomerName, Contact, Address, Nic, date);

        try {
            boolean isUpdated = CustomerRepo.UPDATE(customer);
            if (isUpdated) {
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtCUSTOMERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NIC, txtNIC)) return false;

        return true;
    }

    public void txtCUSTOMERIDOnKeyReleased(KeyEvent keyEvent) {

    }

    public void txtCUSTOMERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtCUSTOMERNAME);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT, txtCONTACT);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtNICNUMBEROnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NIC, txtNIC);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE);
    }

    public void tblCUSTOMEROnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblCUSTOMER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtCUSTOMERID.setText(colCUSTOMERID.getCellData(index).toString());
        txtCUSTOMERNAME.setText(colCUSTOMERNAME.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtNIC.setText(colNIC.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
    }

    public void txtCUSTOMERIDOnAction(ActionEvent actionEvent) {
        txtCUSTOMERNAME.requestFocus();
    }

    public void txtCUSTOMERNAMEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtNIC.requestFocus();
    }

    public void txtNICNUMBEROnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }

    private void getCurrentCustomerId() throws SQLException {
        String currentId = CustomerRepo.getCurrentId();

        String nextCustomerId = generateNextCustomerId(currentId);
        txtCUSTOMERID.setText(nextCustomerId);

    }

    private String generateNextCustomerId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C00");
            int idNum = Integer.parseInt(split[1]);
            return "C00" + ++idNum;
        }
        return "C001";
    }

    private void searchFilter(){
        FilteredList<CustomerTm> filterData = new FilteredList<>(obList, e -> true);
        txtNIC.setOnKeyPressed(e ->{
            txtNIC.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super CustomerTm>) customer ->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (customer.getCustomerId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(customer.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<CustomerTm> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblCUSTOMER.comparatorProperty());
            tblCUSTOMER.setItems(buyer);
        });
    }
}
