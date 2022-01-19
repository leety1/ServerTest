package kB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class testServer3 {

   public static void main(String[] args) {
      
      ServerSocket serverSocket = null;
      Socket socket = null;

      InputStream is = null;
      OutputStream os = null;
      DataInputStream dis = null;
      DataOutputStream dos = null;

      try {
         serverSocket = new ServerSocket();
         serverSocket.bind(new InetSocketAddress("127.0.0.1", 4040));

         while (true) {
            System.out.println("[ Listen ]");
            socket = serverSocket.accept();

            byte[] bytes = null;
      
            String header = null;
            String message = null;
            
            //받을 데이터 소켓에 연결
            is = socket.getInputStream();
            dis = new DataInputStream(is);
            
            
//            int leng = dis.readByte();
//            byte[] b = new byte[leng];
//            int cnt = dis.read(b);
//            while(cnt!=leng) {
//            	int r = dis.read(b,cnt,leng-cnt);
//            	if(r == -1) {
//            		throw new IOException("String Read ERROR");
//            		}else {
//            			cnt+=r;
//            		}
//            	}
     
            // 전체 데이터 길이 표시
            byte[] header_bytes = new byte[5];
            int readHeader = dis.read(header_bytes);
           
            header = new String(header_bytes, 0, readHeader, "UTF-8");//여기서 문제 생김
            System.out.println("\n[ Data Length ] \n" + header);
           
            int length = Integer.parseInt(header);
            //int length = Integer.parseInt(header);
            bytes = new byte[length];   //length 수정 필요 (자릿수 때문에
            
            int readMessage = dis.read(bytes);
            //String s = new String(header_bytes,0,readMessage,"UTF-8");
            // Get Interface ID
            byte[] IF_byte = new byte[10];             
            
            //null에 대한 처리도 필요할듯?
            /* System.arraycopy(src, srcPos, dest, destPos, length); 
            src - 원본 배열 
            srcPos - 원본 배열의 복사 시작 위치 
            dest - 복사할 배열 
            destPost - 복사할 배열의 복사 시작 위치 
            length - 복사할 요소의 개수 */
            if(bytes.length == 2) {
            byte[] b = new byte[readHeader-length-2];//null bytes 처리 따로 필요	
            System.arraycopy(bytes, 0, b, 0, b.length);
            String b_str = new String(b,"UTF-8");
            System.out.println("\n[Data is Null]\n"+b_str);
            message = new String(bytes, 0, readMessage, "UTF-8");
            System.out.println("\n[ Null Received ] \n" + message);}
            else if(bytes.length <10) {
            	
            byte[] b = new byte[readHeader-length-2];	
            System.arraycopy(bytes, 0, b, 0, b.length);
            String IF_ID_str = new String(IF_byte, "UTF-8");
            System.out.println("\n[ Interface ID ] \n" + IF_ID_str);

            message = new String(bytes, 0, readMessage, "UTF-8");
            System.out.println("\n[ Data Received ] \n" + message);
            
            }else if(bytes.length>10) {
            System.arraycopy(bytes, 0, IF_byte, 0, IF_byte.length);
            String IF_ID_str = new String(IF_byte, "UTF-8");
            System.out.println("\n[ Interface ID ] \n" + IF_ID_str);

            message = new String(bytes, 0, readMessage, "UTF-8");
            System.out.println("\n[ Data Received ] \n" + message);	
            	
            }
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
   
   //변환 함수 사용 가능
   public String byteArrayToBinaryString(byte[] b){
	    StringBuilder sb=new StringBuilder();
	    for(int i=0; i<b.length; ++i){
	        sb.append(byteToBinaryString(b[i]));
	    }
	    return sb.toString();
	}

	public String byteToBinaryString(byte n) {
	    StringBuilder sb = new StringBuilder("00000000");
	    for (int bit = 0; bit < 8; bit++) {
	        if (((n >> bit) & 1) > 0) {
	            sb.setCharAt(7 - bit, '1');
	        }
	    }
	    return sb.toString();
	}

	public byte[] binaryStringToByteArray(String s){
	    int count=s.length()/8;
	    byte[] b=new byte[count];
	    for(int i=1; i<count; ++i){
	        String t=s.substring((i-1)*8, i*8);
	        b[i-1]=binaryStringToByte(t);
	    }
	    return b;
	}

	public byte binaryStringToByte(String s){
	    byte ret=0, total=0;
	    for(int i=0; i<8; ++i){        
	        ret = (s.charAt(7-i)=='1') ? (byte)(1 << i) : 0;
	        total = (byte) (ret|total);
	    }
	    return total;
	}

}