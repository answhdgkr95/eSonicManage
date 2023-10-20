package eSonicFileManage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;








public class TcpServer {

    private static final Logger logger = Logger.getLogger(TcpServer.class.getName());
	
	public String g_Server_Url = "http://localhost:8080/eSonicEcm";
	public String g_Used_Volume = "";

//	dos.writeUTF(id + "_" + pwStr + "_" + contentClass);
//	
//
//	dos.writeUTF(archiveId + "_" + ext + "_" + fileSize);

	@SuppressWarnings("resource")
	public void runServer(Socket socket) throws IOException {

			DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String getValue = dis.readUTF();
	        logger.info("rtnStr : " + getValue);
			String id = getValue.split("_")[0];
			String pwStr = getValue.split("_")[1];
			String contentClass = getValue.split("_")[2];

			// ID PW 조회
			String rtnUserSearch = searchUser(id, pwStr);
			switch (rtnUserSearch) {
			case "SUCCESS":

				pw.println(rtnUserSearch); // send response to client

				getValue = dis.readUTF();

				switch (getValue) {
				
				case "create":
					// create ();
					getValue = dis.readUTF();
					String volumeId = getValue.split("_")[0];
			//		String ext = getValue.split("_")[1];
					String fileSize = getValue.split("_")[2];
					long fileSizeL = Long.valueOf(fileSize);
					
					// volumeid 로 저장패스 지정
					String saveVol = searchVolume(volumeId, fileSize);
					// elementid 생성
					String filekey = createFileKey(saveVol);

					String savePath = saveVol + filekey;

					
					//DB 에 volume 업데이트
					String rtnStr = updateVolume(g_Used_Volume,fileSize,"0");
					if(rtnStr.equals("SUCCESS")) {
						//파일 저장
						FileOutputStream fos = new FileOutputStream(savePath);

						System.out.println("savePath : "  + savePath);
						byte[] buffer = new byte[4096];
						int read;
						long totalRead = 0L;

						while (totalRead < fileSizeL) {
							read = dis.read(buffer);
							totalRead += read;
							fos.write(buffer, 0, read);
						}

						fos.close();
						

						//DB Insert
						//ES_FILE_SIZE, ES_ARCHIVE_ID, ES_CONTENT_CLASS, ES_CONTENT_ID, ES_CREATE_USER, ES_FILE_EXT, ES_FILE_PATH, ES_VOLUME_ID
						HashMap<String, String> hm = new HashMap<String, String>();
						hm.put("esFileSize", fileSize);
						hm.put("esArchiveId", volumeId);
						hm.put("esContentClass", contentClass);
						hm.put("esContentId", filekey);
						hm.put("esCreateUser", id);
						hm.put("esFilePath", savePath);
						hm.put("esVolumeId", g_Used_Volume);
						

				        // Gson 객체 생성
				        Gson gson = new Gson();

				        // HashMap을 JSON 문자열로 변환
				        String jsonInputString = gson.toJson(hm);
						
						rtnStr = insertEsContent(jsonInputString);
						System.out.println("rtnStr : " + rtnStr);

						pw.println(rtnStr); // send response to client
						
						
					}else {

						pw.println("0"); // send response to client
					}
					
					
					
					
					
					break;
				case "download":
					
					String esElementId = dis.readUTF();
					//elementid 로 이미지경로 가져오는부분 필요함
					String filepath = selectFile(esElementId);
					System.out.println("filepathfilepath : " + filepath);
					//다운로드 로그 찍는부분 필요함
				    File file = new File(filepath);
				    if (file.exists()) {
				        // 파일 크기와 이름을 클라이언트에게 전송
				        dos.writeLong(file.length());

				        // 파일 데이터를 읽어 클라이언트에게 전송
				        FileInputStream fis = new FileInputStream(file);
				        byte[] bufferD = new byte[4096];
				        int readD;

				        while ((readD = fis.read(bufferD)) > 0) {
				            dos.write(bufferD, 0, readD);
				        }

				        fis.close();
				        dos.writeUTF("SUCCESS");
				        System.out.println("파일 전송 완료: " + filepath);
				    } else {
				        dos.writeUTF("ERROR");
				        System.out.println("파일이 존재하지 않습니다: " + filepath);
				    }
					break;
				case "update":
					String getValueU = dis.readUTF();
					System.out.println("getValueU : " + getValueU);
					String esElementIdU = getValueU.split("_")[0];
					//String filepathU = getValue.split("_")[1];
					long fileSizeU = Long.valueOf(getValueU.split("_")[2]);
					String fileSizeUS = getValueU.split("_")[2];

					//스토리지 사이즈 변경
					//DB 에 volume 업데이트
					String rtnStrUdt = updateVolumeUdt(g_Used_Volume,fileSizeUS,esElementIdU );
				
					
					
					
					//파일사이즈 변경
					
					
					System.out.println("esElementIdU : " + esElementIdU);
					//elementid 로 이미지경로 가져오는부분 필요함
					String esElementPathU = selectFile(esElementIdU);
					//다운로드 로그 찍는부분 필요함
				    File fileUdt = new File(esElementPathU);

					//파일 저장
					fileUdt.delete();

					FileOutputStream fos = new FileOutputStream(esElementPathU);

					System.out.println("savePath : "  + esElementPathU);
					byte[] buffer = new byte[4096];
					int read;
					long totalRead = 0L;
					
					while (totalRead < fileSizeU) {
						read = dis.read(buffer);
						totalRead += read;
						fos.write(buffer, 0, read);
					}

					fos.close();
					
					
					
					

					System.out.println("rtnStr : " + "S");

					pw.println("S"); // send response to client
					
					break;
				case "delete":
					
					
					
					break;
				}
				
				
				


				break;

			default:
				System.out.println("Exit");
				pw.println("0"); // send response to client

				dis.close();
				pw.close();
				socket.close();
				return;

			}
				


			dis.close();
			pw.close();
			socket.close();

	}
	
