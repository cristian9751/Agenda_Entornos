package Agenda;

import Agenda.Controller.Contacto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View {
    private JTextField searchField;
    private JLabel agendaDeContactosLabel;
    private JLabel filtrarPorLabel;
    private JRadioButton filterName;
    private JRadioButton filterTelephone;
    private JRadioButton filterEmail;
    private JPanel Jpanel2;
    private JLabel informaciónDelContactoLabel;
    private JButton searchButton;
    private JPanel JPanel1;
    private JPanel JPanel3;
    private JTextArea infoTextArea;
    private JTextArea contactListTextArea;

    public View() {

    searchButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            String busqueda = searchButton.getText();
            if(filterName.isSelected()) {
                Agenda.Controller.Agenda.buscarPorNombre(busqueda);
            } else if(filterEmail.isSelected()) {
                Agenda.Controller.Agenda.buscarPorEmail(busqueda);
            } else if(filterTelephone.isSelected()) {
                Agenda.Controller.Agenda.buscarPorTelefono(busqueda);
            } else {
                Agenda.Controller.Agenda.busquedaGlobal(busqueda);
            }
        }
    });
}
}
