package Bankomat.command;


import Bankomat.CashMachine;
import Bankomat.ConsoleHelper;
import Bankomat.CurrencyManipulator;
import Bankomat.CurrencyManipulatorFactory;
import Bankomat.exception.InterruptOperationException;
import Bankomat.exception.NotEnoughMoneyException;


import java.util.Map;
import java.util.ResourceBundle;


/**
 * Created by vv_voronov on 05.08.2016.
 */
class WithdrawCommand implements Command
{
    private ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"withdraw_en");


    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator temp = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        int amount = 0;
        Map<Integer,Integer> map;// = new TreeMap<>(Collections.reverseOrder());
     //   ConsoleHelper.writeMessage(res.g);
        while(true){
            try
            {
            System.out.println(res.getString("specify.amount"));
            amount = Integer.parseInt(ConsoleHelper.readString());
            if (amount<=0){
                throw new NullPointerException();
                }
            if (!temp.isAmountAvailable(amount)){

                System.out.println(res.getString("not.enough.money"));
                continue;}

               map = temp.withdrawAmount(amount);
                int result=0;
                for (Map.Entry<Integer, Integer> m : map.entrySet()){
              //      ConsoleHelper.writeMessage("\t" + m.getKey() + " - " + m.getValue());
                    result+=m.getKey()*m.getValue();
                }
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"),result,code.toUpperCase()));
            }catch (NotEnoughMoneyException e){
                System.out.println(res.getString("exact.amount.not.available"));
                continue;
            }
            catch (NullPointerException e){
                System.out.println(res.getString("specify.not.empty.amount"));
                continue;
            }
            break;


        }

    }
}
