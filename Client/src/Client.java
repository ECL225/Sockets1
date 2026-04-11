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

            String missatge;
            String resposta;

            while(true){
                System.out.println("Tu: ");
                missatge = scan.nextLine();
                sortida.println(missatge);
                System.out.println("Enviant missatge... Ok");

                if(missatge.equalsIgnoreCase(paraulaClau)){
                    break;
                }

                resposta = entrada.readLine();
                System.out.println("Servidor: " + resposta);

                if (resposta.equalsIgnoreCase(paraulaClau)){
                    break;
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
