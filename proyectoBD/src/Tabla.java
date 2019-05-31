import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Tabla extends JFrame {
    Conexion conexion;
    public Tabla(String consulta){
        conexion = new Conexion();
        DefaultTableModel model = new DefaultTableModel();
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
        Connection acceso = conexion.getConexion();
        try {
            Statement statement = acceso.createStatement();
            ResultSet rS = statement.executeQuery("select * from " + consulta);
            ResultSetMetaData rSMD = rS.getMetaData();
            int columnas = rSMD.getColumnCount();

            for (int i = 1; i <= columnas ; i++)
                model.addColumn(rSMD.getColumnName(i));

            while (rS.next()){
                Object[] fila = new Object[columnas];

                for (int i = 0; i < columnas; i++)
                    fila[i] = rS.getObject(i + 1);

                model.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setSize(700, 250);
    }
}
