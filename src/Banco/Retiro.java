package Banco;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Retiro extends JFrame {
    private JTextField pantallar;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton borrarButton;
    private JButton enter;
    private JButton menu;
    private JPanel Retiro;
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


    public Retiro(String nombreUsuario){
        super("Retiro");
        this.nombreUsuario = nombreUsuario;
        setContentPane(Retiro);
        setSize(550,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frameRegreso = (JFrame) SwingUtilities.getWindowAncestor(Retiro);
                frameRegreso.dispose();
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallar.setText("");
            }
        });

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRetiro();
            }
        });

        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "1";
                pantallar.setText(cons);
            }
        });

        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "2";
                pantallar.setText(cons);
            }
        });

        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "3";
                pantallar.setText(cons);
            }
        });

        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "4";
                pantallar.setText(cons);
            }
        });

        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "5";
                pantallar.setText(cons);
            }
        });

        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "6";
                pantallar.setText(cons);
            }
        });

        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "7";
                pantallar.setText(cons);
            }
        });

        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "8";
                pantallar.setText(cons);
            }
        });

        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "9";
                pantallar.setText(cons);
            }
        });

        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallar.getText() + "0";
                pantallar.setText(cons);
            }
        });
    }

    private void realizarRetiro() {
        try {
            Connection conn = establecerConexion();

            String query = "SELECT Saldo FROM Usuarios WHERE NombreUsuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                float saldoActual = rs.getFloat("Saldo");
                String retiroStr = pantallar.getText();
                float retiro = Float.parseFloat(retiroStr);
                if (retiro > 0 && retiro <= saldoActual) {
                    float nuevoSaldo = saldoActual - retiro;

                    String updateQuery = "UPDATE Usuarios SET Saldo = ? WHERE NombreUsuario = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setFloat(1, nuevoSaldo);
                    updateStmt.setString(2, nombreUsuario);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Se retiró: " + retiro + "\nSaldo actual: " + nuevoSaldo);
                } else {
                    JOptionPane.showMessageDialog(null, "Monto de retiro inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar el retiro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
