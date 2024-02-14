package Banco;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Saldo extends JFrame {
    private JButton menubtn;
    private JPanel panelsl;
    private JLabel Saldotxt;
    private String nombreUsuario;

    private Connection establecerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el controlador JDBC", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3305/banco";
        String usuarioDB = "root";
        String contraseñaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseñaDB);
    }


    public Saldo(String nombreUsuario) {
        super("Saldo");
        this.nombreUsuario = nombreUsuario;
        setContentPane(panelsl);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menubtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelsl);
                frame.dispose();
            }
        });

        mostrarSaldoDesdeBaseDeDatos();
    }

    private void mostrarSaldoDesdeBaseDeDatos() {
        try {
            Connection conn = establecerConexion();

            String query = "SELECT Saldo FROM Usuarios WHERE NombreUsuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                float saldo = rs.getFloat("Saldo");
                Saldotxt.setText(String.valueOf(saldo));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el saldo desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
