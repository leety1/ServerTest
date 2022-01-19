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
            
            //���� ������ ���Ͽ� ����
            is = socket.getInputStream();
            dis = new DataInputStream(is);

            // ��ü ������ ���� ǥ��
            byte[] header_bytes = new byte[5];
            int readHeader = dis.read(header_bytes);
            //������� �׳� ���� ���
            header = new String(header_bytes, 0, readHeader, "UTF-8");//���⼭ ���� ����
            System.out.println("\n[ Data Length ] \n" + header);
           
            int length = Integer.parseInt(header);
            bytes = new byte[length];
              //length ���� �ʿ� (�ڸ��� ������
            int readMessage = is.read(bytes);
            //null�� ���̰� 10�� �ȵǴ� ���Ѱ�
            sub_bytes = new byte[500];
            // Get Interface ID
            byte[] IF_byte = new byte[10];             
            /* System.arraycopy(src, srcPos, dest, destPos, length); 
            src - ���� �迭 
            srcPos - ���� �迭�� ���� ���� ��ġ 
            dest - ������ �迭 
            destPost - ������ �迭�� ���� ���� ��ġ 
            length - ������ ����� ���� */
            
            //�̰� ���ϸ� ���� ������ ���ְ� �װ� �а� ����� �ް� ó���� �ؾ��Ѵ�.
//            if(length==2) {
//            byte[] b = new byte[readHeader-length-1];//null bytes ó�� ���� �ʿ�	
//            System.arraycopy(by, 0, b, 0, b.length);
//            String b_str = new String(b,"UTF-8");
//            System.out.println("\n[Data is Null]\n"+b_str);
//            message = new String(bytes, 0, readMessage, "UTF-8");
//            System.out.println("\n[ Null Received ] \n" + message);
//            }else {
            System.arraycopy(sub_bytes, 0, IF_byte, 0, IF_byte.length);
            
            //IF_byte ũ�Ⱑ 10�̹Ƿ� bytes�� 10�� �Ѿ�� �Ѵ�. ���� length�� 10���� bytes�� 10�� ����.
            if(length>10) {
            System.arraycopy(bytes, 0, IF_byte, 0, IF_byte.length);	
            }
            
            String IF_ID_str = new String(IF_byte, "UTF-8");
            System.out.println("\n[ Interface ID ] \n" + IF_ID_str);
            //�޼��� ���븸�� �ٶ���
            message = new String(bytes, 0, readMessage, "UTF-8");
            System.out.println("\n[ Data Received ] \n" + message);
            
            //������ ������ ���Ͽ� ����
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
