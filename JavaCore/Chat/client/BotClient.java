package Chat.client;

import Chat.ConsoleHelper;


import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vv_voronov on 26.09.2016.
 */
public class BotClient extends Client
{

    @Override
    protected SocketThread getSocketThread()
    {
        return new BotSocketThread();
    }

    @Override
    protected String getUserName()
    {
        return "date_bot_"+ThreadLocalRandom.current().nextInt(0,99);
    }

    @Override
    protected boolean shouldSentTextFromConsole()
    {
        return false;
    }

    public class BotSocketThread extends SocketThread{
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException
        {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message)
        {
            ConsoleHelper.writeMessage(message);

            if (message!=null){
            if (message.contains(":"))
            {
                SimpleDateFormat format = null;
                String name = message.substring(0, message.indexOf(":"));
                String text = message.substring(message.indexOf(":")+2);
                String textForMessage = null;
                switch (text){
                    case "дата":{
                        format = new SimpleDateFormat("d.MM.YYYY");

                        break;
                    }
                    case "день":{
                        format = new SimpleDateFormat("d");
                        break;
                    }
                    case "месяц":{
                        format = new SimpleDateFormat("MMMM");
                        break;
                    }
                    case "год":{
                        format = new SimpleDateFormat("YYYY");
                        break;
                    }
                    case "время":{
                        format = new SimpleDateFormat("H:mm:ss");
                        break;
                    }
                    case "час":{
                        format = new SimpleDateFormat("H");
                        break;
                    }
                    case "минуты":{
                        format = new SimpleDateFormat("m");
                        break;
                    }
                    case "секунды":{
                        format = new SimpleDateFormat("s");
                        break;
                    }
                    default:return;


                }

                    sendTextMessage(String.format("Информация для %s: %s",name,format.format(new GregorianCalendar().getTime())));

            }
        }}
    }

    public static void main(String[] args)
    {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
