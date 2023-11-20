package Module;

import View.ViewServer;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static PrintStream serverLogPrintStream;
    private static boolean serverRunning;

    public static void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(1234);
                logMessage("Servidor Iniciado!");
                serverRunning = true;

                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Um novo cliente se conectou!");

                    Thread clientThread = new Thread(() -> {
                        ClientHandler clientHandler = new ClientHandler(socket);
                        clientHandler.run();
                    });
                    clientThread.start();
                }


                serverLogPrintStream = new PrintStream(new MessageOutputStream(ViewServer.getServerLogTextArea()));
                System.setOut(serverLogPrintStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverRunning = false;
            }
        }).start();
    }

    public static void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                if (serverRunning) {
                    logMessage("Servidor Fechado!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (serverRunning) {
                logMessage("Falha ao fechar o servidor!");
            }
        }
    }

    private static void logMessage(String message) {
        ViewServer.logMessage(message);
    }

    private static class MessageOutputStream extends OutputStream {
        private JTextArea textArea;

        public MessageOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char) b));
        }
    }
}