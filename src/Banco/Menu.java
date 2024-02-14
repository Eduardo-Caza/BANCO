package Banco;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu extends JFrame {
    private JRadioButton saldobtn;
    private JRadioButton retirobtn;
    private JRadioButton depositobtn;
    private JRadioButton salirbtn;
    private JPanel panelmn;
    private JRadioButton Cambio;
    private JRadioButton descargarFacturaRadioButton;
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

    public Menu(String nombreUsuario) {
        super("menu");
        this.nombreUsuario = nombreUsuario;
        setContentPane(panelmn);
        setSize(550, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        salirbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showOptionDialog(Menu.this,
                        "¡Muchas gracias!",
                        "Salir",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"OK"},
                        "OK");
                if (respuesta == JOptionPane.OK_OPTION) {
                    dispose();
                }
            }
        });
        saldobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Saldo saldo =  new Saldo(nombreUsuario);
                saldo.setVisible(true);
                dispose();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelmn);
                frame.dispose();
            }
        });
        retirobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Retiro retiro = new Retiro(nombreUsuario);
                retiro.setVisible(true);
                dispose();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelmn);
                frame.dispose();
            }
        });
        depositobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposito deposito = new Deposito(nombreUsuario);
                deposito.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(panelmn);
                frame.dispose();
            }
        });
        Cambio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambioclave cambio = new cambioclave();
                cambio.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(panelmn);
                frame.dispose();
            }
        });
        descargarFacturaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descargarHistorialTransacciones("2024-01-01", "2024-02-01");
            }
        });
    }

    public void descargarHistorialTransacciones(String fechaInicio, String fechaFin) {
        try {
            Connection conn = establecerConexion();

            String query = "SELECT * FROM Transacciones WHERE FechaHora BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();

            FileWriter fileWriter = new FileWriter("C:\\Users\\BM\\Downloads\\historial_transacciones.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("ID\tUsuario\tTipo de transacción\tMonto\tFecha y hora");

            while (rs.next()) {
                int id = rs.getInt("ID");
                int userId = rs.getInt("UsuarioID");
                String tipoTransaccion = rs.getString("TipoTransaccion");
                float monto = rs.getFloat("Monto");
                String fechaHora = rs.getString("FechaHora");

                printWriter.println(id + "\t" + userId + "\t" + tipoTransaccion + "\t" + monto + "\t" + fechaHora);
            }

            printWriter.close();
            fileWriter.close();
            rs.close();
            stmt.close();
            conn.close();

            JOptionPane.showMessageDialog(null, "Historial de transacciones descargado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al descargar el historial de transacciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
