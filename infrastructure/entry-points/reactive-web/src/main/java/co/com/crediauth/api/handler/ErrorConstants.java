package co.com.crediauth.api.handler;

public final class ErrorConstants {

    private ErrorConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada");
    }

    public static final class Codes {
        public static final String VALIDATION = "VAL002";
        public static final String BUSINESS = "BUS001";
        public static final String ADMIN = "ADM001";
        public static final String GENERIC = "GEN001";
        public static final String NOT_FOUND = "NF001";
        public static final String UNAUTHORIZED = "UA001";
        public static final String FORBIDDEN = "FB001";
    }

    public static final class Messages {
        public static final String GENERIC_ERROR = "Ha ocurrido un error inesperado: ";
        public static final String NOT_FOUND = "Recurso no encontrado";
        public static final String UNAUTHORIZED = "No autorizado";
        public static final String FORBIDDEN = "Acceso denegado";
    }

    public static final class Success {
        public static final String CODE = "SUC001";
        public static final String MESSAGE = "Operación exitosa";
    }
}