package Agenda.Model;

// **************************
// ***** CLASE Conexion.java (fase 1)
// **************************

import Agenda.Controller.Contacto;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Conexion {
    public String driver = "org.mariadb.jdbc.Driver";
    public String database = "AGENDA";
    public String hostname = "localhost";
    public String port = "3306";
    public String url = "jdbc:mariadb://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    public String username = "usr_agenda";
    public String password = "pwd1234";
    public static Connection conn = null;

    public void conectarMySQL() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("[!] Estás conectado a la BD");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("[!] No se ha podido conectar con la BD. Detalles: ");
            e.printStackTrace();
        }
    }

    public void desconectarMySQL() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("[!] Estás desconectado de la BD");
            }
        } catch (SQLException e) {
            System.out.println("[!] No se ha podido desconectar con la BD. Detalles: ");
            e.printStackTrace();
        }
    }

    public static List<Contacto> leerContactosBD(){
        String consulta = "SELECT fullname, telephone, email, contactid FROM CONTACTS";
        ResultSet res = Consulta(consulta);
        return obtenerLista(res);
    }

    public static List<Contacto> obtenerLista(ResultSet result) {
        List<Contacto> lista = new ArrayList<>();
        try {
            while(result.next()) {
                Contacto contactoActual = new Contacto(result.getString(1), result.getString(2), result.getString(3), result.getInt(4));
                lista.add(contactoActual);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public static int obtenerId(Contacto contacto)  {
        int id = -1;
        String query = "SELECT contactid FROM Contacts WHERE telephone = '" + contacto.getTelefono() +
                "' AND fullname = '" + contacto.getNombre() + "' AND email = '" + contacto.getEmail() + "'";
        ResultSet res = Consulta(query);
        try {
          if(res.next()) {
              id = res.getInt(1);
          }
        } catch (Exception e) {;
            e.printStackTrace();
        }

        return id;


    }

    public static boolean comprobarExiste(int id) {
        String query = "SELECT fullname FROM Contacts WHERE contactid = " + id;
        boolean exists = false;
        try {
            ResultSet res = Consulta(query);
            if(res.next()) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static void insertarBD(Contacto contacto) throws Exception {
      try  {
          String query = " insert into contacts (fullname, telephone, email)"
                  + " values (?, ?, ?)";
          PreparedStatement preparedStatement = conn.prepareStatement(query);
          preparedStatement.setString(1, contacto.getNombre());
          preparedStatement.setString(2, contacto.getTelefono());
          preparedStatement.setString(3, contacto.getEmail());
          preparedStatement.execute();
      } catch (Exception e ) {
          throw new Exception("No se ha podido agregar el contacto a la agenda ");
      }

    }



    public static boolean eliminarBD(int id)  {
        String query = "DELETE FROM Contacts WHERE contactid = " + id;
        if(Consulta(query) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static List<Contacto> buscarPorNombre(String nombre) {
        String query = "SELECT fullname, telephone, email, contactid FROM contacts  WHERE fullname LIKE '%" + nombre +  "%'";
        ResultSet res = Consulta(query);
        return obtenerLista(res);
    }

    public static List<Contacto> buscarPorEmail(String email) {
        String query = "SELECT fullname, telephone, email, contactid FROM contacts WHERE email LIKE '%" + email + "%'";
        ResultSet res = Consulta(query);
        return obtenerLista(res);
    }

    public static List<Contacto> buscarPorTelefono(String telefono)  {
        String query = "SELECT fullname, telephone, email, contactid FROM contacts WHERE telephone LIKE '%" + telefono + "%'";
        ResultSet res = Consulta(query);
       return obtenerLista(res);
    }

    private  static ResultSet Consulta(String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }catch (Exception e) {
            return null;
        }
    }


}