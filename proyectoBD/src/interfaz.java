import javax.swing.*;
import java.awt.*;

public class interfaz extends JFrame {

    public interfaz(){
        setTitle("BD Videojuego");
        setVisible(true);
        setLayout(new GridLayout(1,1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Consulta", null, new Consulta(), "Consutar la base de datos");
        tabbedPane.addTab("Agregar", null, new Agregar(), "Agregar algo a la base de datos");
        tabbedPane.addTab("Borrar",null, new Borrar(), "Borrar algo de a base de datos");
        tabbedPane.addTab("Actualizar",null, new Actualizar(), "actualizar datos de la base");

        add(tabbedPane);
        pack();
    }
}
