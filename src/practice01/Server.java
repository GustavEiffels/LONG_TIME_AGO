package practice01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final static Logger LOG = Logger.getGlobal();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            // Client 대기하다가 client 접속 시 Accept
            Socket socket = serverSocket.accept();
            System.out.println("::::                ::::");
            System.out.println("::::                ::::");
            System.out.println("::::  SERVER START  ::::");
            System.out.println("::::                ::::");
            System.out.println("::::                ::::");
            System.out.println("Connected by " + socket.getLocalAddress() + ", PORT : " + socket.getLocalPort());

            // Client 가 입력한 값 읽기
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = bufReader.readLine();
            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("The Message is : " + message);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Please Writing Something to your client");

            bufReader = new BufferedReader(new InputStreamReader(System.in));

            BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufwriter.write(bufReader.readLine());
            bufwriter.newLine();
            bufwriter.flush();

            socket.close();
            serverSocket.close();
            bufReader.close();
            bufwriter.close();

        } catch (Exception e) {
            LOG.setLevel(Level.INFO);
            LOG.info(e.getMessage());
        }
    }
}
