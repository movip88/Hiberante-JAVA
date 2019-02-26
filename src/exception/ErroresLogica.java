package exception;

/**
 * Clase enecargada de controlar las excepciones de logica de la aplicación
 *
 * @author polmonleonvives
 */
public class ErroresLogica extends Exception {

    public static int LOGGIN_ERROR = 0;
    public static int OPTION_ERROR = 1;
    public static int NOT_LOGGED = 2;
    public static int NOT_PERMIS = 3;
    public static int USER_EXITS = 4;
    public static int ID_EXPEDIENTE_ERROR = 5;
    public static int ID_USUARIO_ERROR = 6;
    public static int NO_DELETE_USER = 7;

    private String[] msgExceptions = {"Usario o contraseña incorrecta",
        "Opción no disponible",
        "No estas loggeado en la aplicación",
        "No tienes permiso para acceder a esta opción",
        "Ya existe un usuario con esa matricula",
        "No hay ningun expediente con este id",
        "No hay ningun usuario con esta matricula",
        "No te puedes borrarte a ti mismo"};

    private int code;

    public ErroresLogica(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.msgExceptions[this.code];
    }

}
