package co.com.crediauth.model.rol;

public class Rol {
    private Long idRol;
    private String name;
    private String description;

    public Rol() {
    }

    public Rol(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
