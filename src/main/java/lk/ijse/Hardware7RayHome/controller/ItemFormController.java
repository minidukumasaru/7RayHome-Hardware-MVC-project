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
import lk.ijse.Hardware7RayHome.model.Item;
import lk.ijse.Hardware7RayHome.model.tm.ItemTm;
import lk.ijse.Hardware7RayHome.repository.ItemRepo;
import lk.ijse.Hardware7RayHome.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ItemFormController {


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
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colDESCRIPTION;

    @FXML
    private TableColumn<?, ?> colITEMID;

    @FXML
    private TableColumn<?, ?> colITEMNAME;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colQUANTITY;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ItemTm> tblITEM;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtDESCRIPTION;

    @FXML
    private TextField txtITEMID;

    @FXML
    private TextField txtITEMNAME;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtQUANTITY;

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllItems();
        setDate();
        getCurrentItemId();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colITEMID.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colITEMNAME.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colQUANTITY.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void loadAllItems() {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<Item> itemList = ItemRepo.getAll();
            for (Item item : itemList) {
                ItemTm tm = new ItemTm(
                        item.getItemId(),
                        item.getName(),
                        item.getQty(),
                        item.getPrice(),
                        item.getDescription(),
                        item.getDate()
                );

                obList.add(tm);
            }

            tblITEM.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtITEMID.setText("");
        txtITEMNAME.setText("");
        txtQUANTITY.setText("");
        txtPRICE.setText("");
        txtDESCRIPTION.setText("");
        txtDATE.setText("");
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String itemId = txtITEMID.getText();
        try {
            boolean isDeleted = ItemRepo.DELETE(itemId);
            initialize();
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
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
        String ItemId = txtITEMID.getText();
        String Name = txtITEMNAME.getText();
        int Qty = Integer.parseInt(txtQUANTITY.getText());
        double Price = Double.parseDouble(txtPRICE.getText());
        String Description = txtDESCRIPTION.getText();
        Date date = Date.valueOf(LocalDate.now());

        try {
            if (isValied()) {
                boolean isSave = ItemRepo.saveItem(ItemId, Name, Qty, Price, Description, date);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Save Successfully!", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item Save Unsuccessfully!", ButtonType.OK).show();
                }
            }else {
                return;
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        loadAllItems();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtITEMID.getText();
        Item item = ItemRepo.searchItem(id);
        if (item != null) {
            txtITEMID.setText(item.getItemId());
            txtQUANTITY.setText(String.valueOf(item.getQty()));
            txtITEMNAME.setText(item.getName());
            txtPRICE.setText(String.valueOf(item.getPrice()));
            txtDATE.setText(String.valueOf(item.getDate()));
            txtDESCRIPTION.setText(item.getDescription());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item not found!").show();
        }

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String ItemId = txtITEMID.getText();
        String Name = txtITEMNAME.getText();
        int Qty = Integer.parseInt(txtQUANTITY.getText());
        double Price = Double.parseDouble(txtPRICE.getText());
        String Description = txtDESCRIPTION.getText();
        Date date = Date.valueOf(LocalDate.now());

        Item item = new Item(ItemId, Name, Qty, Price, Description, date);
        try {
            boolean isUpdated = ItemRepo.UPDATE(item);
            if (isUpdated) {
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtITEMNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.QUANTITY, txtQUANTITY)) return false;

        return true;
    }

    public void txtITEMNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.NAME, txtITEMNAME);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.PRICE, txtPRICE);
    }

    public void txtQUANTITYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.QUANTITY, txtQUANTITY);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Hardware7RayHome.util.TextField.DATE, txtDATE);
    }

    public void tblITEMOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblITEM.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtITEMID.setText(colITEMID.getCellData(index).toString());
        txtITEMNAME.setText(colITEMNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtDESCRIPTION.setText(colDESCRIPTION.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtQUANTITY.setText(colQUANTITY.getCellData(index).toString());
    }

    public void txtITEMIDOnAction(ActionEvent actionEvent) {
        txtITEMNAME.requestFocus();
    }

    public void txtITEMNAMEOnAction(ActionEvent actionEvent) {
        txtQUANTITY.requestFocus();
    }

    public void txtQUANTITYOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtDESCRIPTION.requestFocus();
    }

    public void txtDESCRIPTIONOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }

    private void getCurrentItemId() throws SQLException {
        String currentId = ItemRepo.getCurrentId();

        String nextItemId = generateNextItemId(currentId);
        txtITEMID.setText(nextItemId);
    }

    private String generateNextItemId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("I00");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "I00" + ++idNum;
        }
        return "I001";
    }
}
