package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class Item {
    private String ItemId;
    private String Name;
    private int Qty;
    private double Price;
    private String Description;
    private Date Date;
}
