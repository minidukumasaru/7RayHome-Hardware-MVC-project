package lk.ijse.Hardware7RayHome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class User {
    private   String UserId;
    private  String UserName;
    private Date Date;
    private String Password;
}
