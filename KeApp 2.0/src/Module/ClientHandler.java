package Module;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

//    public String clientUsername;

    public ClientHandler(Socket socket) {
//        broadcastMessage("SERVER: " + clientUsername + " entrou no chat");
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            synchronized (clientHandlers) {
                clientHandlers.add(this);

            }

        } catch (IOException e) {
            closeEverything();
        }
    }


    public void run() {

        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    public void removeClientHandler() {
        synchronized (clientHandlers) {
            clientHandlers.remove(this);
        }
//        broadcastMessage("SERVER: " + clientUsername + " saiu do chat");
    }

    public void closeEverything() {
        removeClientHandler();
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
