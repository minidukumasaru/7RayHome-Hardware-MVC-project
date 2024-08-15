package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class Supplier {
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
