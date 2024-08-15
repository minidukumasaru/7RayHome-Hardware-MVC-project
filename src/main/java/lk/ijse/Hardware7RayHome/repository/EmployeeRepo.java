package lk.ijse.Hardware7RayHome.repository;

import javafx.scene.control.Alert;
import lk.ijse.Hardware7RayHome.db.DbConnection;
import lk.ijse.Hardware7RayHome.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> emList = new ArrayList<>();

        while (resultSet.next()) {
            String EmployeeId = resultSet.getString(1);
            String EmployeeName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            int Contact = Integer.parseInt(resultSet.getString(4));
            Date Date = resultSet.getDate(5);
            double Salary = resultSet.getDouble(6);
            String WorkingHours = resultSet.getString(7);
            String Attendence = resultSet.getString(8);
            String Position = resultSet.getString(9);
            String UserId = resultSet.getString(10);
            Employee employee = new Employee(EmployeeId, EmployeeName, Address, Contact, Date, Salary, WorkingHours, Attendence, Position, UserId);
            emList.add(employee);
        }
        return emList;
    }

    public static boolean save(String EmployeeId, String EmployeeName, String Address, int Contact, Date Date, double Salary, String WorkingHours, String Attendence, String Position, String UserId) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?,?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, EmployeeId);
        pstm.setObject(2, EmployeeName);
        pstm.setObject(3, Address);
        pstm.setObject(4, Contact);
        pstm.setObject(5, Date);
        pstm.setObject(6, Salary);
        pstm.setObject(7, WorkingHours);
        pstm.setObject(8, Attendence);
        pstm.setObject(9, Position);
        pstm.setObject(10, UserId);

        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this employee").show();
        }
        return false;
    }

    public static boolean DELETE(String employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE EmployeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employeeId);

        return pstm.executeUpdate() > 0;
    }

    public static Employee SEARCH(String contact) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE EmployeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, contact);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String EmployeeId = resultSet.getString(1);
            String EmployeeName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            int Contact = Integer.parseInt(resultSet.getString(4));
            Date date = resultSet.getDate(5);
            double Salary = Double.parseDouble(resultSet.getString(6));
            String WorkingHours = resultSet.getString(7);
            String Attendence = resultSet.getString(8);
            String Position = resultSet.getString(9);
            String UserId = resultSet.getString(10);


            Employee employee = new Employee(EmployeeId, EmployeeName, Address, Contact, date, Salary, WorkingHours, Attendence, Position, UserId);
            return employee;
        }
        return null;
    }

    public static boolean UPDATE(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET EmployeeName = ?, Address = ?,  Contact = ?, Date = ?, Salary = ?,WorkHours = ?,Attendence =?, Position =?, UserId =? WHERE EmployeeId = ? ";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeName());
        pstm.setObject(2, employee.getAddress());
        pstm.setObject(3, employee.getContact());
        pstm.setObject(4, employee.getDate());
        pstm.setObject(5, employee.getSalary());
        pstm.setObject(6, employee.getWorkHours());
        pstm.setObject(7, employee.getAttendence());
        pstm.setObject(8, employee.getPosition());
        pstm.setObject(9, employee.getUserId());
        pstm.setObject(10, employee.getEmployeeId());
        return pstm.executeUpdate() > 0;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT EmployeeId FROM Employee ORDER BY EmployeeId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            return employeeId;
        }
        return null;
    }
}
