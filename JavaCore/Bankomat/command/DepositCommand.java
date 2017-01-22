package Bankomat.command;


import Bankomat.CashMachine;
import Bankomat.ConsoleHelper;

import Bankomat.CurrencyManipulator;
import Bankomat.CurrencyManipulatorFactory;
import Bankomat.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by vv_voronov on 05.08.2016.
 */
class DepositCommand implements Command
{
    private ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"deposit_en");
    @Override
    public void execute() throws InterruptOperationException
    {
      /*  ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        String [] money = ConsoleHelper.getValidTwoDigits(code);
        CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code).addAmount(Integer.parseInt(money[0]),Integer.parseInt(money[1]));
       // System.out.println(CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code).getTotalAmount());
*/
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        while (true) {
            try {
                String[] values = ConsoleHelper.getValidTwoDigits(code);
                CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
                int dom = Integer.valueOf(values[0]);
                int count = Integer.valueOf(values[1]);
                manipulator.addAmount(dom, count);
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), (dom * count), code));
                break;
            } catch (NumberFormatException ex) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
    }
    }

