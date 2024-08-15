package lk.ijse.Hardware7RayHome.controller;


import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.Hardware7RayHome.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardFormController {

    public Label lblCustomerCount;
    public Label lblEmployeeCount;
    public Label lblSupplierCount;

    private  int employeeCount;
    private int supplierCount;
    private  int customerCount;


    public void initialize() {
        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);

        try {
            supplierCount = getSupplierCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);

        try {
            employeeCount = getEmployeeCount();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmployeeCount(employeeCount);
    }

    private void setEmployeeCount(int employeeCount) { lblEmployeeCount.setText(String.valueOf(employeeCount));}
    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }
    private void setSupplierCount(int supplierCount){ lblSupplierCount.setText(String.valueOf(supplierCount));}
    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customerCount FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("customerCount");
        }
        return 0;
    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplierCount FROM Supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("supplierCount");
        }
        return 0;
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employeeCount FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("employeeCount");
        }
        return 0;
    }
}
