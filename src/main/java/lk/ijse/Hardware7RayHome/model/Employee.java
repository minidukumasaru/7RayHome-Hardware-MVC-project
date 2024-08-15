package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@AllArgsConstructor@Data
public class Employee {
    private String EmployeeId;
    private String EmployeeName;
    private String Address;
    private int Contact;
    private java.sql.Date Date;
    private double Salary;
    private String WorkHours;
    private String Attendence;
    private String Position;
    private String UserId;
}
