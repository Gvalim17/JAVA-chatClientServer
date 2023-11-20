package Module;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Client {
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    static String username;

    public static void startClient(String username) {
        try {
            socket = new Socket("10.136.64.43", 1234); // Senac - 10.136.64.43
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Client.username = username;
            new Thread(() -> listenForMessage()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void listenForMessage() {
        String msgFromGroupChat;
        while (socket.isConnected()) {
            try {
                msgFromGroupChat = bufferedReader.readLine();
                System.out.println(msgFromGroupChat);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    public static void sendMessage(String message) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("(HH:mm) - ");
            String formattedDate = dateFormat.format(new Date());
            bufferedWriter.write(formattedDate + username + ": " + message + "\n");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    public static void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
