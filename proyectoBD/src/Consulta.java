import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Consulta extends JPanel {
    JComboBox comboVistas, comboBox;
    JButton consultaVista, consultaTabla;

    public Consulta(){
        GridLayout layout = new GridLayout(5,4);
        layout.setHgap(5);
        layout.setVgap(5);
        setLayout(layout);
        setVisible(true);
        // parte de las consutas
        JLabel vistas = new JLabel("Vista a Consultar: ");
        add(vistas);
        comboVistas = new JComboBox();
        comboVistas.addItem("");
        comboVistas.addItem("Poblacion de cada continente");
        comboVistas.addItem("Poblacion de cada mundo");
        comboVistas.addItem("Miembros y alianzas");
        add(comboVistas);
        consultaVista = new JButton("Consultar Vista");
        consultaVista.addActionListener(new Accion(comboVistas));
        add(consultaVista);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        // parte de las tablas
        JLabel label = new JLabel("Tabla a consultar: ");
        add(label);
        comboBox = new JComboBox();
        comboBox.addItem("");
        Borrar borrar = new Borrar();
        borrar.setVisible(false);
        ArrayList<String> tablas = borrar.getTablesNames();
        for (String t: tablas)
            comboBox.addItem(t);
        add(comboBox);

        consultaTabla = new JButton("consulta Tabla");
        consultaTabla.addActionListener(new Accion(comboBox));
        add(consultaTabla);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
    }

    public class Accion implements ActionListener{
        JComboBox cmbo;
        public Accion(JComboBox cmbo){
            this.cmbo = cmbo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Tabla tabla;
            if (e.getSource() == consultaVista){
                String selectedItem = comboVistas.getSelectedItem().toString();
                switch (selectedItem){
                    case "":
                        break;
                    case "Poblacion de cada continente":
                        tabla = new Tabla("habitantes_continente");
                        comboVistas.setSelectedIndex(0);
                        break;
                    case "Poblacion de cada mundo":
                        tabla = new Tabla("habitantes_mundo");
                        comboVistas.setSelectedIndex(0);
                        break;
                    case "Miembros y alianzas":
                        tabla = new Tabla("miembros_alianzas");
                        comboVistas.setSelectedIndex(0);
                        break;
                }
            }
            else {
                String selectedItem = comboBox.getSelectedItem().toString();
                if (selectedItem !=  "")
                    tabla = new Tabla(selectedItem);
                    comboBox.setSelectedIndex(0);
            }
        }
    }

}
