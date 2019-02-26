package persistence;

import exception.ErroresLogica;
import java.util.List;
import modelo.Expedientes;
import modelo.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author polmonleonvives
 */
public class VetstucomDAO {

    /**
     * Devuelve una lista con todos los expedientes que hay en la bbdd
     *
     * @return - List<Expedientes>
     */
    public List<Expedientes> getAllExpedients() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Expedientes> allExpedients = sesion.createQuery("from Expedientes").list();
        sesion.close();
        return allExpedients;
    }

    /**
     * Devuleve una lista con todos los usuarios que hay en la bbdd
     *
     * @return - List<Usuarios>
     */
    public List<Usuarios> getAllUsuarios() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Usuarios> allExpedients = sesion.createQuery("from Usuarios").list();
        sesion.close();
        return allExpedients;
    }

    /**
     * Recive un usuario por parametro si este existe en la bbdd con la misma
     * matricula lanza una excepcion ya que no pueden existir dos usuarios con
     * la misma matricula
     *
     * @param - Usuarios
     * @throws - ErroresLogica
     */
    public void saveUser(Usuarios user) throws ErroresLogica {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        if (exitsUserWhithSameMatricula(user)) {
            sesion.close();
            throw new ErroresLogica(ErroresLogica.USER_EXITS);
        }
        sesion.save(user);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un usuario por parametro y lo actualuza en la bbdd
     *
     * @param - Usuarios
     */
    public void updateUser(Usuarios user) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update(user);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un usuario por parametro y lo borra de la bbdd
     *
     * @param - Usuarios
     */
    public void deleteUser(Usuarios user) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(user);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un expediente por parametro y lo guarada en la bbdd
     *
     * @param - Expedientes
     */
    public void saveExpediente(Expedientes expediente) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.save(expediente);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un expediente por parametro y lo actualiza en la bbdd
     *
     * @param - Expedientes
     */
    public void updateExpediente(Expedientes expediente) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.update(expediente);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un expediente por parametro y lo borra en la bbdd
     *
     * @param - Expedientes
     */
    public void deleteExpediente(Expedientes expediente) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        sesion.delete(expediente);
        tx.commit();
        sesion.close();
    }

    /**
     * Recive un usuario que solo contiene la pass y la matricula y busca uno
     * que contega los mismos valores si no lo encuentra devuelve null
     *
     * @param - Usuarios
     * @return - Usuarios
     */
    public Usuarios autentificateUser(Usuarios user) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuarios userReturn = (Usuarios) sesion.createQuery("from Usuarios user where user.matricula = '" + user.getMatricula() + "' and user.pass = '" + user.getPass() + "'").uniqueResult();
        sesion.close();
        return userReturn;
    }

    /**
     * Recive un String con la matricula de un usuario y busca un usuario con
     * dicha matricula si no lo encuentra devuelve null
     *
     * @param - String
     * @return - Usuarios
     */
    public Usuarios getUser(String matricula) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuarios userReturn = (Usuarios) sesion.createQuery("from Usuarios user where user.matricula = '" + matricula + "'").uniqueResult();
        sesion.close();
        return userReturn;
    }

    /**
     * Recive un Integer con el id de un usuario y busca un expediente con dicho
     * id si no lo encuentra devuelve null
     *
     * @param - Integer
     * @return - Expedientes
     */
    public Expedientes getExpediente(Integer idExpediente) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Expedientes expedienteReturn = (Expedientes) sesion.createQuery("from Expedientes expediente where expediente.id = " + idExpediente + "").uniqueResult();
        sesion.close();
        return expedienteReturn;
    }

    /**
     * Recive un usuario y comprueve si hay en la bbd un usuario con la misma
     * matricula y devuelve un booleano dependiendo de si existe o no
     *
     * @param - Usuarios
     * @return - boolean
     */
    private boolean exitsUserWhithSameMatricula(Usuarios user) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuarios userReturn = (Usuarios) sesion.createQuery("from Usuarios user where user.matricula = '" + user.getMatricula() + "'").uniqueResult();
        sesion.close();
        return userReturn != null;
    }
}
