package Agenda.View;
import javax.swing.*;
import java.awt.*;

public class View {
    public static void main(String[] args) {
        OwnFrame mainFrame  = new OwnFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle(mainFrame.getTitle() + " - Ventana principal");
    }
}

class OwnFrame extends JFrame {
    public OwnFrame() {
        Toolkit screen = Toolkit.getDefaultToolkit();
        Dimension screenSize = screen.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setVisible(true);
        setTitle("Agenda de contactos");
        setSize(screenWidth / 2, screenHeight/2);
        setLocation(screenWidth / 4, screenHeight / 4);

    }

}

