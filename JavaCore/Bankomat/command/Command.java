package Bankomat.command;


import Bankomat.exception.InterruptOperationException;

/**
 * Created by vv_voronov on 05.08.2016.
 */
interface Command
{
    void execute() throws InterruptOperationException;

}
