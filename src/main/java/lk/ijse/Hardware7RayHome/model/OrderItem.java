package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@AllArgsConstructor@Data
public class OrderItem {
    private String  OrderId;
    private String ItemId;
    private int Quantity;
    private double Price;
}
