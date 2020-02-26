package cursor.jdbc.model;

import cursor.jdbc.annotation.Name;
import cursor.jdbc.annotation.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements Model{

    private int id;
    @Name(name = "first_name")
    private String firstName;
    @Name(name = "last_name")
    private String lastName;
    private String city;
    private String gender;
    private String username;
}
