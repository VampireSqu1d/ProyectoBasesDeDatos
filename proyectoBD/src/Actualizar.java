import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Actualizar extends JPanel {
    JComboBox comboBox;
    JButton actualizar;
    Borrar borrar;
    JTable table;
    JScrollPane scrollPane;

    public Actualizar(){
        BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        setLayout(layout);
        setVisible(true);
        borrar = new Borrar();
        ArrayList<String> tablas = borrar.getTablesNames();
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(new JLabel("tabla a actualizar:"));

        comboBox = new JComboBox();
        comboBox.addItem("");
        for (String t: tablas)
            comboBox.addItem(t);
        comboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrar.getTableRows(tableModel, comboBox.getSelectedItem().toString());
                scrollPane.setVisible(true);
            }
        });
        add(comboBox);

        add(new JLabel("Cambie el valor en la talba"));

        add(scrollPane);

        actualizar = new JButton("Actualizar");
        actualizar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrar.rS.absolute(table.getSelectedRow() + 1);
                    borrar.rS.deleteRow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                addRows(table, comboBox);
            }
        });

        add(actualizar);
    }

    public void addRows(JTable tablaa, JComboBox comboBoxx){
        //DefaultTableModel tableModel = (DefaultTableModel) tablaa.getModel();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConexion();
        String insertar = "INSERT INTO " + comboBoxx.getSelectedItem().toString() + " VALUES (";
        int row = tablaa.getSelectedRow();
        for (int i = 0; i < tablaa.getColumnCount() ; i++) {
                insertar = insertar + "\'" + tablaa.getValueAt(row, i ).toString() + "\'" + ",";
            }

        insertar = insertar.substring(0, insertar.length() - 1) + ");";

        try {
            PreparedStatement ps = con.prepareStatement(insertar);

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}

