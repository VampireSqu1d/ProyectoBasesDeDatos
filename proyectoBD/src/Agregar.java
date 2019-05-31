import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class Agregar extends JPanel {
    JComboBox comboBox;
    JButton actualizar;
    Borrar borrar;
    JPanel scrollPane;
    Statement statement;

    public Agregar(){
        BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        setLayout(layout);
        setVisible(true);
        borrar = new Borrar();
        ArrayList<String> tablas = borrar.getTablesNames();
        scrollPane = new JPanel();
        add(new JLabel("Inserte los datos en la tabla:"));

        comboBox = new JComboBox();
        comboBox.addItem("");
        for (String t: tablas)
            comboBox.addItem(t);
        comboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnas = getColumnLabels(comboBox.getSelectedItem().toString());

                if (scrollPane.getComponents().length > 0){
                    scrollPane.removeAll();
                    validate();
                    repaint();
                }
                scrollPane = GrabValues(columnas);
                add(scrollPane);
                setSize(0,0);
            }
        });

        add(comboBox);
        actualizar = new JButton("Insetar valores");
        actualizar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTextFromFields();
                scrollPane.removeAll();
                validate();
                repaint();
            }
        });
        add(actualizar);

    }

    public JPanel GrabValues( Object[] nombresColumnas){
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.PAGE_AXIS));
        if (nombresColumnas == null) return contenedor;

        for (int i = 0; i < nombresColumnas.length; i++) {
            JLabel label = new JLabel(nombresColumnas[i].toString() + ": ");
            contenedor.add(label);
            contenedor.add(new JTextField());

        }
        contenedor.validate();
        return  contenedor;
    }

    public void getTextFromFields(){
            Component[] components = scrollPane.getComponents();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConexion();
        String insertar = "INSERT INTO " + comboBox.getSelectedItem().toString() + " VALUES(";

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JTextField){
                JTextField text = (JTextField) components[i];
                insertar = insertar + "\'" + text.getText() + "\'" + ",";
            }
        }

        insertar = insertar.substring(0, insertar.length() - 1) + ");";
        System.out.println(insertar);
        try {
            Statement ps = con.createStatement();
            ps.executeUpdate(insertar);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getColumnLabels(String tabla) {
        Conexion conexion = new Conexion();

        if (!tabla.equals("")) {
            Connection acceso = conexion.getConexion();
            try {
                statement = acceso.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rS = statement.executeQuery("select * from " + tabla);
                ResultSetMetaData rSMD = rS.getMetaData();
                int columnas = rSMD.getColumnCount();
                String[] labelsColumnas = new String[columnas];
                for (int i = 0; i < columnas; i++){
                    labelsColumnas[i] = rSMD.getColumnName(i + 1) + " (" + rSMD.getColumnTypeName( i + 1)  + ")";
            }
                return labelsColumnas;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
