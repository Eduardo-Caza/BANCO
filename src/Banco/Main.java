package Banco;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Bienvenida bienve = new Bienvenida();
                bienve.setSize(550,500);
                bienve.setVisible(true);
                bienve.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
