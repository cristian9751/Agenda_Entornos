package Agenda.Controller;

import Agenda.Model.Conexion;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private static Conexion con = new Conexion();
    public static List<Contacto> obtenerContactos() {
        con.conectarMySQL();
        List<Contacto> res =  Conexion.leerContactosBD();
        con.desconectarMySQL();
        return res;
    }


    public static List<Contacto> buscarPorNombre(String nombre)  {
        con.conectarMySQL();
        nombre = nombre.toUpperCase();
        List<Contacto> res =  Conexion.buscarPorNombre(nombre);
        con.desconectarMySQL();
        return res;
    }

    public static List<Contacto> buscarPorEmail(String email) {
        con.conectarMySQL();
        email = email.toUpperCase();
        List<Contacto> res = Conexion.buscarPorEmail(email);
        con.desconectarMySQL();
        return res;
    }

    public static List<Contacto> buscarPorTelefono(String telefono) {
        con.conectarMySQL();
        telefono = telefono.toUpperCase();
        List<Contacto> res =  Conexion.buscarPorTelefono(telefono);
        con.desconectarMySQL();
        return res;
    }

    public static void agregarContacto(String nombre, String telefono, String email) throws Exception {
        con.conectarMySQL();
        Contacto nuevoContacto = new Contacto(nombre, telefono, email);
        if(Conexion.obtenerId(nuevoContacto) == -1) {
            Conexion.insertarBD(nuevoContacto);
            nuevoContacto.setId();
        } else {
            con.desconectarMySQL();
            throw new Exception("El contacto ya existe");
        }

        con.desconectarMySQL();
    }

    public static List<Contacto> busquedaGlobal(String busqueda) {
        List<Contacto> resultado = new ArrayList<>();
        resultado.addAll(buscarPorNombre(busqueda));
        resultado.addAll(buscarPorEmail(busqueda));
        resultado.addAll(buscarPorTelefono(busqueda));
        for(int i = 0; i<resultado.size() -1 ; i++) {
            if(resultado.get(i).getId() == resultado.get(i + 1).getId()) {
                resultado.remove(i);
            }
        }
        return resultado;
    }

    public static boolean eliminarContacto(int id) {
        con.conectarMySQL();
         boolean res = Conexion.eliminarBD(id);
         con.desconectarMySQL();
         return res;
    }


    public static boolean comprobarExiste(int id) {
        con.conectarMySQL();
        boolean res = Conexion.comprobarExiste(id);
        con.desconectarMySQL();
        return res;
    }

}
