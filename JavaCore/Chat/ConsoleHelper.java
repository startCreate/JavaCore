package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by vv_voronov on 22.09.2016.
 */
public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        String line = "";
        while (true){
        try
        {
            line = reader.readLine();
            break;
        }
        catch (IOException e)
        {
           writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            continue;
        }}

        return line;
    }

    public static int readInt(){
        int number=0;
        while (true){
            try
            {
        number = Integer.parseInt(readString());
                break;
            }catch (NumberFormatException e){
                System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
                continue;
            }}
        return number;
    }
}
