package co.com.crediauth.usecase.user;

import co.com.crediauth.model.user.User;

import java.time.LocalDate;

public class UserBuilder {
    private Long id;
    private String names;
    private String lastNames;
    private String email;
    private String documentId;
    private Double baseSalary;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private Long rolId;

    public UserBuilder() {}

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder names(String names) {
        this.names = names;
        return this;
    }

    public UserBuilder lastNames(String lastNames) {
        this.lastNames = lastNames;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder documentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public UserBuilder baseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    public UserBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder birthDate(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate);
        return this;
    }

    public UserBuilder address(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder rolId(Long rolId) {
        this.rolId = rolId;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setNames(names);
        user.setLastNames(lastNames);
        user.setEmail(email);
        user.setDocumentId(documentId);
        user.setBaseSalary(baseSalary);
        user.setBirthDate(birthDate);
        user.setAddress(address);
        user.setPhone(phone);
        user.setRolId(rolId);
        return user;
    }

    // Métodos de conveniencia para casos comunes
    public static UserBuilder aValidUser() {
        return new UserBuilder()
                .names("Juan")
                .lastNames("Pérez")
                .email("juan.perez@email.com")
                .documentId("12345678")
                .baseSalary(25000.0)
                .birthDate("1990-05-15")
                .address("Calle 123 #45-67")
                .phone("+573001234567")
                .rolId(1L);
    }

    public static UserBuilder anAdminUser() {
        return aValidUser()
                .names("ADMIN")
                .lastNames("System")
                .email("admin@crediauth.com")
                .rolId(1L);
    }

    public static UserBuilder aClientUser() {
        return aValidUser()
                .rolId(2L);
    }

    public static UserBuilder aTestUser() {
        return new UserBuilder()
                .names("Test")
                .lastNames("User")
                .email("test@test.com")
                .documentId("99999999")
                .baseSalary(1000000.0)
                .birthDate("1995-01-01")
                .address("Test Address")
                .phone("3000000000")
                .rolId(2L);
    }

    public static UserBuilder aHighSalaryUser() {
        return aValidUser()
                .baseSalary(20000000.0); // Excede el límite para pruebas
    }

    public static UserBuilder aUserWithExistingEmail(String email) {
        return aValidUser()
                .email(email);
    }

    public static UserBuilder aUserWithExistingDocument(String documentId) {
        return aValidUser()
                .documentId(documentId);
    }
}