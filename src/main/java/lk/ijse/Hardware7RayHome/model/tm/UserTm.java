package lk.ijse.Hardware7RayHome.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class UserTm {
    private   String UserId;
    private  String UserName;
    private java.sql.Date Date;
    private String Password;
}
