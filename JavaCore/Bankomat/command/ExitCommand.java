package Bankomat.command;


import Bankomat.CashMachine;
import Bankomat.ConsoleHelper;
import Bankomat.exception.InterruptOperationException;


import java.util.ResourceBundle;

/**
 * Created by vv_voronov on 05.08.2016.
 */
class ExitCommand implements Command
{
    private ResourceBundle res =ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"exit_en");
    @Override
    public void execute() throws InterruptOperationException
    {
        System.out.println(res.getString("exit.question.y.n"));
        if (ConsoleHelper.readString().equalsIgnoreCase(res.getString("yes")))
        ConsoleHelper.writeMessage(res.getString("thank.message"));
    //    else ConsoleHelper.askOperation();

      //4 exit =

    }
}
