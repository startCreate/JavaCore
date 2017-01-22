package Chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by vv_voronov on 22.09.2016.
 */
public class Server
{

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message){
        try
        {
        for (Map.Entry<String, Connection> map: connectionMap.entrySet())
        {
                map.getValue().send(message);
        }}
        catch (IOException e)
        {
            ConsoleHelper.writeMessage("Message not receive");
        }
    }

    private static class Handler extends Thread{
       private Socket socket;

        public Handler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            SocketAddress remoteSocetAdress = socket.getRemoteSocketAddress();
            ConsoleHelper.writeMessage("Connection is successful "+remoteSocetAdress);
            String nameUser=null;

            try(Connection connection = new Connection(socket);){

                nameUser = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED,nameUser));
                sendListOfUsers(connection,nameUser);
                serverMainLoop(connection,nameUser);

            }
            catch (IOException e)
            {
               ConsoleHelper.writeMessage("Error in the exchange of data with the server");
            }
            catch (ClassNotFoundException e)
            {
                ConsoleHelper.writeMessage("Error in the exchange of data with the server");
            }

            if (nameUser!=null){
                connectionMap.remove(nameUser);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED,nameUser));
            }
            ConsoleHelper.writeMessage("Connection "+remoteSocetAdress+" close!");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{

            while (true)
            {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                if (message.getType()==MessageType.USER_NAME){
                   String nameUser = message.getData();
                        if (nameUser!=null&&!nameUser.isEmpty()&&!connectionMap.containsKey(nameUser)){
                            connectionMap.put(nameUser,connection);
                            connection.send(new Message(MessageType.NAME_ACCEPTED));
                            return nameUser;
                        }
                }
            }
        }

       private void sendListOfUsers(Connection connection, String userName) throws IOException{
            for (Map.Entry<String,Connection> map: connectionMap.entrySet() )
                {
                    String name = map.getKey();
                    if (!name.equals(userName)){
                    Message message = new Message(MessageType.USER_ADDED,name);
                    connection.send(message);
                    }
            }
       }
       private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
           while (true)
           {
               Message message = connection.receive();
               if (message.getType() == MessageType.TEXT)
               {
                   Message newMessageForUsers = new Message(MessageType.TEXT, userName + ": " + message.getData());
                   sendBroadcastMessage(newMessageForUsers);
               }
               else ConsoleHelper.writeMessage("Message is not a TEXT");
           }
       }
    }

    public static void main(String[] args)
    {
        ConsoleHelper.writeMessage("Input Port");
        Integer port = ConsoleHelper.readInt();

            try( ServerSocket serverSocket = new ServerSocket(port);){
                ConsoleHelper.writeMessage("Server start");

                while (true){
                    Socket socket = serverSocket.accept();
                    Handler handler = new Handler(socket);
                    handler.start();
                }

            }

        catch (Exception e)
        {
            ConsoleHelper.writeMessage("Error");
        }
    }
}
