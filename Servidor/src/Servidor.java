import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String paraulaClau = args[1];
        try (ServerSocket servidor = new ServerSocket(port)) {
            System.out.println("--- Servidor esperando conexiones en el puerto " + port + " ---");
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde: " + socketCliente.getInetAddress());
            Thread hilo = new Thread(new Server(socketCliente, port, paraulaClau));
            hilo.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Server implements Runnable {
        private Socket socket;
        int port;
        String paraulaClau;

        public Server(Socket socket, int port, String paraulaClau) {
            this.socket = socket;
            this.port = port;
            this.paraulaClau = paraulaClau;
        }

        @Override
        public void run() {
            try {
                System.out.println("Iniciando Servidor... Ok");
                Scanner scan = new Scanner(System.in);
                PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String missatge;
                String resposta;
                while (true) {
                    resposta = entrada.readLine();
                    if (resposta.equals("Client keyword detected") || resposta == null){
                        System.out.println("Client keyword detected");
                        break;
                    }
                    else if (resposta.equalsIgnoreCase(paraulaClau)) {
                        System.out.println("Server keyword detected");
                        break;
                    }
                    else {
                        System.out.println("Client: " + resposta);
                    }

                    System.out.println("Servidor: ");
                    missatge = scan.nextLine();
                    if (missatge.equalsIgnoreCase(paraulaClau)) {
                        System.out.println("Server keyword detected");
                        break;
                    }
                    sortida.println(missatge);
                }
                System.out.println("Tancant Server... Ok");
                scan.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
