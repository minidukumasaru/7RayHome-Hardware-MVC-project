package lk.ijse.Hardware7RayHome.repository;

import javafx.scene.control.Alert;
import lk.ijse.Hardware7RayHome.db.DbConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT UserId FROM User";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }



    public static void RegisterNow(String UserId, String UserName, String Password, Date Date) throws SQLException {
        String sql = "INSERT INTO User VALUES (?, ? ,?, ? )";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, UserId);
        pstm.setObject(2, UserName);
        pstm.setObject(3, Password);
        pstm.setObject(4, Date);

        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Register successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't register this user").show();
        }

    }

    public static String getCurrentId() throws SQLException {

        String sql = "SELECT UserId FROM User ORDER BY UserId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String userId = resultSet.getString(1);
            return userId;
        }
        return null;


    }
}
