package practice01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 9999);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("Please Say Something to Server!!");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            writer.write(br.readLine());
            writer.newLine();
            writer.flush();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(" Server Said : " + br.readLine());

            socket.close();
            br.close();
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
