package lk.ijse.Hardware7RayHome.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor@AllArgsConstructor@Data
public class OrderTm {
    private  String OrderId;
    private Date Date;
    private double Price;
    private String NIC;
    private String UserId;

}
