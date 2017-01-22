package Bankomat.command;





import Bankomat.CashMachine;
import Bankomat.CurrencyManipulatorFactory;

import java.util.ResourceBundle;


/**
 * Created by vv_voronov on 05.08.2016.
 */
class InfoCommand implements Command
{
    private ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"info_en");
    @Override
    public void execute()
 /*   { boolean isEmpty=false;
        for(Map.Entry<String,CurrencyManipulator> map :CurrencyManipulatorFactory.getAllCurrencyManipulators().entrySet()){
            if(map.getValue().hasMoney())
                isEmpty =true;
        }
        if (isEmpty){
        for(Map.Entry<String,CurrencyManipulator> map : CurrencyManipulatorFactory.getAllCurrencyManipulators().entrySet()){
           // System.out.println(map.getKey()+ " - "+ map.getValue().getTotalAmount());
            System.out.printf("%s - %d%n",map.getKey(),map.getValue().getTotalAmount());
        }}
        else System.out.println("No money available.");


    }*/
    {
        System.out.println(res.getString("before"));
        boolean isEmpty=false;
        for(String manipulator: CurrencyManipulatorFactory.getAllCurrencyManipulators()){
            if(CurrencyManipulatorFactory.getManipulatorByCurrencyCode(manipulator).hasMoney())
                isEmpty =true;
        }
        if (isEmpty){
            for(String manipulator:CurrencyManipulatorFactory.getAllCurrencyManipulators()){
                System.out.printf("%s - %s%n",manipulator,CurrencyManipulatorFactory.getManipulatorByCurrencyCode(manipulator).getTotalAmount());
            }

        }else System.out.println(res.getString("no.money"));
    }
}
