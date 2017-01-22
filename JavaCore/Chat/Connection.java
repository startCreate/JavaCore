package Chat;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by vv_voronov on 22.09.2016.
 */
public class Connection implements Closeable
{
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException
    {
       this.socket = socket;

          out = new ObjectOutputStream(socket.getOutputStream());

        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Message message) throws IOException{
        synchronized (out){
        out.writeObject(message);}
    }

    public Message receive() throws IOException, ClassNotFoundException{
        Message receiveMessage = null;
        synchronized (in){
             receiveMessage = (Message) in.readObject();
        }
        return receiveMessage;
    }

    public SocketAddress getRemoteSocketAddress(){
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        return socketAddress;
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException
    {
        in.close();
        out.close();
        socket.close();
    }
}
