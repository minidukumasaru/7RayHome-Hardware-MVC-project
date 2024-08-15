package lk.ijse.Hardware7RayHome.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class EmployeeTm {
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
