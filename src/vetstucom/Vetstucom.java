package vetstucom;

import controller.VetStucomController;
import exception.ErroresValidacionCampos;
import exception.ErroresLogica;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Expedientes;
import modelo.Usuarios;
import persistence.HibernateUtil;

/**
 *
 * @author user88
 */
public class Vetstucom {

    static boolean continuar = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        while (continuar) {
            try {
                String op = menu();
                runOption(op);
            } catch (ErroresLogica | ErroresValidacionCampos ex) {
                System.out.println(ex.getMessage());
            }
        }

        HibernateUtil.close();
    }

    /**
     * Funcion que imprime el menu correspondiente al usuario que este loggeado
     * en ese momento
     *
     * @return - String
     */
    private static String menu() {
        StringBuilder sb = new StringBuilder();
        sb.append("===============").append(VetStucomController.getInstance().getUserLogged() != null ? VetStucomController.getInstance().getUserLogged().getNombre() : "NO ESTAS LOGEADO").append("===============\n");
        if (VetStucomController.getInstance().getUserLogged() == null) {
            sb.append("\t1-Iniciar sesión\n");
        } else {
            int con = 1;
            for (String option : VetStucomController.getInstance().getOptionsUser()) {
                sb.append("\t").append(con).append(" - ").append(option).append("\n");
                con++;
            }
            sb.append("\n\t").append("X - Cerrar sesión\n");
        }
        sb.append("\n\t").append("E - Salir\n");
        sb.append("\tIntroduce tu opcion:\n=======================================");
        System.out.println(sb.toString());
        return InputAsker.askString("");
    }

    /**
     * Funcion encargada de ejecvutar la operacion deseada por el usuario recive
     * un String con la opcion y puede generar excepciones
     *
     * @param op - String
     * @throws ErroresLogica
     * @throws ErroresValidacionCampos
     */
    private static void runOption(String op) throws ErroresLogica, ErroresValidacionCampos {
        try {
            int opInt = Integer.parseInt(op);
            switch (opInt) {
                case 1:
                    //Ya que la opcion 1 se comparte cunado no hay usuario loggeado y cuando hay uno hay que controlar en que caso ejecutar una codigo distinto
                    if (VetStucomController.getInstance().getUserLogged() == null) {
                        loggearte();
                    } else {
                        System.out.println(VetStucomController.getInstance().getExpedientes());
                    }
                    break;
                case 2:
                    altaExpediente();
                    break;
                case 3:
                    bajaExpediente();
                    break;
                case 4:
                    editarExpedientes();
                    break;
                case 5:
                    altaUsuarios();
                    break;
                case 6:
                    bajaUsuarios();
                    break;
                case 7:
                    editarUsuarios();
                    break;
                case 8:
                    System.out.println(VetStucomController.getInstance().getUsers());
                    break;
                default:
                    throw new ErroresLogica(ErroresLogica.OPTION_ERROR);
            }
        } catch (NumberFormatException ex) {
            switch (op.toUpperCase()) {
                case "X":
                    VetStucomController.getInstance().logoutUser();
                    System.out.println("Esperamos verte pronto :)");
                    break;
                case "E":
                    continuar = false;
                    System.out.println("Esperamos verte pronto :)");
                    break;
                default:
                    throw new ErroresLogica(ErroresLogica.OPTION_ERROR);
            }
        }
    }

    private static void loggearte() throws ErroresLogica {
        String matricula = InputAsker.askString("Introduce tu matricula");
        String pass = InputAsker.askString("Introduce tu contraseña");
        VetStucomController.getInstance().loggUser(matricula, pass);
    }

    private static void altaExpediente() throws ErroresValidacionCampos, ErroresLogica {
        Expedientes expediente = new Expedientes();
        expediente.setNombre(InputAsker.askString("Introduce el nombre: "));
        expediente.setApellidos(InputAsker.askString("Introduce los apellidos: "));
        expediente.setDni(InputAsker.askString("Introduce el DNI: ").toUpperCase());
        expediente.setCp(InputAsker.askString("Introduce el codigo postal: "));
        expediente.setTelefono(InputAsker.askString("Introduce el numero de telefono: "));
        expediente.setNMascotas(InputAsker.askInt("Introduce el nuemero de mascotas: "));
        expediente.setFechaAlta(new Date());
        VetStucomController.getInstance().saveExpediente(expediente);
        System.out.println("Expediente guardado correctamente!!");
    }

    private static void bajaExpediente() throws ErroresLogica {
        Expedientes expediente = VetStucomController.getInstance().getExpediente(InputAsker.askInt("Introduce el id del expediente"));
        if (InputAsker.askString("Estas seguro de que quieres borrar el expediente? \n SI - para borrar\n NO - para cancelar", "SI", "NO").toUpperCase().equals("SI")) {
            VetStucomController.getInstance().deleteExpediente(expediente);
            System.out.println("Expediente borrado correctamente!!");
        }
    }

    private static void editarExpedientes() throws ErroresLogica, ErroresValidacionCampos {
        Expedientes expedienteAntiguo = VetStucomController.getInstance().getExpediente(InputAsker.askInt("Introduce el id del expediente"));
        System.out.println(expedienteAntiguo + "\n Si no quieres modificar el campo no introduzcas ningun texto y aprieta enter");

        String nuevoNombre = InputAsker.askStringOptionalEmpty("Introduce el nombre: ");
        expedienteAntiguo.setNombre(nuevoNombre.isEmpty() ? expedienteAntiguo.getNombre() : nuevoNombre);
        String nuevoApellido = InputAsker.askStringOptionalEmpty("Introduce los apellidos: ");
        expedienteAntiguo.setApellidos(nuevoApellido.isEmpty() ? expedienteAntiguo.getApellidos() : nuevoApellido);
        String nuevoDNI = InputAsker.askStringOptionalEmpty("Introduce el DNI: ").toUpperCase();
        expedienteAntiguo.setDni(nuevoDNI.isEmpty() ? expedienteAntiguo.getDni() : nuevoDNI);
        String nuevoCp = InputAsker.askStringOptionalEmpty("Introduce el codigo postal: ");
        expedienteAntiguo.setCp(nuevoCp.isEmpty() ? expedienteAntiguo.getCp() : nuevoCp);
        String nuevoNumero = InputAsker.askStringOptionalEmpty("Introduce el numero de telefono: ");
        expedienteAntiguo.setTelefono(nuevoNumero.isEmpty() ? expedienteAntiguo.getTelefono() : nuevoNumero);
        Integer nuevoNumeroMascotas = InputAsker.askIntWhitNull("Introduce el nuemero de mascotas: ");
        expedienteAntiguo.setNMascotas(nuevoNumeroMascotas == null ? expedienteAntiguo.getNMascotas() : nuevoNumeroMascotas);

        VetStucomController.getInstance().updateExpediente(expedienteAntiguo);
        System.out.println("Expediente editado correctamente!!");
    }

    private static void altaUsuarios() throws ErroresValidacionCampos, ErroresLogica {
        Usuarios newUser = new Usuarios();
        newUser.setNombre(InputAsker.askString("Introduce el nombre: "));
        newUser.setApellidos(InputAsker.askString("Introduce los apellidos"));
        newUser.setDni(InputAsker.askString("Introduce el DNI").toUpperCase());
        newUser.setMatricula(InputAsker.askString("Introduce la matricula: "));
        newUser.setPass(InputAsker.askString("Introduce la contraseña: "));
        newUser.setTipoUsuario(InputAsker.askInt("1 - Auxiliar\n2 - Veterinario\n3 - Administrador\nIntroduce el tipo de usuario:"));
        VetStucomController.getInstance().saveUser(newUser);
        System.out.println("Usuario registrado correctamente!!");
    }

    private static void bajaUsuarios() throws ErroresLogica {
        Usuarios usuario = VetStucomController.getInstance().getUser(InputAsker.askString("Introduce la matricula del usuario"));
        if (InputAsker.askString("Estas seguro de que quieres borrar el usario? \n SI - para borrar\n NO - para cancelar", "SI", "NO").toUpperCase().equals("SI")) {
            VetStucomController.getInstance().deleteUser(usuario);
            System.out.println("Usuario borrado correctamente!!");
        }
    }

    private static void editarUsuarios() throws ErroresLogica, ErroresValidacionCampos {
        Usuarios usuarioAntiguo = VetStucomController.getInstance().getUser(InputAsker.askString("Introduce la matricula del usuario"));
        System.out.println(usuarioAntiguo + "\n Si no quieres modificar el campo no introduzcas ningun texto y aprieta enter");

        String nuevoNombre = InputAsker.askStringOptionalEmpty("Introduce el nombre: ");
        usuarioAntiguo.setNombre(nuevoNombre.isEmpty() ? usuarioAntiguo.getNombre() : nuevoNombre);
        String nuevoApellido = InputAsker.askStringOptionalEmpty("Introduce los apellidos: ");
        usuarioAntiguo.setApellidos(nuevoApellido.isEmpty() ? usuarioAntiguo.getApellidos() : nuevoApellido);
        String nuevoDNI = InputAsker.askStringOptionalEmpty("Introduce el DNI: ").toUpperCase();
        usuarioAntiguo.setDni(nuevoDNI.isEmpty() ? usuarioAntiguo.getDni() : nuevoDNI);
        String nuevaMatricula = InputAsker.askStringOptionalEmpty("Introduce la matricula: ");
        usuarioAntiguo.setMatricula(nuevaMatricula.isEmpty() ? usuarioAntiguo.getMatricula() : nuevaMatricula);
        String nuevaPass = InputAsker.askStringOptionalEmpty("Introduce la contraseña: ");
        usuarioAntiguo.setPass(nuevaPass.isEmpty() ? usuarioAntiguo.getPass() : nuevaPass);
        Integer nuevoTipo = InputAsker.askIntWhitNull("1 - Auxiliar\n2 - Veterinario\n3 - Administrador\nIntroduce el tipo de usuario:");
        usuarioAntiguo.setTipoUsuario(nuevoTipo == null ? usuarioAntiguo.getTipoUsuario() : nuevoTipo);

        VetStucomController.getInstance().updateUser(usuarioAntiguo);
        System.out.println("Usuario editado correctamente!!");
    }
}
