package wechatserver;

import beans.Params;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingWorker;
import net.sf.json.JSONObject;
import tables.MemberManager;

public class ReceiveAndTransmitMessage extends SwingWorker<List<String>,String>{
    private Socket toClientSocket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private int clientCounts=0;//客户机数
    public ReceiveAndTransmitMessage() {
        
    }
    public ReceiveAndTransmitMessage(Socket toClientSocket,int clientCounts) {
        this.toClientSocket = toClientSocket;
        this.clientCounts = clientCounts;
    }

    @Override
    protected List<String> doInBackground() throws Exception {
        try {          
            // 创建绑定到套接字toClientSocket上的网络输入流与输出流
            in=new BufferedReader(new InputStreamReader(toClientSocket.getInputStream(),"UTF-8"));
            out=new PrintWriter(new OutputStreamWriter(toClientSocket.getOutputStream(),"UTF-8"),true);
            //5. 根据服务器协议，在网络流上进行读写操作
            String recvStr;
            
            while ((recvStr=in.readLine())!=null){ //只要客户机不关闭，则反复等待和接收客户机消息
                JSONObject job = JSONObject.fromObject(recvStr);//字符串转为JSON对象
                String type = job.getString("type");
                if (type.equalsIgnoreCase("LOGIN")) {
                    String name = job.getString("userName");
                    String password = job.getString("password");
                    System.out.println(name);
                    System.out.println(password);
                    int flag = MemberManager.Login(name, password);
                    out.println(String.valueOf(flag));//结果返回给客户端1，-1
                    if (flag == 1) {
                        Params.USERS.put(name, toClientSocket);
                    }
                } 
                if (type.equalsIgnoreCase("MSG")) {
                    out=new PrintWriter(new OutputStreamWriter(Params.USERS.get(job.getString("toPerson")).getOutputStream(),"UTF-8"),true);
                    System.out.println("接收到消息：" + recvStr);
                    out.println(recvStr);
                    out.flush();
                    System.out.println("已转发！");
                }
                //out.println(toClientSocket.getLocalSocketAddress()+ " 客户机编号: "+clientCounts+" Echo消息："+recvStr+" : ");
            }//end while   
           
            //远程客户机断开连接，线程释放资源
            if (in!=null) in.close();
            if (out!=null) out.close();
            if (toClientSocket!=null) toClientSocket.close(); 
            System.out.println("有一个连接断开了！");
        }catch (IOException ex) {}
        return null;
    }
    
    
}
