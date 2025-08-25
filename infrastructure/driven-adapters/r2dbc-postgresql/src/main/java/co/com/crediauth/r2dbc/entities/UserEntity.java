package co.com.crediauth.r2dbc.entities;


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

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("correo_electronico")
    private String correoElectronico;

    @Column("salario_base")
    private Double salarioBase;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column("direccion")
    private String direccion;

    @Column("telefono")
    private String telefono;
}
