package test;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketTimeoutException;
public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    MyServer(int Port, ClientHandler handler){
        this.port = Port;
        this.ch = handler;
        this.stop = false;
    }
    public void start(){
    new Thread(() -> {
        try {
            runServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }).start();
    }
    public void close(){
        this.stop = true;
    }
    private void runServer()throws Exception {
        ServerSocket server=new ServerSocket(port);
        server.setSoTimeout(1000);
        while(!stop){
            try{
                Socket aClient=server.accept(); // blocking call
                try {
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.getInputStream().close();
                    aClient.getOutputStream().close();
                    aClient.close();
                } catch (IOException e) {/*...*/}
            }catch(SocketTimeoutException e) {/*...*/}
        }
        server.close();
    }
}