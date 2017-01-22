package Bankomat;

import Bankomat.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;


/**
 * Created by vv_voronov on 03.08.2016.
 */
public class ConsoleHelper
{
    private static ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"common_en");
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException
    {

        String line="";
        try
        {
            line = reader.readLine();

        }
        catch (IOException e)
        {
            writeMessage("Invalid data");
        }
        if (line.equalsIgnoreCase("exit"))
            throw new InterruptOperationException();
        return line;
    }

    public static String askCurrencyCode() throws InterruptOperationException
    {
        System.out.println(res.getString("choose.currency.code"));
        String code = null;
        boolean valid = true;
        while (valid)
        {
            code = readString();
            if (code.length() != 3)
                System.out.println("Error");
            else valid = false;
        }
        return code.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException
    {
      /*  System.out.println(String.format(res.getString("choose.denomination.and.count.format")));
        boolean valid = true;
        String[] money=new String[2];
        int nominal=0;
        int count =0;
        while (valid)
        {
        try
        {


            String line = readString();
            money = line.split("\\s+");
            nominal = Integer.parseInt(money[0]);
            count = Integer.parseInt(money[1]);
            //           System.out.println(nominal +" "+count);
            if (nominal > 0 && count > 0)
                valid = false;
            else System.out.println("ne vernuq vvjd<0");
        }
            catch (IllegalArgumentException e)
            {
                System.out.println("Povtorite vvod");
            }
        }*/
       String[] money=new String[2];
        String line="";
        System.out.println(String.format(res.getString("choose.denomination.and.count.format"),line));
        line = readString();
        int nominal=0;
        int count =0;
        money=line.split("\\s+");
        nominal = Integer.parseInt(money[0]);
        count = Integer.parseInt(money[1]);
        //           System.out.println(nominal +" "+count);
        if (nominal <= 0 || count <= 0)
            throw new NumberFormatException();
     //  else System.out.println("ne vernuq vvjd<0");
  //  }
        return money;
    }

    public static Operation askOperation() throws InterruptOperationException
    {

        ConsoleHelper.writeMessage(res.getString("choose.operation") + " \n" +
               "1 - " + res.getString("operation.INFO") + "\n" +
                "2 - " + res.getString("operation.DEPOSIT") + "\n" +
              "3 - " +  res.getString("operation.WITHDRAW") + "\n" +
              "4 - " +  res.getString("operation.EXIT") );
        Operation result=null;
        while (true){

            result=Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));
            break;


        }

        return result;
    }

    public static void printExitMessage(){
        System.out.println(res.getString("the.end"));
    }


}
