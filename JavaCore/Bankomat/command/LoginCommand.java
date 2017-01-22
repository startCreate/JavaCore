package Bankomat.command;


import Bankomat.CashMachine;
import Bankomat.ConsoleHelper;
import Bankomat.exception.InterruptOperationException;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by vv_voronov on 09.08.2016.
 */
class LoginCommand implements Command
{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"verifiedCards");
    private ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"login_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        System.out.println(res.getString("before"));
        System.out.println(res.getString("specify.data"));
        while (true){


            String catrd = ConsoleHelper.readString();
            if (catrd.length()!=12){
                System.out.println(res.getString("try.again.or.exit"));
                continue;}
            String pinCard = ConsoleHelper.readString();
            if (pinCard.length()!=4){
                System.out.println(res.getString("try.again.or.exit"));
                continue;}
            boolean isOk=true;
            Enumeration<String> cardProperties = validCreditCards.getKeys();

            while (cardProperties.hasMoreElements())
            {
                String login = cardProperties.nextElement();
                String pin = validCreditCards.getString(login);
                if (catrd.equals(login)&&pinCard.equals(pin)){
                    System.out.println(String.format(res.getString("success.format"),catrd));
                    isOk=true;break;}
                else{
                   // System.out.println("No such card");
                    isOk =false;}
            }


            if(isOk){
                break;
            }
        else System.out.println(String.format(res.getString("not.verified.format"),catrd));
            System.out.println(res.getString("try.again.with.details"));}

    }

    }

