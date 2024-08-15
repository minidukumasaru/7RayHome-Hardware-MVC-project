package lk.ijse.Hardware7RayHome.repository;

import lk.ijse.Hardware7RayHome.db.DbConnection;
import lk.ijse.Hardware7RayHome.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String Order_id = resultSet.getString(1);
            return Order_id;
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders VALUES(?,?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setString(2, String.valueOf(order.getDate()));
        pstm.setString(3, String.valueOf(order.getPrice()));
        pstm.setString(4, order.getNIC());
        pstm.setString(5, order.getUserId());

        return pstm.executeUpdate() > 0;

    }

}