	public String insertEsContent(String jsonInputString) {
		String rtnStr = "";
		String password = rtnUrlPost(g_Server_Url + "/interface/content/insert", jsonInputString);
		
		Gson gson = new Gson();
		Map<String, Object> map = null;
		try {
		    map = gson.fromJson(password, new TypeToken<Map<String, Object>>(){}.getType());
		} catch (JsonSyntaxException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	        if ("01".equals(map.get("rtnCd"))) {
	        	
	        }else {

	            System.out.println(map.get("rtnMsg"));  
	        }

            rtnStr = (String)map.get("contentKey");
	        
			return rtnStr;
	}
	
	public String updateEsContent(String jsonInputString) {
		String rtnStr = "";
		String password = rtnUrlPost(g_Server_Url + "/interface/content/insert", jsonInputString);
		
		Gson gson = new Gson();
		Map<String, Object> map = null;
		try {
		    map = gson.fromJson(password, new TypeToken<Map<String, Object>>(){}.getType());
		} catch (JsonSyntaxException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	        if ("01".equals(map.get("rtnCd"))) {
	        	
	        }else {

	            System.out.println(map.get("rtnMsg"));  
	        }

            rtnStr = (String)map.get("contentKey");
	        
			return rtnStr;
	}
	
	
	@SuppressWarnings("unchecked")
	public String selectFile(String esElementId) {
	      logger.info("esElementId : " + esElementId);
			String rtnStr = "";
			String rtnVal = rtnUrl(g_Server_Url + "/interface/content/search/one/" + esElementId);
	        logger.info("rtnVal : " + rtnVal);
	        
	        
	        
	        Gson gson = new Gson();
	        Map<String, Object> map = null;
	        try {
	            map = gson.fromJson(rtnVal, new TypeToken<Map<String, Object>>(){}.getType());
	        } catch (JsonSyntaxException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        if ("01".equals(map.get("rtnCd"))) {
	            Map<String, Object> esContentEntity = (Map<String, Object>) map.get("esContentEntity");
	            System.out.println(esContentEntity.get("esFilePath"));  // Outputs: "SUPER"
	            
	            rtnStr = (String)esContentEntity.get("esFilePath");
	        }else {

	            System.out.println(map.get("rtnMsg"));  
	            rtnStr = (String)map.get("rtnMsg");
	        }
	        
	        
			return rtnStr;
		
		
	
	}
	

	public String createFileKey(String volumePath) {
		String rtnFileKey = "";
		try {
			createDirectory(volumePath);
			// get current time
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String currentTime = now.format(formatter);

			// count the number of files in the directory that start with the current time
			long count;
			count = Files.list(Paths.get(volumePath))
					.filter(path -> path.getFileName().toString().startsWith(currentTime)).count();
			

			// generate file name
			String twoDigitNumber = String.format("%02d", (count + 1));
			String fileName = currentTime + twoDigitNumber;

			return fileName;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rtnFileKey;
		}

	}
	 public static void createDirectory(String path) {
	        File directory = new File(path);
	        if (!directory.exists()) {
	            boolean result = directory.mkdirs();
	            if (result) {
	            } else {
	                System.out.println("디렉토리 생성에 실패하였습니다.");
	            }
	        } 
	    }

    private HttpClient client = HttpClient.newHttpClient();
    
    
    public String rtnUrl(String url) {
    	System.out.println(url);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    @SuppressWarnings("finally")
	public String rtnUrlPost(String strUrl,String jsonInputString ) {
    	String rtnStr = "";
		try {
			URL url = new URL(strUrl);
	          // HTTP 연결 생성
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	          // POST 요청 설정
	          conn.setRequestMethod("POST");
	          conn.setDoOutput(true);

	          // Content-Type 헤더 설정
	          conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


	          // 요청 본문에 JSON 데이터 쓰기
	          try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
	              writer.write(jsonInputString);
	              writer.flush();
	          }

	          int responseCode = conn.getResponseCode();
	          System.out.println(responseCode);
	          // HTTP 응답 본문 읽기
	          BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	          String inputLine;
	          StringBuffer content = new StringBuffer();
	          while ((inputLine = in.readLine()) != null) {
	              content.append(inputLine);
	          }
	          rtnStr = content.toString();


	          in.close();
	          conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			return rtnStr;
		}
    }
    
    
    @SuppressWarnings("unchecked")
	public String updateVolume(String volumeId, String createFileSize, String removeFileSize) {
		String rtnStr = "";
		rtnStr = rtnUrl(g_Server_Url + "/interface/volume/update/" + volumeId + "/" + createFileSize + "/" + removeFileSize);
        
        
		Gson gson = new Gson();
		Map<String, Object> map = null;
		try {
		    map = gson.fromJson(rtnStr, new TypeToken<Map<String, Object>>(){}.getType());
		} catch (JsonSyntaxException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

        if ("01".equals(map.get("rtnCd"))) {

            System.out.println(map.get("rtnMsg"));  
            rtnStr = (String)map.get("rtnMsg");
        }else {

            System.out.println(map.get("rtnMsg"));  
            rtnStr = (String)map.get("rtnMsg");
        }
        
        
		return rtnStr;
	}
    
    @SuppressWarnings("unchecked")
	public String updateVolumeUdt(String volumeId, String updateFileSize, String esContentId) {
		String rtnStr = "";
		rtnStr = rtnUrl(g_Server_Url + "/interface/volume/updaterpc/" + volumeId + "/" + updateFileSize + "/" + esContentId);
        
        
		Gson gson = new Gson();
		Map<String, Object> map = null;
		try {
		    map = gson.fromJson(rtnStr, new TypeToken<Map<String, Object>>(){}.getType());
		} catch (JsonSyntaxException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

        if ("01".equals(map.get("rtnCd"))) {

            System.out.println(map.get("rtnMsg"));  
            rtnStr = (String)map.get("rtnMsg");
        }else {

            System.out.println(map.get("rtnMsg"));  
            rtnStr = (String)map.get("rtnMsg");
        }
        
        
		return rtnStr;
	}
    
    
	@SuppressWarnings("unchecked")
	public String searchUser(String id, String pw) {
		String rtnStr = "";
		String password = rtnUrl(g_Server_Url + "/interface/user/search/one/" + id);
        
        Gson gson = new Gson();
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(password, new TypeToken<Map<String, Object>>(){}.getType());
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if ("01".equals(map.get("rtnCd"))) {
            Map<String, Object> esUserEntity = (Map<String, Object>) map.get("esUserEntity");
            System.out.println(esUserEntity.get("esUserPw"));  // Outputs: "SUPER"
            if( esUserEntity.get("esUserPw").equals(pw)) {
                rtnStr = (String)map.get("rtnMsg");
            }else {
                rtnStr = "PWERROR";
            	
            }
        }else {

            rtnStr = (String)map.get("rtnMsg");
        }
        
        
		return rtnStr;
	}
	

	@SuppressWarnings("unchecked")
	public String searchVolume(String ARCHIVE_ID, String FileSize) {

		
		String rtnVol = "";
		
			String volumeInfo = rtnUrl(g_Server_Url + "/interface/volume/search/usedvol/" + ARCHIVE_ID + "/" + FileSize);
			System.out.println("volumeInfo : " + volumeInfo);
			Gson gson = new Gson();
			Map<String, Object> map = null;
			try {
			    map = gson.fromJson(volumeInfo, new TypeToken<Map<String, Object>>(){}.getType());
			} catch (JsonSyntaxException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

	        if ("01".equals(map.get("rtnCd"))) {
	        	
	            Map<String, Object> esVolumeVO = (Map<String, Object>) map.get("esVolumeVO");
	        	rtnVol = esVolumeVO.get("esVolumeName") + "\\" + esVolumeVO.get("esArchiveName") + "\\" ;
	            System.out.println(rtnVol);  // Outputs: "SUPER"
	    		g_Used_Volume = esVolumeVO.get("esVolumeId").toString();
	    		File directory = new File(rtnVol);

	    		 // 모든 하위 폴더를 가져옵니다.
	            File[] subfolders = directory.listFiles(File::isDirectory);

	            // 폴더가 없을 경우 1을 반환합니다.
	            if (subfolders == null || subfolders.length == 0) {
	                return rtnVol + "1\\";
	            }

	            // 가장 마지막 폴더를 찾습니다.
	            File lastFolder = null;
	            for (File folder : subfolders) {
	                if (lastFolder == null || folder.lastModified() > lastFolder.lastModified()) {
	                    lastFolder = folder;
	                }
	            }
	            // 폴더명을 출력합니다.
	            if (lastFolder != null) {
	            	 File[] files = lastFolder.listFiles();

	                
	            	if(files.length>=100) {

	                	rtnVol = rtnVol +  (Integer.parseInt(lastFolder.getName()) + 1) + "\\";
	            	}else {
	                	rtnVol = rtnVol +  lastFolder.getName() + "\\";
	            		
	            	}
	            }else {

	            	rtnVol =  rtnVol + "1\\";
	            }
	    		
	    		
	            
	            
	        }else {

	            System.out.println(map.get("rtnMsg"));  
	            rtnVol = (String)map.get("rtnMsg");
	        }
	        
	        
		

		return rtnVol;

	}

}