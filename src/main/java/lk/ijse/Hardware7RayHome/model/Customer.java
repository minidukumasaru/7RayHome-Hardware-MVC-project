package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String CustomerId;
    private String CustomerName;
    private int Contact;
    private String Address;
    private String Nic;
    private Date date;
}
