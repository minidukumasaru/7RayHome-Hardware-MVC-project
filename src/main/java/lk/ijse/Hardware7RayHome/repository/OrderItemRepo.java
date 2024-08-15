package lk.ijse.Hardware7RayHome.repository;

import lk.ijse.Hardware7RayHome.db.DbConnection;
import lk.ijse.Hardware7RayHome.model.OrderItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderItemRepo {
    public static boolean save(List<OrderItem> odList) throws SQLException {
        for (OrderItem od : odList){
            boolean isSaved = Save(od);
            if(!isSaved){
                return false;
            }
        }
        return true;
    }

    private static boolean Save(OrderItem od) throws SQLException {
        String sql = "INSERT INTO OrderItemInfo VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItemId());
        pstm.setInt(3, od.getQuantity());
        pstm.setDouble(4, od.getPrice());

        return pstm.executeUpdate() > 0;
    }
}
