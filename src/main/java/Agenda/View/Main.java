package Agenda.View;

import Agenda.Controller.Agenda;
import Agenda.Controller.Contacto;
import Agenda.Model.*;
import org.checkerframework.checker.units.qual.A;

import java.util.*;


public class Main {

    public static boolean mostrarLista(String txt, List<Contacto> lista) {
        if(lista.isEmpty()) {
            System.out.println("No hay ningun contacto con el que realizar esta operacion");
            return false;
        }

        Iterator it_lista =  lista.iterator();
        System.out.println(txt + ": ");
        while(it_lista.hasNext()) {
            Contacto contactoActual = (Contacto) it_lista.next();
            System.out.println(contactoActual);
        }
        return true;
     }

     private static Scanner in = new Scanner(System.in);

    public static String pedirString(String txt) {
        boolean valido = true;
        String res = new String();
        do {
            System.out.println(txt + ": ");
            try {
                res = in.nextLine();
            } catch(InputMismatchException e) {
                System.out.println("Debes introducir una cadena de texto");
                valido = false;
            }
        } while(!valido);

        return res;
    }

    public static int pedirInt(String txt) {
        int res = 0;
        boolean valido;
        do {
            System.out.println(txt + ": ");
            valido = true;
            try {
                res = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un numero entero ");
                valido = false;
            }
            in.nextLine();
        } while(!valido);

        return res;

    }


    public static int escogerIdContacto(String busqueda) {
        List<Contacto> lista = Agenda.obtenerContactos();
        boolean valid = true;
        int escogido = -1;
        do {
            if(mostrarLista("Contactos", lista)) {
                 escogido = pedirInt("Selecciona la id del contacto");
                valid = Agenda.comprobarExiste(escogido);
                if(escogido == -1) {
                    break;
                }
                if(!valid) {
                    System.out.println("La id que has escogido no existe");
                }
            }
        } while(!valid);

        return escogido;
    }


    public static void main(String[] args) {
        Conexion con = new Conexion();
        con.conectarMySQL();
        int opcion = 0;
        String busqueda = new String();
        int idContacto = 0;
        while(opcion != 8) {
            System.out.println("MENU AGENDA");;
            System.out.println("Opciones:");
            System.out.println("1. Ver todos los contactos");
            System.out.println("2. Añadir nuevo contacto");
            System.out.println("3. Eliminar un contacto");
            System.out.println("4. Buscar contacto por nombre");
            System.out.println("5. Buscar contacto por email");
            System.out.println("6. Buscar contacto por telefono");
            System.out.println("7. Busqueda global");
            System.out.println("8. Salir");
            opcion = pedirInt("Selecciona una opcion");

            switch (opcion) {
                case 1:
                    //Ver contactos
                    mostrarLista("Contactos en la agenda", Agenda.obtenerContactos());
                    break;
                case 2:
                    //Añadir nuevo contacto
                    String nombre = pedirString("Introduce el nombre del contacto");
                    String telefono = pedirString("Introduce el telefono del contacto");
                    String email = pedirString("Introduce el email del contacto");
                    try {
                        Agenda.agregarContacto(nombre, telefono, email);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    //Eliminar contacto
                    idContacto = escogerIdContacto(busqueda);
                    if(idContacto != -1) {
                        if(Agenda.eliminarContacto(idContacto)) {
                            System.out.println("El contacto se ha eliminado correctamente");
                        } else {
                            System.out.println("El contacto que quieres eliminar no existe");
                        }
                    }

                    break;
                case 4:
                    //Buscar contacto por nombre
                    busqueda = pedirString("Introduce el nombre del contacto que quieres consultar");
                    mostrarLista("Contactos cuyo nombre coincide con " + busqueda,
                            Agenda.buscarPorNombre(busqueda));
                    break;
                case 5:
                    //Buscar por email
                    busqueda = pedirString("Introduce el email del contacto que quieres consultar");
                    mostrarLista("Contactos cuyo email coincide con " + busqueda,
                            Agenda.buscarPorEmail(busqueda));
                break;

                case 6:
                    //Buscar por telefono
                    busqueda = pedirString("Introduce el numero de telefono del contacto que quieres consultar");
                    mostrarLista("Contactos cuyo telefono coincide con " + busqueda,
                            Agenda.buscarPorTelefono(busqueda));
                    break;
                case 7:
                    //Busqueda globañ
                    busqueda = pedirString("Introduce lo que buscas");
                    mostrarLista("Resultado de la busqueda", Agenda.busquedaGlobal(busqueda));
                    break;
                case 8:
                    System.out.println("Seleccionaste salir de la aplicacion");
                    break;
                default:
                    System.out.println("Debes seleccionar una opcion del 1 al 8");
                    break;

            }
        }
        con.desconectarMySQL();
    }
}
