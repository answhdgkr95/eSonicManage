package eSonicFileManage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
	public static int thcnt = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2102);
		//연결이 추가될때마다 Thread로 접속추가
        while (true) {
        	thcnt++;
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket, thcnt)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final int threadCnt;
    

    public ClientHandler(Socket socket, int threadCnt) {
        this.clientSocket = socket;
        this.threadCnt = threadCnt;
    }

	TcpServer tcpServer = new TcpServer();
    public void run() {
    	try {
			tcpServer.runServer(this.clientSocket,this.threadCnt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}