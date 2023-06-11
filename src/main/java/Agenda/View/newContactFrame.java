package Agenda.View;

import Agenda.Controller.Agenda;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Agenda.View.MainFrame.WListener;

public class newContactFrame extends OwnFrame implements ActionListener {
    public newContactFrame(WListener wl) {
        super();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(screenWidth / 4, screenHeight / 4);
        setLocation(screenWidth / 2 , screenHeight / 2);
        add(panel1);
        crearButton.addActionListener(this);
        addWindowListener(wl);
    }
    private JPanel panel1;
    private JTextField tfNombre;
    private JTextField tfTelefono;
    private JTextField tfEmail;
    private JButton crearButton;
    private JButton cancelarButton;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == crearButton) {
            actionCrear();
        } else if(e.getSource() == cancelarButton) {
            actionCancelar();
        }
    }

    public void actionCrear() {
        String nombre = tfNombre.getText();
        String email = tfEmail.getText();
        String telefono = tfEmail.getText();
        try {
            Agenda.agregarContacto(nombre, telefono, email);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        dispose();

    }
    public void actionCancelar() {

    }
}
