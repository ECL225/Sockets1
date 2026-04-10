import java.io.*;
import java.net.*;

void main() {
    int puerto = 5000;

    try (ServerSocket servidor = new ServerSocket(puerto)) {
        System.out.println("--- Servidor Multicliente esperando conexiones en el puerto " + puerto + " ---");

        while (true) {
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde: " + socketCliente.getInetAddress());
            Thread hilo = new Thread(new ClienteManager(socketCliente));
            hilo.start();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

class ClienteManager implements Runnable {
    private Socket socket;

    public ClienteManager(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje = entrada.readLine();
            System.out.println("Mensaje recibido: " + mensaje);

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println("Hola desde el servidor multicliente. Has sido atendido con éxito.");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}