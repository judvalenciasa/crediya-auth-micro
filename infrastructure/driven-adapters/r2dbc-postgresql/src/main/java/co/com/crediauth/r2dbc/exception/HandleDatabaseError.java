package co.com.crediauth.r2dbc.exception;

public class HandleDatabaseError extends RuntimeException {

    public HandleDatabaseError(String mesage) {
        super(mesage);
    }
}
