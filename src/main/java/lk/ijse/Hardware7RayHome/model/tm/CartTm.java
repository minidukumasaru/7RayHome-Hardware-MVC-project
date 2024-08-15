package lk.ijse.Hardware7RayHome.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class CartTm {
    private String ItemId;
    private String ItemName;
    private int Quantity;
    private double Price;
    private double Total;
    private JFXButton btnRemove;
}
