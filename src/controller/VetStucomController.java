package controller;

import exception.ErroresLogica;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import modelo.Expedientes;
import modelo.Usuarios;
import persistence.VetstucomDAO;

/**
 * Clase enecarda de gestionar la logica de la aplicacion
 *
 * @author polmonleonvives
 */
public class VetStucomController {

    private static VetStucomController instance = null;
    private Usuarios userLogged;
    private VetstucomDAO vetstucomDAO;

    /**
     * Metodo que gestiona la unica instancia de la calse cuando se llama por
     * primera vez se crea la instgancia y si ya existe un la devuelve
     *
     * @return
     */
    public static VetStucomController getInstance() {
        if (instance == null) {
            instance = new VetStucomController();
        }
        return instance;
    }

    private VetStucomController() {
        this.userLogged = null;
        this.vetstucomDAO = new VetstucomDAO();
    }

    /**
     * Recive dos strings uno con la matricula del usuario y el otro con su pass
     * en caso de que no lo encuentre lanza ena excepcion y si lo encuantra se
     * lo guarda en la variable de la calsse como usuario loggeado
     *
     * @param String
     * @param String
     * @throws ErroresLogica
     */
    public void loggUser(String matricula, String pass) throws ErroresLogica {
        this.userLogged = vetstucomDAO.autentificateUser(new Usuarios(matricula, pass));
        if (this.userLogged == null) {
            throw new ErroresLogica(ErroresLogica.LOGGIN_ERROR);
        }
        this.userLogged.setUltimoAcceso(new Date());
        this.vetstucomDAO.updateUser(this.userLogged);
    }

    public Usuarios getUserLogged() {
        return userLogged;
    }

    /**
     * Si no hay ningun usuario loggeado lanza una excepcion si hay uno deja la
     * variable en un null
     *
     * @throws ErroresLogica
     */
    public void logoutUser() throws ErroresLogica {
        if (this.userLogged == null) {
            throw new ErroresLogica(ErroresLogica.OPTION_ERROR);
        }
        this.userLogged = null;
    }

    /**
     * Deveulve un String con la informacion de todos los expedientes
     *
     * @return - String
     * @throws ErroresLogica
     */
    public String getExpedientes() throws ErroresLogica {
        checkPermision(1);
        List<Expedientes> allExpedients = vetstucomDAO.getAllExpedients();
        if (allExpedients.isEmpty()) {
            return "No hay exepdientes aún...";
        }
        StringBuilder sb = new StringBuilder("\tListado de expedientes\n----------------------------------------------------");
        sb.append(allExpedients.stream().map(Expedientes::toString).collect(Collectors.joining("\n-------------------\n", "\n", "")));
        return sb.toString();
    }

    /**
     * Deveulve un String con la informacion de todos los Usuario
     *
     * @return - String
     * @throws ErroresLogica
     */
    public String getUsers() throws ErroresLogica {
        checkPermision(3);
        List<Usuarios> allUsers = vetstucomDAO.getAllUsuarios();
        StringBuilder sb = new StringBuilder("\tListado de expedientes\n----------------------------------------------------");
        sb.append(allUsers.stream().map(Usuarios::toString).collect(Collectors.joining("\n-------------------\n", "\n", "")));
        return sb.toString();
    }

    /**
     * recive un expediente le seta el creador como el usuario que esta loggeado
     * y le dice al dao que lo guarade
     *
     * @param - Expedientes
     * @throws ErroresLogica
     */
    public void saveExpediente(Expedientes expediente) throws ErroresLogica {
        checkPermision(2);
        expediente.setUsuarios(userLogged);
        vetstucomDAO.saveExpediente(expediente);
    }

    /**
     * Recive un expediente y le dice al dao que lo actualice
     *
     * @param - Expedientes
     * @throws ErroresLogica
     */
    public void updateExpediente(Expedientes expedientEditado) throws ErroresLogica {
        checkPermision(2);
        vetstucomDAO.updateExpediente(expedientEditado);
    }

    /**
     * Recive un Uusraio y le dice al dao que lo actualice
     *
     * @param - Usuarios
     * @throws ErroresLogica
     */
    public void updateUser(Usuarios usuario) throws ErroresLogica {
        checkPermision(3);
        if(usuario.equals(this.userLogged))
            this.userLogged = usuario;
        vetstucomDAO.updateUser(usuario);
    }

    /**
     * Recive un Expediente como parametro y le dice al DAO que lo elimine
     *
     * @param - Expedientes
     */
    public void deleteExpediente(Expedientes expediente) {
        vetstucomDAO.deleteExpediente(expediente);
    }

    /**
     * Recive un Usuario como parametro y le dice al DAO que lo elimine
     *
     * @param - Usuarios
     * @throws ErroresLogica
     */
    public void deleteUser(Usuarios usuarios) throws ErroresLogica {
        if (usuarios.equals(userLogged)) {
            throw new ErroresLogica(ErroresLogica.NO_DELETE_USER);
        }
        vetstucomDAO.deleteUser(usuarios);
    }

    /**
     * Recive un Usuario como parametro y le dice al DAO que lo actulice
     *
     * @param - Usuarios
     * @throws ErroresLogica
     */
    public void saveUser(Usuarios user) throws ErroresLogica {
        checkPermision(3);
        vetstucomDAO.saveUser(user);
    }

    /**
     * Recive un Integer con el id de un usuario y en caso de encontrar el
     * expediente genera una excepcion
     *
     * @param - Integer
     * @return - Expedientes
     */
    public Expedientes getExpediente(Integer idExpediente) throws ErroresLogica {
        Expedientes expediente = vetstucomDAO.getExpediente(idExpediente);
        if (expediente == null) {
            throw new ErroresLogica(ErroresLogica.ID_EXPEDIENTE_ERROR);
        }
        return expediente;
    }

    /**
     * Recive un String con la matricula de un usuario y en caso de encontrar el
     * usuario o que sea el mimso usuario que esta loggeado genera una excepcion
     *
     * @param - String
     * @return - Usuarios
     */
    public Usuarios getUser(String matricula) throws ErroresLogica {
        Usuarios user = vetstucomDAO.getUser(matricula);
        if (user == null) {
            throw new ErroresLogica(ErroresLogica.ID_USUARIO_ERROR);
        }
        return user;
    }

    /**
     * recive un integer con el nivel minimo para realizar la accion si el tipo
     * de usuario es más pequeño lanza una excepcion ya que no tiene permitida
     * la opcion
     *
     * @param levelPermision
     * @throws ErroresLogica
     */
    private void checkPermision(int levelPermision) throws ErroresLogica {
        if (this.userLogged == null) {
            throw new ErroresLogica(ErroresLogica.NOT_LOGGED);
        }
        if (this.userLogged.getTipoUsuario() < levelPermision) {
            throw new ErroresLogica(ErroresLogica.NOT_PERMIS);
        }
    }

    /**
     * Devuleve todas las opciones segun el usuario que es loggeado
     *
     * @return
     */
    public String[] getOptionsUser() {
        switch (this.userLogged.getTipoUsuario()) {
            case 1:
                return new String[]{"Consultar expedientes"};
            case 2:
                return new String[]{"Consultar expedientes", "Alta expedientes", "Baja expedientes", "Editar expedientes"};
            case 3:
                return new String[]{"Consultar expedientes", "Alta expedientes", "Baja expedientes", "Editar expedientes", "Alta usuarios", "Baja usuarios", "Editar usuarios", "Consultar usuarios"};
            default:
                return new String[]{""};
        }
    }
}
