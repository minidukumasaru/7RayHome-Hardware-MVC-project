package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class Order {
    private  String OrderId;
    private java.sql.Date Date;
    private double Price;
    private String NIC;
    private String UserId;
}
