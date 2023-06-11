package Agenda.Controller;

import Agenda.Model.Conexion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contacto{
    private String nombre;
    private String telefono;
    private String email;

    private int id;

    public Contacto(String nombre, String telefono, String email) {
        this.nombre = nombre.toUpperCase();
        this.telefono = telefono.toUpperCase();
        this.email = email.toUpperCase();
    }


    public Contacto(String nombre, String telefono, String email, int id) {
        this(nombre, telefono, email);
        this.id = id;
    }


    public void setId() {
        this.id = Conexion.obtenerId(this);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
