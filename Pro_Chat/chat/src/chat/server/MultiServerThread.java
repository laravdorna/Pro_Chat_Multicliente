package chat.server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Este hilo es responsable de leer la entrada del servidor e imprimirla en
 * consola. Se ejecuta en un bucle infinito hasta que el cliente se desconecta
 * del servidor.
 *
 * @author Juan Martinez Piñeiro y Lara Vázquez Dorna
 */
public class MultiServerThread extends Thread {

    private Socket socket;
    private Server server;
    private PrintWriter writer;

    /**
     * Constructor. crea un servidor y un socket
     *
     * @param socket
     * @param server
     */
    public MultiServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * metodo heredado de la interface Thread que imprime los usuarios
     * conectados y sus respuestas pasandoselas a los otros clientes
     */
    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "Nuevo usuario conectado: " + userName;
            server.broadcast(serverMessage, this);
            server.saveToFile(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
                server.saveToFile(serverMessage, this);

            } while (!clientMessage.equals("chao"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " ha dejado el chat.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envía una lista de usuarios en línea al usuario recién conectado.
     */
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Usuarios conectados: " + server.getUserNames());
        } else {
            writer.println("No hay otros usuarios conectados");
        }
    }

    /**
     * Envia el mensaje al cliente.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}
