package co.com.crediauth.r2dbc.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name="users")
public class UserEntity {
    @Id
    private Long id;

    @Column("names")
    private String names;

    @Column("lastNames")
    private String lastNames;

    @Column("email")
    private String email;

    @Column("baseSalary")
    private Double baseSalary;

    @Column("birthDate")
    private LocalDate birthDate;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;
}
