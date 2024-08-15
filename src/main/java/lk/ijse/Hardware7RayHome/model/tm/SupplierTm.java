package lk.ijse.Hardware7RayHome.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor@AllArgsConstructor@Data
public class SupplierTm {
    private String SupplierId;
    private String SupplierName;
    private String Address;
    private int Contact;
    private int Quantity;
    private double Price;
    private String ProductName;
    private java.sql.Date Date;
    private String NIC;
}
