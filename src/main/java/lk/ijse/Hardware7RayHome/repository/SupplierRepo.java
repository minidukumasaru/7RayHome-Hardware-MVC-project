package lk.ijse.Hardware7RayHome.repository;

import javafx.scene.control.Alert;
import lk.ijse.Hardware7RayHome.db.DbConnection;
import lk.ijse.Hardware7RayHome.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String SupplierId = resultSet.getString(1);
            String SupplierName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            int Contact = Integer.parseInt(resultSet.getString(4));
            int Quantity = Integer.parseInt(resultSet.getString(5));
            double Price = resultSet.getDouble(6);
            String Product = resultSet.getString(7);
            Date Date = resultSet.getDate(8);
            String Nic = resultSet.getString(9);

            Supplier supplier = new Supplier(SupplierId, SupplierName, Address, Contact, Quantity, Price, Product, Date,Nic);
            supList.add(supplier);
        }
        return supList;
    }

    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean SAVE(String SupplierId, String SupplierName, String Address, int Contact, int Quantity, double Price, String Product, Date Date, String NicNumber) throws SQLException {
        String sql = "INSERT INTO Supplier VALUES(?, ?, ?, ?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, SupplierId);
        pstm.setObject(2, SupplierName);
        pstm.setObject(3, Address);
        pstm.setObject(4, Contact);
        pstm.setObject(5, Quantity);
        pstm.setObject(6, Price);
        pstm.setObject(7, Product);
        pstm.setObject(8, Date);
        pstm.setObject(9, NicNumber);

        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this employee").show();
        }
        return false;
    }

    public static Supplier SEARCH(String supplierId) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplierId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String SupplierId = resultSet.getString(1);
            String SupplierName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            int Contact = Integer.parseInt(resultSet.getString(4));
            int Quantity = Integer.parseInt(resultSet.getString(5));
            double Price = resultSet.getDouble(6);
            String ProductName = resultSet.getString(7);
            Date Date = resultSet.getDate(8);
            String Nic = resultSet.getString(9);

            Supplier supplier = new Supplier(SupplierId, SupplierName, Address, Contact, Quantity, Price, ProductName,Date, Nic);
            return supplier;
        }

        return null;
    }

    public static boolean UPDATE(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET SupplierName = ?, Address = ?, Contact = ? , Quantity = ? , Price = ? , ProductName = ? ,Date= ? ,NIC = ? WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplier.getSupplierName());
        pstm.setObject(2, supplier.getAddress());
        pstm.setObject(3, supplier.getContact());
        pstm.setObject(4, supplier.getQuantity());
        pstm.setObject(5, supplier.getPrice());
        pstm.setObject(6, supplier.getProductName());
        pstm.setObject(7, supplier.getDate());
        pstm.setObject(8, supplier.getNIC());
        pstm.setObject(9,supplier.getSupplierId());
        return pstm.executeUpdate() > 0;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT SupplierId FROM Supplier ORDER BY SupplierId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String supplierId = resultSet.getString(1);
            return supplierId;
        }
        return null;
    }
}
