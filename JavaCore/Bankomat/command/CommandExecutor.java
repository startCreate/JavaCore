package Bankomat.command;



import Bankomat.Operation;
import Bankomat.exception.InterruptOperationException;
import Bankomat.exception.NotEnoughMoneyException;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by vv_voronov on 05.08.2016.
 */
public class CommandExecutor
{


    private CommandExecutor()
    {
    }

    private static Map<Operation, Command> operationCommandMap;
    static
    {
        operationCommandMap=new HashMap<>();
        operationCommandMap.put(Operation.LOGIN,new LoginCommand());
        operationCommandMap.put(Operation.INFO, new  InfoCommand());
        operationCommandMap.put(Operation.DEPOSIT, new DepositCommand());
        operationCommandMap.put(Operation.WITHDRAW, new WithdrawCommand());
        operationCommandMap.put(Operation.EXIT, new ExitCommand());

    }
    public static final void execute(Operation operation) throws InterruptOperationException, NotEnoughMoneyException
    {

        operationCommandMap.get(operation).execute();
    }
}


