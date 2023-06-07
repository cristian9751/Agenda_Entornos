package Agenda.Controller;

import Agenda.Model.Conexion;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    public static List<Contacto> obtenerContactos() {
        return Conexion.leerContactosBD();
    }


    public static List<Contacto> buscarPorNombre(String nombre)  {
        nombre = nombre.toUpperCase();
        return Conexion.buscarPorNombre(nombre);
    }

    public static List<Contacto> buscarPorEmail(String email) {
        email = email.toUpperCase();
        return Conexion.buscarPorEmail(email);
    }

    public static List<Contacto> buscarPorTelefono(String telefono) {
        telefono = telefono.toUpperCase();
        return Conexion.buscarPorTelefono(telefono);
    }

    public static void agregarContacto(String nombre, String telefono, String email) throws Exception {
        Contacto nuevoContacto = new Contacto(nombre, telefono, email);
        if(Conexion.obtenerId(nuevoContacto) == -1) {
            Conexion.insertarBD(nuevoContacto);
            nuevoContacto.setId();
        } else {
            throw new Exception("El contacto ya existe");
        }
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
        return Conexion.eliminarBD(id);
    }


    public static boolean comprobarExiste(int id) {
        return Conexion.comprobarExiste(id);
    }

}
