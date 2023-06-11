package Agenda.View;

import Agenda.Controller.*;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

class OwnFrame extends JFrame {
    private static  Toolkit defaultToolKit = Toolkit.getDefaultToolkit();
    protected static  int screenWidth;

    protected static  int screenHeight;

    public OwnFrame() {
        setVisible(true);
        screenWidth = defaultToolKit.getScreenSize().width;
        screenHeight = defaultToolKit.getScreenSize().height;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
public class MainFrame extends OwnFrame implements ActionListener, ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String textInfo = new String();
        Contacto selectedContacto = null;
        try {
            selectedContacto = (Contacto) listContact.getSelectedValue();
        } catch(Exception exception) {
            textInfo = "\n" + "No hay informacion disponible";
        }

        if(selectedContacto != null) {
            textInfo = "Nombre:"+ selectedContacto.getNombre() + "\n" +
                    "Numero de telefono: " + selectedContacto.getTelefono() + "\n" +
                    "Correo electronico: " + selectedContacto.getEmail();
        }

        lblContactInfo.setText(textInfo.toString());

    }

    class WListener extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            mostrarTodo();
        }

        @Override
        public void windowClosing(WindowEvent e) {

        }
    }

    private JPanel panelMain;
    private JPanel pnlInnerNorth;
    private JPanel pnlInnerCenter;
    private JTextField tfSearch;
    private JComboBox comboBox1;
    private JButton btnSearch;
    private JList  listContact;
    private JButton btnNewContact;
    private JButton btnRemoveContact;
    private JLabel lblContactInfo;

    private WListener wl;


    public static void main(String[] args) {
        MainFrame main = new MainFrame();
    }
    public MainFrame(){
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(screenWidth / 4, screenHeight / 4);
        setSize(screenWidth / 2, screenHeight / 2);
        add(panelMain);
        mostrarTodo();
        btnSearch.addActionListener(this);
        btnNewContact.addActionListener(this);
        wl = new WListener();
        addWindowListener(wl);
        listContact.addListSelectionListener(this);
        btnRemoveContact.addActionListener(this);

    }

    private int confirmAction( String text) {
        int escogido = JOptionPane.showConfirmDialog(null, text, "Confirmar",
                JOptionPane.YES_NO_OPTION);
        return escogido;
    }
    private  void mostrarTodo() {
        if(!setCustommModel(Agenda.obtenerContactos(), "No hay contactos en la agenda")) {
            if(confirmAction("No hay contactos en la agenda. ¿Desea crear uno nuevo") == JOptionPane.OK_OPTION) {
                actionNewContact();
            }
        }
    }

    private  boolean setCustommModel(List<Contacto> lista, String error) {
        DefaultListModel defaultModel = new DefaultListModel();
        CustomListModel customModel = null;
        boolean exito  = true;
        try {
            customModel = new CustomListModel(lista);
        }  catch (Exception e) {
            defaultModel.addElement(error);
            exito = false;
        }


        if(exito) {
            listContact.setModel(customModel);
        } else {
            listContact.setModel(defaultModel);
        }
        return exito;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSearch) {
            searchAction();
        } else if(e.getSource() == btnNewContact) {
            actionNewContact();
        } else if(e.getSource() == btnNewContact) {
            actionNewContact();
        } else if(e.getSource() == btnRemoveContact) {
            actionRemoveContact();
            mostrarTodo();
        }

    }

    private void actionRemoveContact() {
        Contacto selectedContacto =(Contacto) listContact.getSelectedValue();
        try {
            Agenda.eliminarContacto(selectedContacto.getId());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error"
            , JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actionNewContact() {
        newContactFrame frame = new newContactFrame(wl);
    }


    public void searchAction() {
        String busqueda = tfSearch.getText();
        String valueFilter = comboBox1.getSelectedItem().toString();
        System.out.println(busqueda);

        if(busqueda.equals("") || busqueda == null) {
            JOptionPane.showMessageDialog(null, "Debes introducir un texto en el cuadro de " +
                    "busqueda", "Campos vacios", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (valueFilter) {
            case "Sin filtro":
                setCustommModel(Agenda.busquedaGlobal(busqueda), "No hay contactos que coincidan con" +
                        " " + busqueda);
                break;
            case "Nombre":
                setCustommModel(Agenda.buscarPorNombre(busqueda), "No hay contactso cuyo nombre coincide" +
                        "con  coincidan con "
                 + busqueda);
                break;
            case "Correo electronico":
                setCustommModel(Agenda.buscarPorEmail(busqueda), "No hay contactos cuyo email coincida con"
                 + busqueda);
                break;

            case "Numero de telefono":
                setCustommModel(Agenda.buscarPorTelefono(busqueda), "No hay contactos cuyo telefono coincida con" +
                        " " + busqueda);
                break;
        }
    }
}

class CustomListModel extends AbstractListModel  {


    private List<Contacto> lista = new ArrayList();

    public CustomListModel(List<Contacto> lista) throws Exception{
        if(lista.isEmpty()) {
            throw new Exception("No hay contactos");
        } else {
            this.lista = lista;
        }
    }

    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public Object getElementAt(int index) {
        Contacto c = lista.get(index);
        return c;
    }

}