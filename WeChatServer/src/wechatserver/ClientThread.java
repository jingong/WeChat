package wechatserver;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingWorker;

/**
 *
 * @author World
 */
public class ClientThread extends SwingWorker<List<String>,String> {
    private Socket toClientSocket=null;//会话套接字
    private BufferedReader in; //网络输入流
    private PrintWriter out; //网络输出流
    private int clientCounts=0;//客户机数
    public ClientThread(Socket toClientSocket,int clientCounts) { //构造函数
        this.toClientSocket=toClientSocket;
        this.clientCounts=clientCounts;
    }    
    @Override
    protected List<String> doInBackground(){
        try {          
            // 创建绑定到套接字toClientSocket上的网络输入流与输出流
            in=new BufferedReader(new InputStreamReader(toClientSocket.getInputStream(),"UTF-8"));
            out=new PrintWriter(new OutputStreamWriter(toClientSocket.getOutputStream(),"UTF-8"),true);
            //5. 根据服务器协议，在网络流上进行读写操作
            String recvStr;
            while ((recvStr=in.readLine())!=null){ //只要客户机不关闭，则反复等待和接收客户机消息
               publish(toClientSocket.getRemoteSocketAddress()+ " 客户机编号: "+clientCounts+" 消息："+recvStr+" ：\n"); //发布到process方法
               
               //按照echo协议原封不动回送消息
               //out.println(toClientSocket.getLocalSocketAddress()+ " 客户机编号: "+clientCounts+" Echo消息："+recvStr+" : ");
            }//end while   
            ServerUI.clientCounts--; //客户机总数减1
            //远程客户机断开连接，线程释放资源
            if (in!=null) in.close();
            if (out!=null) out.close();
            if (toClientSocket!=null) toClientSocket.close();             
        }catch (IOException ex) {}
        return null;
    } //end doInBackground
    @Override
    protected void process(List<String> message) {
        for (String str:message) {
            ServerUI.userArea.append(str);
        }
    }//end process
}
