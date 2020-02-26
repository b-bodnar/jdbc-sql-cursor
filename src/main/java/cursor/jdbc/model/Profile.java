package cursor.jdbc.model;

import cursor.jdbc.annotation.Name;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private int id;
    private String username;
    @Name(name = "job_title")
    private String jobTitle;
    private String department;
    private String company;
    private String skill;
}
