package co.com.crediauth.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="roles")
public class RolEntity {
    @Id
    @Column("id_rol")
    private Long idRol;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}
