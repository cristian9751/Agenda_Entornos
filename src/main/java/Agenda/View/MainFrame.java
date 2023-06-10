package Agenda.View;

import Agenda.Controller.Agenda;
import Agenda.Controller.Contacto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;




class OwnFrame extends JFrame {
    public static final Toolkit tools = Toolkit.getDefaultToolkit();
    public static final int screenWidth = tools.getScreenSize().width;
    public static final int screenHeight = tools.getScreenSize().height;

    public OwnFrame() {
        setVisible(true);
        setTitle("Agenda de contactos");
    }


}

public class MainFrame extends OwnFrame implements ActionListener{
    private static boolean isEmpty = false;
    public static void main(String[] args) {
        MainFrame main = new MainFrame();
    }
    public MainFrame() {
        super();
        setContentPane(mainPanel);
        setSize((screenWidth / 2), (screenHeight / 2));
        setLocation((screenWidth / 4), screenHeight / 4);
        searchButton.addActionListener(this);
        listarDefault();
        contactList.addListSelectionListener(lListener);
        deleteContactButton.addActionListener(this);
        newContactButton.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private ListListener lListener = new ListListener();
    private  JPanel mainPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JComboBox filterComboBox;
    private JTextPane infoContactTextPane;
    private JList contactList;
    private JScrollBar scrollBar1;

    private JButton deleteContactButton = new JButton();

    private JButton newContactButton;


    @Override
    public void actionPerformed(ActionEvent e) {
            if(isEmpty == false) {
            if (e.getSource() == searchButton) {
                actionSearch(e);
            }
            if(e.getSource() == deleteContactButton) {
                actionDelete(e);
            }
            listarDefault();
        }

        if(e.getSource() == newContactButton) {
            newContact(e);
        }
    }


    public void newContact(ActionEvent e) {
        //Metodo que controla las acciones del boton crear nuevo contacto
    }
    public void actionDelete(ActionEvent e) {
        Contacto contacto = (Contacto) contactList.getSelectedValue();
        Agenda.eliminarContacto(contacto.getId());
    }

    public void  listarDefault() {
        mostrarLista(Agenda.obtenerContactos(), "No hay contactos en la agenda");
    }
    public  DefaultListModel mostrarLista(List<Contacto> lista, String error) {
        DefaultListModel modelo = new DefaultListModel();
        for(Contacto contacto  : lista) {
            modelo.addElement(contacto);
        }

        if(lista.isEmpty()) {
            isEmpty = true;
            JLabel errorLabel = new JLabel(error);
            errorLabel.setVisible(true);
            errorLabel.setSize(300, 100);
            errorLabel.setLocation(0, -30);
            contactList.add(errorLabel);
            return  null;
        }


        contactList.setModel(modelo);
        return modelo;

    }


    public void actionSearch(ActionEvent e) {
        String filterValue = filterComboBox.getSelectedItem().toString();
        String busqueda = searchTextField.getText();
        if(busqueda.equals("")) {
           JOptionPane.showMessageDialog(null, "Debes rellenar el campo de busqueda",
                   "Campos invalidos", JOptionPane.ERROR_MESSAGE);
           return;
        }

        switch (filterValue) {
            case "":
                mostrarLista(Agenda.busquedaGlobal(busqueda), "No  se han encontrado resultados al buscar "
                + busqueda);
                break;
            case "Nombre":
                mostrarLista(Agenda.buscarPorNombre(busqueda), "No hay contactos cuyo nombre coinicda con "
                + busqueda);
                break;
            case "Correo electronico":
                mostrarLista(Agenda.buscarPorEmail(busqueda), "No hay contactos cuy email coincide con "
                + busqueda);
                break;
            case "Numero de telefono":
                mostrarLista(Agenda.buscarPorTelefono(busqueda), "No hay contactos cuyo numero de telefono" +
                        " coincide con " + busqueda);
                break;
        }
    }
    class ListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Contacto selectedContacto = (Contacto) contactList.getSelectedValue();
            if(selectedContacto == null) {
                infoContactTextPane.setText("No hay informacion disponible");
                return;
            }
            infoContactTextPane.setText(
                    "Nombre del contacto: " + selectedContacto + "\n"
                    + "Telefono del contacto: " + selectedContacto.getTelefono() + "\n"
                    + "Correo electronico del contacto: " + selectedContacto.getEmail()
            );

            deleteContactButton.setText("Eliminar contacto");
            deleteContactButton.setVisible(true);
            deleteContactButton.setSize(searchButton.getSize().width * 2, searchButton.getSize().height);
            deleteContactButton.setLocation(0, mainPanel.getSize().height / 8);

            infoContactTextPane.add(deleteContactButton);
        }
    }
}