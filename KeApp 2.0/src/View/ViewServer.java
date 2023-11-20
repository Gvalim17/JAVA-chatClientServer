package View;

import Module.Server;

import javax.swing.*;
import java.awt.*;

public class ViewServer {
    private static JTextArea serverLogTextArea;
    private JButton startButton;
    private JButton stopButton;

    public ViewServer() {
        JFrame frame = new JFrame("Servidor");
        frame.setSize(400, 300);

        serverLogTextArea = new JTextArea();
        serverLogTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(serverLogTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        startButton = new JButton("Iniciar Servidor");
        frame.add(startButton, BorderLayout.NORTH);

        stopButton = new JButton("Fechar Servidor");
        frame.add(stopButton, BorderLayout.SOUTH);
        stopButton.setEnabled(false);

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            Server.startServer();
        });


        stopButton.addActionListener(e -> {
            stopButton.setEnabled(false);
            Server.stopServer();
        });

        frame.setVisible(true);
    }

    public static JTextArea getServerLogTextArea() {
        return serverLogTextArea;
    }

    public static void logMessage(String message) {
        serverLogTextArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewServer();
        });
    }
}