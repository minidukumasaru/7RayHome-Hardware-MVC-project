package lk.ijse.Hardware7RayHome.repository;

import javafx.scene.control.Alert;
import lk.ijse.Hardware7RayHome.db.DbConnection;
import lk.ijse.Hardware7RayHome.model.Item;
import lk.ijse.Hardware7RayHome.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean saveItem(String ItemId, String Name, int Qty, double Price, String Description, Date Date) throws SQLException {
        String sql = "INSERT INTO Item VALUES (?, ? ,? ,? ,? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, ItemId);
        pstm.setObject(2, Name);
        pstm.setObject(3, Qty);
        pstm.setObject(4, Price);
        pstm.setObject(5, Description);
        pstm.setObject(6, Date);

        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this item").show();
        }
        return  false;
    }

    public static Item searchItem(String id) throws SQLException {
        String sql = "SELECT * FROM Item WHERE Name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String ItemId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            int Qty = Integer.parseInt(resultSet.getString(3));
            double Price = Double.parseDouble(resultSet.getString(4));
            String Description = resultSet.getString(5);
            Date Date = resultSet.getDate(6);

            Item item = new Item(ItemId, Name, Qty, Price, Description, Date);
            return item;
        }
        return null;
    }

    public static boolean UPDATE(Item item) throws SQLException {
        String sql = "UPDATE Item SET Name = ?, Qty = ?,Price = ? , Description= ? , Date = ? WHERE ItemId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getName());
        pstm.setObject(2, item.getQty());
        pstm.setObject(3, item.getPrice());
        pstm.setObject(4, item.getDescription());
        pstm.setObject(5, item.getDate());
        pstm.setObject(6, item.getItemId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean DELETE(String ItemId) throws SQLException {
        String sql = "DELETE FROM Item WHERE ItemId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,ItemId);

        return pstm.executeUpdate() > 0;

    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String ItemId = resultSet.getString(1);
            String Name = resultSet.getString(2);
            int Qty = Integer.parseInt(resultSet.getString(3));
            double Price = Double.parseDouble(resultSet.getString(4));
            String Description = resultSet.getString(5);
            Date Date = java.sql.Date.valueOf(resultSet.getString(6));

            Item item = new Item(ItemId, Name, Qty, Price,Description,Date);
            itemList.add(item);
        }
        return itemList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT ItemId FROM Item ORDER BY ItemId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String itemId = resultSet.getString(1);
            return itemId;
        }
        return null;
    }

    public static List<String> getItem() throws SQLException {
        String sql = "SELECT Name FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> itemList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            itemList.add(id);
        }
        return itemList;

    }

    public static boolean UpdateQty(OrderItem items) throws SQLException {
        String sql = "Update Item set Qty = Qty - ? where ItemId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, items.getQuantity());
        pstm.setString(2, items.getItemId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean SaveItem(OrderItem items) throws SQLException {
        String sql = "INSERT INTO OrderItemInfo VALUES(?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, items.getOrderId());
        pstm.setObject(2, items.getItemId());
        pstm.setObject(3, items.getQuantity());
        pstm.setObject(4, items.getPrice());

        return pstm.executeUpdate() > 0;
    }
}
