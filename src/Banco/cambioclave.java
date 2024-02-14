package Banco;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cambioclave extends JFrame {
    private JPanel cambiopn;
    private JButton cambiarButton;
    private JButton menuButton;
    private JTextField txt1;
    private JTextField txt2;
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


    public cambioclave() {
        super("CambioClave");
        this.nombreUsuario = nombreUsuario;
        setContentPane(cambiopn);
        setSize(550, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frameRegreso = (JFrame) SwingUtilities.getWindowAncestor(cambiopn);
                frameRegreso.dispose();
            }
        });
        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String claveActual = txt1.getText();
                String nuevaClave = txt2.getText();
                cambiarClave(claveActual, nuevaClave);
            }
        });
    }
    private void cambiarClave(String claveActual, String nuevaClave) {
        try {
            Connection conn = establecerConexion();

            // Verificar si la nueva contraseña cumple con ciertos criterios de complejidad
            if (!validarNuevaClave(nuevaClave)) {
                JOptionPane.showMessageDialog(null, "La nueva clave no cumple con los criterios de complejidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String consultaClaveQuery = "SELECT Contraseña FROM Usuarios WHERE NombreUsuario = ?";

            // Utilizamos try-with-resources para asegurarnos de que las conexiones, declaraciones y resultados se cierren correctamente
            try (PreparedStatement consultaStmt = conn.prepareStatement(consultaClaveQuery)) {
                consultaStmt.setString(1, nombreUsuario);
                ResultSet rs = consultaStmt.executeQuery();

                if (rs.next()) {
                    String claveAlmacenada = rs.getString("Contraseña");
                    if (claveAlmacenada.equals(claveActual)) {
                        String updateQuery = "UPDATE Usuarios SET Contraseña = ? WHERE NombreUsuario = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                            stmt.setString(1, nuevaClave);
                            stmt.setString(2, nombreUsuario);
                            int rowsUpdated = stmt.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "Clave cambiada exitosamente.");
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo cambiar la clave.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La clave actual no coincide con la almacenada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cambiar la clave: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para validar la complejidad de la nueva clave
    private boolean validarNuevaClave(String nuevaClave) {
        // Aquí puedes implementar tu lógica para verificar la complejidad de la contraseña
        // Por ejemplo, longitud mínima, uso de caracteres especiales, etc.
        return nuevaClave.length() >= 8; // Ejemplo: la clave debe tener al menos 8 caracteres
    }
}
