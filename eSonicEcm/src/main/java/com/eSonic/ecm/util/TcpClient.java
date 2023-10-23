package com.eSonic.ecm.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient {

	private Socket socket;
	private DataOutputStream dos;
	private BufferedReader br;

	public String connect(String ip, int port, String id, String pw) {

		try {
			System.out.println("");
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dos.writeUTF(id);
			dos.writeUTF(pw);
			return br.readLine(); // read response from server
		} catch (Exception e) {
			e.printStackTrace();
			
			return "Not Connected";
			
		}
	}

	public String create(String filePath, String volumeId) {
		try {

			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int read;

			dos.writeUTF(volumeId);
			dos.writeLong(file.length());

			while ((read = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, read);
			}

			fis.close();
			
			
			return br.readLine(); // read response from server
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
