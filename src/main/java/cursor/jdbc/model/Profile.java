package cursor.jdbc.model;

import cursor.jdbc.annotation.Name;
import cursor.jdbc.annotation.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "profiles")
public class Profile implements Model{

    @Setter(AccessLevel.NONE)
    private int id;
    private String username;

    @Name(name = "job_title")
    private String jobTitle;
    private String department;
    private String company;
    private String skill;

    public Profile(String username, String jobTitle, String department, String company, String skill) {
        this.username = username;
        this.jobTitle = jobTitle;
        this.department = department;
        this.company = company;
        this.skill = skill;
    }
}
