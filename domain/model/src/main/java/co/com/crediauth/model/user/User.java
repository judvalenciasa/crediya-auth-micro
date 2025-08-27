package co.com.crediauth.model.user;


public class User {
    private Long id;
    private String names;
    private String lastNames;
    private String email;
    private Double baseSalary;
    private String birthDate;
    private String address;
    private String phone;


    public User(){}

    public User(String nombres, String lastNames, String email, Double baseSalary, String birthDate, String address, String phone) {
        this.names = nombres;
        this.lastNames = lastNames;
        this.email = email;
        this.baseSalary = baseSalary;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String nombres) {
        this.names = nombres;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
