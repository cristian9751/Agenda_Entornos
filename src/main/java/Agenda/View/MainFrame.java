package Agenda.View;

import Agenda.Controller.Agenda;
import Agenda.Controller.Contacto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


abstract class ownFrame extends JFrame {
    public static final Toolkit tools = Toolkit.getDefaultToolkit();
    public static final int screenWidth = tools.getScreenSize().width;
    public static final int screenHeight = tools.getScreenSize().height;

    public ownFrame() {
        setVisible(true);
    }


}

public class MainFrame extends ownFrame implements ActionListener{

    public static void main(String[] args) {
        MainFrame main = new MainFrame();
    }
    public MainFrame() {
        super();
        setContentPane(mainPanel);
        setSize((screenWidth / 2), (screenHeight / 2));
        setLocation((screenWidth / 4), screenHeight / 4);
        searchButton.addActionListener(this);
        mostrarLista(Agenda.obtenerContactos(), "No hay contactos en la agenda");
    }

    private  JPanel mainPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JComboBox filterComboBox;
    private JTextPane infoContactTextPane;
    private JList contactList;
    private JScrollBar scrollBar1;


    public DefaultListModel mostrarLista(List<Contacto> lista, String error) {
        DefaultListModel modelo = new DefaultListModel();
        if(lista.isEmpty()) {
            modelo.addElement(error);
        }
        for(Contacto contacto  : lista) {
            modelo.addElement("Nombre: " + contacto.getNombre());
            modelo.addElement("Email : " + contacto.getEmail());
            System.out.println("A");
        }

        contactList.setModel(modelo);
        return modelo;

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
