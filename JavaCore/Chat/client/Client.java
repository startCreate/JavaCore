package Chat.client;

import Chat.Connection;
import Chat.ConsoleHelper;
import Chat.Message;
import Chat.MessageType;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by vv_voronov on 24.09.2016.
 */
public class Client
{
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Enter server address");
        String line = ConsoleHelper.readString();
        return line;
    }

    protected int getServerPort(){
        ConsoleHelper.writeMessage("Enter server port");
        int port = ConsoleHelper.readInt();
        return port;
    }

    protected String getUserName(){
        ConsoleHelper.writeMessage("Enter your name");
        String name = ConsoleHelper.readString();
        return name;
    }

    protected boolean shouldSentTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        SocketThread socketThread = new SocketThread();
        return socketThread;
    }

    protected void sendTextMessage(String text){


        Message message = new Message(MessageType.TEXT,text);
        try
        {
            connection.send(message);
        }
        catch (IOException e)
        {
            ConsoleHelper.writeMessage("Error! Message not send");
            clientConnected=false;
        }
    }

    public void run(){
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        try
        {
            synchronized (this){
            wait();}
        }
        catch (InterruptedException e)
        {
            ConsoleHelper.writeMessage("Error while waiting for thread");
           return;
        }
        if (clientConnected)
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        else ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        String message = null;
        while (clientConnected){
            message=ConsoleHelper.readString();
            if (message.equals("exit"))
                return;
            if (shouldSentTextFromConsole())
                sendTextMessage(message);
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        client.run();
    }

    public class SocketThread extends Thread{

        @Override
        public void run()
        {
            String serverAdress = getServerAddress();
            int serverPort = getServerPort();
            try
            {
                Socket socket = new Socket(serverAdress,serverPort);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();

            }
            catch (IOException e)
            {
               notifyConnectionStatusChanged(false);
            }
            catch (ClassNotFoundException e)
            {
                notifyConnectionStatusChanged(false);
            }
        }

        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " connect to chat");
        }

        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " left chat");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException{
            while (true)
            {
                Message message = connection.receive();
                switch (message.getType())
                {
                    case NAME_REQUEST:
                    {
                        String nameUer = getUserName();
                        Message newMessage = new Message(MessageType.USER_NAME, nameUer);
                        connection.send(newMessage);
                        break;
                    }

                    case NAME_ACCEPTED:
                    {
                        notifyConnectionStatusChanged(true);
                        return;
                    }
                    default:
                        throw new IOException("Unexpected MessageType");
                }
            }
        }
        protected void clientMainLoop() throws IOException, ClassNotFoundException{
            while (true){
                Message messageFromServer = connection.receive();
            switch (messageFromServer.getType()){
                case TEXT :{
                processIncomingMessage(messageFromServer.getData());
                    break;
            }
                case USER_ADDED:{
                informAboutAddingNewUser(messageFromServer.getData());
                    break;
            }
                case USER_REMOVED:{
                informAboutDeletingNewUser(messageFromServer.getData());
                    break;
            }
                default: throw new IOException("Unexpected MessageType");
            }}
        }
    }
}
