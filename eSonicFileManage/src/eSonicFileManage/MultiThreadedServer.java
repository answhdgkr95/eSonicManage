package eSonicFileManage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2102);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

	TcpServer tcpServer = new TcpServer();
    public void run() {
    	try {
			tcpServer.runServer(this.clientSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 여기에 클라이언트와의 통신 코드를 작성하세요.
        // 예를 들어, 파일을 전송하거나 받는 코드를 여기에 작성할 수 있습니다.
    }
}