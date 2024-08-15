package lk.ijse.Hardware7RayHome.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Hardware7RayHome.model.*;
import lk.ijse.Hardware7RayHome.model.tm.CartTm;
import lk.ijse.Hardware7RayHome.repository.*;

import net.sf.jasperreports.engine.export.data.TextValue;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {

   @FXML
    public Label lblNETBALANCE;
    @FXML
    private JFXButton btnADDTOCART;

    @FXML
    private JFXButton btnPLACEORDER;

    @FXML
    private TableColumn<?, ?> colACTION;

    @FXML
    private TableColumn<?, ?> colITEMID;

    @FXML
    private TableColumn<?, ?> colITEMNAME;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colQUANTITY;

    @FXML
    private TableColumn<?, ?> colTOTAL;

    @FXML
    private ComboBox<String> combUSERID;

    @FXML
    private TableView<CartTm> tblPLACEORDER;

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
    private TextField txtITEMID;

    @FXML
    private TextField txtITEMNAME;

    @FXML
    private TextField txtNICNUMBER;

    @FXML
    private TextField txtORDERID;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtQTYONHAND;

    @FXML
    private TextField txtQUANTITY;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        setDate();
        getUserId();
        setCellValueFactory();
        getCurrentOrderId();
        setNIC();
        setItemName();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colITEMID.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colITEMNAME.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colQUANTITY.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colTOTAL.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colACTION.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    public void clearFields(){
        txtITEMNAME.clear();
        txtORDERID.clear();
        txtQUANTITY.clear();
        txtPRICE.clear();
        txtCONTACT.clear();
        txtPRICE.clear();
       // lblNETBALANCE.clear();
        txtDATE.clear();
        txtNICNUMBER.clear();
        txtQTYONHAND.clear();
        txtCUSTOMERNAME.clear();
        txtCUSTOMERID.clear();
        combUSERID.setValue("");
        txtADDRESS.clear();
        txtITEMID.clear();
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            txtORDERID.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void getUserId() {
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

    private void calculateNetTotal() {
        int netBalance = 0;
        for (int i = 0; i < tblPLACEORDER.getItems().size(); i++) {
            netBalance += (double) colTOTAL.getCellData(i);
        }
        lblNETBALANCE.setText(String.valueOf(netBalance));
    }

    @FXML
    void btnADDTOCARTOnAction(ActionEvent event) {
        String ItemId = txtITEMID.getText();
        String ItemName = txtITEMNAME.getText();
        int Quantity = Integer.parseInt(txtQUANTITY.getText());
        double Price = Double.parseDouble(txtPRICE.getText());
        double Total = Quantity * Price;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPLACEORDER.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblPLACEORDER.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblPLACEORDER.getItems().size(); i++) {
            if (ItemId.equals(colITEMID.getCellData(i))) {

                CartTm tm = obList.get(i);
                Quantity += tm.getQuantity();
                Total = Quantity * Price;

                tm.setQuantity(Quantity);
                tm.setTotal(Total);

                tblPLACEORDER.refresh();

                calculateNetTotal();
                return;
            }
        }


        CartTm tm = new CartTm(ItemId, ItemName, Quantity, Price, Total, btnRemove);
        obList.add(tm);

        tblPLACEORDER.setItems(obList);
        calculateNetTotal();
        txtQUANTITY.setText("");

    }


    @FXML
    void btnPLACEORDEROnAction(ActionEvent event) {
        String OrderId = txtORDERID.getText();
        Date date = Date.valueOf(LocalDate.now());
        double Price = Double.parseDouble(txtPRICE.getText());
        String Nic = txtNICNUMBER.getText();
        String UserId = combUSERID.getValue();



        var order = new Order(OrderId,date,Price,Nic,UserId);

        List<OrderItem>  odList = new ArrayList<>();

        for (int i = 0; i < tblPLACEORDER.getItems().size(); i++){
            CartTm tm = obList.get(i);

            OrderItem od = new OrderItem(
                    OrderId,
                    tm.getItemId(),
                    tm.getQuantity(),
                    tm.getPrice()
            );
            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblPLACEORDER.setItems(obList);
                calculateNetTotal();
                getCurrentOrderId();

            }else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
    }


    @FXML
    void txtITEMNAMEOnAction(ActionEvent event) {
        String itemName = txtITEMNAME.getText();

        try {
            Item item = ItemRepo.searchItem(itemName);
            txtITEMID.setText(item.getItemId());
            txtQTYONHAND.setText(String.valueOf(item.getQty()));
            txtPRICE.setText(String.valueOf(item.getPrice()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void   setItemName() throws SQLException {
        List<String> item = ItemRepo.getItem();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String n : item){
            obList.add(n);
        }

        TextFields.bindAutoCompletion(txtITEMNAME, obList);
    }

    @FXML
    void txtNICNUMBEROnAction(ActionEvent event) {
        String nic =  txtNICNUMBER.getText();

        try {
            Customer customer = CustomerRepo.SEARCHbyNic(nic);

            txtCUSTOMERID.setText(customer.getCustomerId());
            txtCUSTOMERNAME.setText(customer.getCustomerName());
            txtADDRESS.setText(customer.getAddress());
            txtCONTACT.setText(String.valueOf(customer.getContact()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setNIC() throws SQLException {
        List<String> nic = CustomerRepo.getNIC();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String n : nic){
            obList.add(n);
        }

        TextFields.bindAutoCompletion(txtNICNUMBER, obList);
    }



}
