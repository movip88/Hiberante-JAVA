package modelo;
// Generated 29-ene-2019 19:38:18 by Hibernate Tools 4.3.1

import exception.ErroresValidacionCampos;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Expedientes generated by hbm2java
 */
public class Expedientes implements java.io.Serializable {

    private Integer id;
    private Usuarios usuarios;
    private String nombre;
    private String apellidos;
    private String dni;
    private String cp;
    private Date fechaAlta;
    private String telefono;
    private Integer NMascotas;

    public Expedientes() {
    }

    public Expedientes(Usuarios usuarios, String nombre, String apellidos, String dni, String cp, Date fechaAlta, String telefono, Integer NMascotas) {
        this.usuarios = usuarios;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.cp = cp;
        this.fechaAlta = fechaAlta;
        this.telefono = telefono;
        this.NMascotas = NMascotas;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuarios getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombre() {
        return this.nombre;
    }

    /**
     * Esta metodo puede generar una excepcion ya que el string introducido
     * puede tener una largada maxima de 30
     *
     * @param - String
     * @throws ErroresValidacionCampos
     */
    public void setNombre(String nombre) throws ErroresValidacionCampos {
        if (nombre.length() > ErroresValidacionCampos.NOMBRE_APELLIDOS_EXPEDIENTE) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.NOMBRE_APELLIDOS_EXPEDIENTE, ErroresValidacionCampos.ERROR_LENTH);
        }
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * Esta metodo puede generar una excepcion ya que el string introducido
     * puede tener una largada maxima de 30
     *
     * @param - String
     * @throws ErroresValidacionCampos
     */
    public void setApellidos(String apellidos) throws ErroresValidacionCampos {
        if (nombre.length() > ErroresValidacionCampos.NOMBRE_APELLIDOS_EXPEDIENTE) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.NOMBRE_APELLIDOS_EXPEDIENTE, ErroresValidacionCampos.ERROR_LENTH);
        }
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.dni;
    }

    /**
     * Esta metodo puede generar una excepcion ya que comprueva que le formato
     * del DNI se un formato correcto
     *
     * @param - String
     * @throws ErroresValidacionCampos
     */
    public void setDni(String dni) throws ErroresValidacionCampos {
        if (dni.length() > ErroresValidacionCampos.DNI) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.DNI, ErroresValidacionCampos.ERROR_LENTH);
        } else if (!Pattern.matches("\\d{8}[A-HJ-NP-TV-Z]", dni)) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.ERROR_DNI_FORMAT);
        }
        this.dni = dni;
    }

    public String getCp() {
        return this.cp;
    }

    /**
     * Esta metodo puede generar una excepcion ya que el string introducido
     * puede tener una largada maxima de 6
     *
     * @param - String
     * @throws ErroresValidacionCampos
     */
    public void setCp(String cp) throws ErroresValidacionCampos {
        if (cp.length() > ErroresValidacionCampos.MATRICULA_CP) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.MATRICULA_CP, ErroresValidacionCampos.ERROR_LENTH);
        }
        this.cp = cp;
    }

    public Date getFechaAlta() {
        return this.fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTelefono() {
        return this.telefono;
    }

    /**
     * Esta metodo puede generar una excepcion ya que el string introducido
     * puede tener una largada maxima de 12
     *
     * @param - String
     * @throws ErroresValidacionCampos
     */
    public void setTelefono(String telefono) throws ErroresValidacionCampos {
        if (nombre.length() > ErroresValidacionCampos.TELEFONO) {
            throw new ErroresValidacionCampos(ErroresValidacionCampos.TELEFONO, ErroresValidacionCampos.ERROR_LENTH);
        }
        this.telefono = telefono;
    }

    public Integer getNMascotas() {
        return this.NMascotas;
    }

    public void setNMascotas(Integer NMascotas) {
        this.NMascotas = NMascotas;
    }

    @Override
    public String toString() {
        return "Id expediente: " + id
                + "\nNombre: " + nombre
                + "\nApellidos: " + apellidos
                + "\nDNI: " + dni
                + "\nCp: " + cp
                + "\nFecha de alta: " + fechaAlta
                + "\nTelefono: " + telefono
                + "\nNumero de mascotas: " + NMascotas
                + "\nMatricula usuario: " + usuarios.getMatricula();
    }
}
