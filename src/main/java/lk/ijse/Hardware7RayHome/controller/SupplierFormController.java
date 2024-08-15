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
import lk.ijse.Hardware7RayHome.model.Supplier;
import lk.ijse.Hardware7RayHome.model.tm.SupplierTm;
import lk.ijse.Hardware7RayHome.repository.SupplierRepo;
import lk.ijse.Hardware7RayHome.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierFormController {

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
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colPRODUCTNAME;

    @FXML
    private TableColumn<?, ?> colQUANTITY;

    @FXML
    private TableColumn<?, ?> colSUPPLIERID;

    @FXML
    private TableColumn<?, ?> colSUPPLIERNAME;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SupplierTm> tblSUPPLIER;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtNICNUMBER;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtPRODUCTNAME;

    @FXML
    private TextField txtQUANTITY;

    @FXML
    private TextField txtSUPPLIERID;

    @FXML
    private TextField txtSUPPLIERNAME;

    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllSuppliers();
        getCurrentSupplierId();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colSUPPLIERID.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colSUPPLIERNAME.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colQUANTITY.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colPRODUCTNAME.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
    }
    private void loadAllSuppliers() {
        ObservableList<SupplierTm> spList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getSupplierName(),
                        supplier.getAddress(),
                        supplier.getContact(),
                        supplier.getQuantity(),
                        supplier.getPrice(),
                        supplier.getProductName(),
                        supplier.getDate(),
                        supplier.getNIC()
                );

                spList.add(tm);
            }

            tblSUPPLIER.setItems(spList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSUPPLIERID.setText("");
        txtSUPPLIERNAME.setText("");
        txtADDRESS.setText("");
        txtCONTACT.setText("");
        txtNICNUMBER.setText("");
        txtQUANTITY.setText("");
        txtPRODUCTNAME.setText("");
        txtPRICE.setText("");
        txtDATE.setText("");
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String SupplierId = txtSUPPLIERID.getText();

        try {
            boolean isDeleted = SupplierRepo.DELETE(SupplierId);
            initialize();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
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
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        int Quantity = Integer.parseInt(txtQUANTITY.getText());
        double Price = Double.parseDouble(txtPRICE.getText());
        String Product = txtPRODUCTNAME.getText();
        Date Date = java.sql.Date.valueOf(txtDATE.getText());
        String Nic = txtNICNUMBER.getText();

        try {
            if (isValied()) {
                boolean isSave = SupplierRepo.SAVE(SupplierId, SupplierName, Address, Contact, Quantity, Price, Product, Date, Nic);
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
        loadAllSuppliers();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String SupplierId = txtSUPPLIERID.getText();

        Supplier supplier = SupplierRepo.SEARCH(SupplierId);
        if (supplier != null) {
            txtSUPPLIERID.setText(supplier.getSupplierId());
            txtSUPPLIERNAME.setText(supplier.getSupplierName());
            txtADDRESS.setText(supplier.getAddress());
            txtCONTACT.setText(String.valueOf(supplier.getContact()));
            txtPRODUCTNAME.setText(supplier.getProductName());
            txtDATE.setText(String.valueOf(supplier.getDate()));
            txtPRICE.setText(String.valueOf(supplier.getPrice()));
            txtQUANTITY.setText(String.valueOf(supplier.getQuantity()));
            txtNICNUMBER.setText(supplier.getNIC());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
        }
    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        int Contact = Integer.parseInt(txtCONTACT.getText());
        int Quantity = Integer.parseInt(txtQUANTITY.getText());
        double Price = Double.parseDouble(txtPRICE.getText());
        String Product = txtPRODUCTNAME.getText();
        Date Date = java.sql.Date.valueOf(txtDATE.getText());
        String Nic = txtNICNUMBER.getText();

        Supplier supplier = new Supplier(SupplierId, SupplierName, Address, Contact, Quantity, Price, Product, Date, Nic);


        try {
            boolean isUpdated = SupplierRepo.UPDATE(supplier);
            if (isUpdated) {
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public boolean isValied() {

        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtSUPPLIERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NIC, txtNICNUMBER)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtPRODUCTNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.QUANTITY, txtQUANTITY)) return false;

        return true;
    }

    public void txtSUPPLIERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtSUPPLIERNAME);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.CONTACT, txtCONTACT);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtQUANTITYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.QUANTITY, txtQUANTITY);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE, txtPRICE);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE);
    }

    public void txtNICNUMBEROnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NIC, txtNICNUMBER);
    }

    public void txtPRODUCTNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtPRODUCTNAME);
    }

    public void tblSUPPLIEROnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblSUPPLIER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtSUPPLIERID.setText(colSUPPLIERID.getCellData(index).toString());
        txtSUPPLIERNAME.setText(colSUPPLIERNAME.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtQUANTITY.setText(colQUANTITY.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtPRODUCTNAME.setText(colPRODUCTNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtNICNUMBER.setText(colNIC.getCellData(index).toString());

    }


    public void txtSUPPLIERIDOnAction(ActionEvent actionEvent) {
        txtSUPPLIERNAME.requestFocus();
    }

    public void txtSUPPLIERNAMEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtQUANTITY.requestFocus();
    }

    public void txtQUANTITYOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtPRODUCTNAME.requestFocus();
    }


    public void txtPRODUCTNAMEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtNICNUMBER.requestFocus();
    }

    public void txtNICNUMBEROnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }

    private void getCurrentSupplierId() throws SQLException {
        String currentId = SupplierRepo.getCurrentId();

        String nextSupplierId = generateNextSupplierId(currentId);
        txtSUPPLIERID.setText(nextSupplierId);
    }

    private String generateNextSupplierId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S00");
            int idNum = Integer.parseInt(split[1]);
            return "S00" + ++idNum;
        }
        return "S001";
    }
}
