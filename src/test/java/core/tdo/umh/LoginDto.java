package core.tdo.umh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String company;
    private String user;
    private String password;
    private String title;
}
