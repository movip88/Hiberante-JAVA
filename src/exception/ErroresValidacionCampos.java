package exception;

/**
 * Clase enecargada de las excepciones de validacieones de los campos de las
 * clases Usuarios y Expedientes
 *
 * @author polmonleonvives
 */
public class ErroresValidacionCampos extends Exception {

    public static int ERROR_LENTH = 0;
    public static int ERROR_DNI_FORMAT = 1;
    public static int USER_TYPE_ERROR = 2;

    public static int NOMBRE_APELLIDOS_EXPEDIENTE = 30;
    public static int NOMBRE_APELLIDOS_USEUARIO = 25;
    public static int MATRICULA_CP = 6;
    public static int PASS = 8;
    public static int TELEFONO = 12;
    public static int NOMBRE_EXPEDIENTE = 30;
    public static int DNI = 9;

    private Integer logitudMaxima;
    private int code;

    private String[] msgExceptions = {"El campo no puede superar la lungitutd de : ",
        "El formato del DNI es incorrecto",
        "El tipo de usuario introducino no existe"};

    public ErroresValidacionCampos(Integer code) {
        this.code = code;
        this.logitudMaxima = null;
    }

    public ErroresValidacionCampos(int logitudMaxima, Integer code) {
        this.logitudMaxima = logitudMaxima;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.msgExceptions[this.code] + (this.logitudMaxima != null ? this.logitudMaxima : "");
    }

}
