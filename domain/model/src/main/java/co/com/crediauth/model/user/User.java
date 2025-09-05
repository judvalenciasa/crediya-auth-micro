package co.com.crediauth.model.user;

import java.time.LocalDate;

public class User {
    private Long id;
    private Long rolId;
    private String names;
    private String lastNames;
    private String email;
    private Double baseSalary;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String documentId;
    private String password;
    private boolean enabled;
    private int loginAttempts;


    public User() {
    }

    public User(String nombres, String lastNames, String email, Double baseSalary, LocalDate  birthDate, String address, String phone, String documentId) {
        this.names = nombres;
        this.lastNames = lastNames;
        this.email = email;
        this.baseSalary = baseSalary;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.documentId = documentId;
        this.loginAttempts = 3;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public LocalDate  getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate  birthDate) {
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
