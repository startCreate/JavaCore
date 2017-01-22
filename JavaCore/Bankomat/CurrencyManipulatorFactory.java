package Bankomat;



import java.util.Collection;

import java.util.HashMap;

import java.util.Map;

/**
 * Created by vv_voronov on 03.08.2016.
 */
public final class CurrencyManipulatorFactory
{
    private static Map<String,CurrencyManipulator> manipulators = new HashMap<>();
    private CurrencyManipulatorFactory()
    {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){

        for(Map.Entry<String,CurrencyManipulator> manipulator:manipulators.entrySet()){
            if(manipulator.getKey().equals(currencyCode))
                return manipulator.getValue();

        }
        CurrencyManipulator newManipulator = new CurrencyManipulator(currencyCode);
        manipulators.put(currencyCode,newManipulator);
        return newManipulator;

    }

    public static Collection<String> getAllCurrencyManipulators(){

        return  manipulators.keySet();
    }
}
