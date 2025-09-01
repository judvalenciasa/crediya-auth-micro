package co.com.crediauth.r2dbc.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name="usuarios")
public class UserEntity {
    @Id
    private Long id;

    @Column("id_rol")
    private Long rolId;

    @Column("nombre")
    private String names;

    @Column("apellido")
    private String lastNames;

    @Column("email")
    private String email;

    @Column("salario_base")
    private Double baseSalary;

    @Column("fecha_nacimiento")
    private LocalDate birthDate;

    @Column("direccion")
    private String address;

    @Column("telefono")
    private String phone;

    @Column("documento_identidad")
    private String documentId;

    @Column("clave")
    private String password;

    @Column("activo")
    private boolean enabled;
}
