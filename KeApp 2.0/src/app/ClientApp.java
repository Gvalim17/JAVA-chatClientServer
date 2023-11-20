package app;

import View.ViewClient;
import View.ViewServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientApp {
    private static void app() {
        JFrame janelaPrincipal = new JFrame("JavaZap");
        janelaPrincipal.setResizable(false);
        janelaPrincipal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janelaPrincipal.setSize(250, 200);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        Container caixa = janelaPrincipal.getContentPane();
        caixa.setLayout(null);

        JButton botaoUsuario = new JButton("Cliente");
        botaoUsuario.setBounds(75, 90, 100, 50);
        janelaPrincipal.add(botaoUsuario);

        JButton botaoServidor = new JButton("Servidor");
        botaoServidor.setBounds(75, 20, 100, 50);
        janelaPrincipal.add(botaoServidor);

        janelaPrincipal.setVisible(true);

        botaoUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewClient viewClient = new ViewClient();
            }
        });

        botaoServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewServer viewServidor = new ViewServer();
            }
        });

        janelaPrincipal.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        app();
    }
}
