package kB;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.io.StringReader;

public class serverTEst {

   public static void main(String[] args) {
      
      ServerSocket serverSocket = null;
      Socket socket = null;

      InputStream is = null;
      OutputStream os = null;
      DataInputStream dis = null;
      DataOutputStream dos = null;

      try {
         serverSocket = new ServerSocket();
         serverSocket.bind(new InetSocketAddress("192.168.1.44", 4040));

         while (true) {
            System.out.println("[ Listen ]");
            socket = serverSocket.accept();

            byte[] bytes = null;
            byte[] sub_bytes = null;
            String header = null;
            String message = null;
            
            //받을 데이터 소켓에 연결
            is = socket.getInputStream();
            dis = new DataInputStream(is);

            // 전체 데이터 길이 표시
            byte[] header_bytes = new byte[5];
            int readHeader = dis.read(header_bytes);
            //여기까지 그냥 따로 계산
            header = new String(header_bytes, 0, readHeader, "UTF-8");//여기서 문제 생김
            System.out.println("\n[ Data Length ] \n" + header);
           
            int length = Integer.parseInt(header);
            bytes = new byte[length];
              //length 수정 필요 (자릿수 때문에
            int readMessage = is.read(bytes);
            //null과 길이가 10이 안되는 위한것
            sub_bytes = new byte[500];
            // Get Interface ID
            byte[] IF_byte = new byte[10];             
            /* System.arraycopy(src, srcPos, dest, destPos, length); 
            src - 원본 배열 
            srcPos - 원본 배열의 복사 시작 위치 
            dest - 복사할 배열 
            destPost - 복사할 배열의 복사 시작 위치 
            length - 복사할 요소의 개수 */
            
            //이걸 안하면 따로 보낼때 써주고 그걸 읽고 여기로 받고 처리를 해야한다.
//            if(length==2) {
//            byte[] b = new byte[readHeader-length-1];//null bytes 처리 따로 필요	
//            System.arraycopy(by, 0, b, 0, b.length);
//            String b_str = new String(b,"UTF-8");
//            System.out.println("\n[Data is Null]\n"+b_str);
//            message = new String(bytes, 0, readMessage, "UTF-8");
//            System.out.println("\n[ Null Received ] \n" + message);
//            }else {
            System.arraycopy(sub_bytes, 0, IF_byte, 0, IF_byte.length);
            
            //IF_byte 크기가 10이므로 bytes는 10을 넘어야 한다. 따라서 length가 10부터 bytes에 10이 들어간다.
            if(length>10) {
            System.arraycopy(bytes, 0, IF_byte, 0, IF_byte.length);	
            }
            
            String IF_ID_str = new String(IF_byte, "UTF-8");
            System.out.println("\n[ Interface ID ] \n" + IF_ID_str);
            //메세지 내용만을 바란다
            message = new String(bytes, 0, readMessage, "UTF-8");
            System.out.println("\n[ Data Received ] \n" + message);
            
            //보내줄 데이터 소켓에 연결
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);
//            message = header + message;
            bytes = message.getBytes("UTF-8");

            dos.write(bytes);
            dos.flush();

            System.out.println("\n[ Data Send Success ] \n" + message + "\n");

//            dos.close();
//            os.close();
//            dis.close();
//            is.close();
//
//            socket.close();
         }

      } catch (Exception e) {
         e.printStackTrace();
//      } finally {
//         try {
//            if (dos != null) {
//               dos.close();
//            }
//            if (os != null) {
//               os.close();
//            }
//            if (dis != null) {
//               dis.close();
//            }
//            if (is != null) {
//               is.close();
//            }
//            if (socket != null) {
//               socket.close();
//               System.out.println("\n[ Socket closed ]\n");
//            }
//         } catch (Exception e2) {
//            // TODO: handle exception
//            e2.printStackTrace();
//         }
//
//      }

//      if (!serverSocket.isClosed()) {
//         try {
//            serverSocket.close();
//            System.out.println("\n[ Socket closed ]\n");
//         } catch (IOException e) {
//            e.printStackTrace();
//         }
      }
   }

}
