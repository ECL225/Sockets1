import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String paraulaClau = args[1];

        try {
            System.out.println("Iniciando cliente... Ok");
            Socket socket = new Socket("localhost", port);

            Scanner scan = new Scanner(System.in);
            PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String missatge = "";
            String resposta = "";
            boolean kword = false;
            while(true){
                System.out.println("Client: ");
                missatge = scan.nextLine();
                if (missatge.equalsIgnoreCase(paraulaClau)){
                    System.out.println("Client keyword detected");
                    sortida.println("Client keyword detected");
                    break;
                }
                else {
                    sortida.println(missatge);
                }
                resposta = entrada.readLine();

                if (resposta == null || resposta.equals("Server keyword detected")){
                    System.out.println("Server keyword detected");
                    break;
                }

                if(resposta.equalsIgnoreCase(paraulaClau)){
                    sortida.println("Client keyword detected");
                    System.out.println("Client keyword detected");
                    break;
                }
                else {
                    System.out.println("Servidor: " + resposta);
                }
            }
            System.out.println("Tancant client... Ok");
            scan.close();
            socket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
