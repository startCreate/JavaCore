package Bankomat;

import Bankomat.command.CommandExecutor;
import Bankomat.exception.InterruptOperationException;
import Bankomat.exception.NotEnoughMoneyException;

import java.util.Locale;

/**
 * Created by vv_voronov on 03.08.2016.
 */
public class CashMachine
{
    public static final String RESOURCE_PATH = "Bankomat.resources.";
    public static void main(String[] args) throws NotEnoughMoneyException
    {
        try
        {
            Locale.setDefault(Locale.ENGLISH);
    //        CommandExecutor.execute(Operation.LOGIN);

            Operation operation = null;
            do
            {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);
        }
        catch (InterruptOperationException e)
        {
            ConsoleHelper.printExitMessage();
        }

    }
}
