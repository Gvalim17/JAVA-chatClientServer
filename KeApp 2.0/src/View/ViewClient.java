package View;

import Module.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ViewClient {
    private JTextField messageTextField;

    public ViewClient() {
        JFrame frame = new JFrame("Client Chat");
        frame.setSize(400, 300);

        String nickName = JOptionPane.showInputDialog("Informe seu nome de usuário");
        frame.setTitle(nickName);

        Client.startClient(nickName);

        JTextArea chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        messageTextField = new JTextField();
        JButton sendButton = new JButton("Enviar");
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        inputPanel.add(messageTextField, constraints);
        inputPanel.add(sendButton);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        messageTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage(messageTextField.getText());
                messageTextField.setText("");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.sendMessage(messageTextField.getText());
                messageTextField.setText("");
            }
        });

        PrintStream terminalPrintStream = new PrintStream(new MessageOutputStream(System.out, chatTextArea));
        System.setOut(terminalPrintStream);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewClient();
        });
    }

    class MessageOutputStream extends OutputStream {
        private OutputStream originalOutputStream;
        private JTextArea textArea;

        public MessageOutputStream(PrintStream originalOutputStream, JTextArea textArea) {
            this.originalOutputStream = originalOutputStream;
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            originalOutputStream.write(b);
            textArea.append(String.valueOf((char) b));
        }
    }
}