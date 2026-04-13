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
                if (missatge.equalsIgnoreCase(paraulaClau)){ // comprobes que no ha escrit la keyword
                    System.out.println("Client keyword detected");
                    sortida.println("Client keyword detected");
                    break;
                }
                else {// envies el missatge
                    sortida.println(missatge);
                }
                resposta = entrada.readLine();

                if (resposta == null || resposta.equals("Server keyword detected")){ // comprobes que no s'ha tancat la conexio ni s'ha trigerejat la keyword del servidor
                    System.out.println("Server keyword detected");
                    break;
                }

                if(resposta.equalsIgnoreCase(paraulaClau)){ // si el missatge que ha arribat no es la keyword del client mostra el missatge
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
