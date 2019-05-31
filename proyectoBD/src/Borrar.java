import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class Borrar extends JPanel {
    Conexion conexion;
    JComboBox comboBox;
    JButton aceptar;
    JTable table;
    JScrollPane scrollPane;
    ResultSet rS, rs;
    Statement statement;

    public Borrar(){
        BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        setLayout(layout);
        setVisible(true);
        ArrayList<String> tablas = getTablesNames();
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(new JLabel("Tabla a borrar:"));
        comboBox = new JComboBox();
        comboBox.addItem("");
        for (String t: tablas)
            comboBox.addItem(t);

        comboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTableRows(tableModel, comboBox.getSelectedItem().toString());
                scrollPane.setVisible(true);
            }
        });
        add(comboBox);

        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel("Seleccione un renglon a borrar"));
        add(scrollPane);
        aceptar = new JButton("Borrar :)");

        aceptar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rS.absolute(table.getSelectedRow() + 1);
                    rS.deleteRow();
                    tableModel.removeRow(table.getSelectedRow());
                } catch (Exception ex) {
                    ex.printStackTrace();}
            }
        });

        add(aceptar);
    }

    public ArrayList<String> getTablesNames(){
        conexion = new Conexion();
        ArrayList<String> tablas = new ArrayList<>();
        Connection acceso = conexion.getConexion();

        try {
            DatabaseMetaData md = acceso.getMetaData();
            rs = md.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next())
                tablas.add(rs.getString("TABLE_NAME"));

        } catch (Exception e) {
            e.printStackTrace();}
        return tablas;
    }

    public void getTableRows(DefaultTableModel model, String tabla){
        model.setColumnCount(0);
        model.setNumRows(0);
        if (!tabla.equals("") ) {
            Connection acceso = conexion.getConexion();
            try {
                statement = acceso.createStatement(ResultSet.CONCUR_UPDATABLE,
                        ResultSet.CONCUR_UPDATABLE);
                rS = statement.executeQuery("select * from " + tabla);
                ResultSetMetaData rSMD = rS.getMetaData();
                int columnas = rSMD.getColumnCount();

                for (int i = 1; i <= columnas; i++)
                    model.addColumn(rSMD.getColumnName(i));

                while (rS.next()) {
                    Object[] fila = new Object[columnas];

                    for (int i = 0; i < columnas; i++)
                        fila[i] = rS.getObject(i + 1);

                    model.addRow(fila);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
