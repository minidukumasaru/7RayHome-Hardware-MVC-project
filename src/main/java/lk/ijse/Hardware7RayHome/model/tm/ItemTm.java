package lk.ijse.Hardware7RayHome.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor@NoArgsConstructor@Data
public class ItemTm {
    private String ItemId;
    private String Name;
    private int Qty;
    private double Price;
    private String Description;
    private Date Date;
}
