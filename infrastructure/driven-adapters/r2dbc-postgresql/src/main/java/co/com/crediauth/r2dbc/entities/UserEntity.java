package co.com.crediauth.r2dbc.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="usuarios")
public class UserEntity {

    @Id
    private Long id;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("correoElectronico")
    private String correoElectronico;

    @Column("salarioBase")
    private Double salarioBase;

    @Column("fechaNacimiento")
    private String fechaNacimiento;

    @Column("direccion")
    private String direccion;

    @Column("telefono")
    private String telefono;
}
