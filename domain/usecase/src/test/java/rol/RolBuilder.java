package rol;

import co.com.crediauth.model.rol.Rol;

public class RolBuilder {
    private Long idRol;
    private String name;
    private String description;

    public static RolBuilder aValidRol() {
        return new RolBuilder()
                .idRol(1L)
                .name("USER")
                .description("Basic user role");
    }

    public RolBuilder idRol(Long idRol) {
        this.idRol = idRol;
        return this;
    }

    public RolBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RolBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Rol build() {
        return Rol.builder()
                .idRol(idRol)
                .name(name)
                .description(description)
                .build();
    }
}
