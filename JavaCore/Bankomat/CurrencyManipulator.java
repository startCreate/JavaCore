package Bankomat;



import Bankomat.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * Created by vv_voronov on 03.08.2016.
 */
public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer,Integer> denominations=new HashMap<>();

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count){
        if (denominations.containsKey(denomination))
            denominations.put(denomination,denominations.get(denomination)+count);
        else
        denominations.put(denomination,count);
    }

    public int getTotalAmount(){
        int amount = 0;
        for(Map.Entry<Integer,Integer> map : denominations.entrySet()){
            int nominal = map.getKey();
            int count = map.getValue();
            amount +=nominal * count;
        }
        return amount;
    }

    public boolean hasMoney(){
        if (!denominations.isEmpty())
            return true;
        else return false;
    }

    public boolean isAmountAvailable(int expectedAmount){
        if (expectedAmount<=getTotalAmount())
            return true;
        else return false;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {
        List<Integer> list = new ArrayList<>(denominations.keySet());
        Collections.sort(list,Collections.reverseOrder());
        Map<Integer, Integer> newMap = new TreeMap<>(Collections.reverseOrder());
        int amount = expectedAmount;
        int iter = 0;
        int lastEddedNominal = 0;
        while (true){

        for(int i =iter; i<list.size();i++)
        {
            for(int j =denominations.get(list.get(i)); j>0;j--)
            {
                if (amount >= list.get(i))
                {
                    amount -= list.get(i);
                    lastEddedNominal = list.get(i);
                    int count = 0;
                    if (newMap.containsKey(list.get(i)))
                        count = newMap.get(list.get(i));
                    newMap.put(list.get(i), count + 1);
              //      int nomKo = denominations.get(list.get(i));
               //     denominations.put(list.get(i),nomKo-1);

                } else break;
            }
        //    if(denominations.get(list.get(i))==0){
              //  denominations.remove(list.get(i));//
    //        list.remove(i);i--;
      //          if(amount==0)break;
      //     }

        }
        if (amount!=0){
            if (iter<list.size()){
                iter++;
                if(newMap.containsKey(lastEddedNominal))
                if(newMap.get(lastEddedNominal)>1){
                amount = amount+lastEddedNominal;
                int count =newMap.get(lastEddedNominal);
                newMap.put(lastEddedNominal,count-1);
                continue;}
                else {
                newMap.clear();
            continue;}
            }
        }
        if (amount!=0&&iter == list.size()){
           throw new NotEnoughMoneyException();}
            else{ if (amount==0){
            //удаляем из мапы банкомата банкноты которые выдали
            for(Map.Entry<Integer,Integer> map : newMap.entrySet()){
                if(denominations.containsKey(map.getKey())){
                    int countValue=denominations.get(map.getKey());
                    denominations.put(map.getKey(),countValue-map.getValue());
            //        System.out.println(map.getKey() + " - "+ map.getValue());
                }
            }
            Iterator<Map.Entry<Integer,Integer>> iterator = denominations.entrySet().iterator();
            while (iterator.hasNext()){
              Map.Entry<Integer,Integer> map = iterator.next();
                int nominal = map.getKey();
                int coutn = map.getValue();
                if(coutn==0)
                    iterator.remove();
            }
            break; }

        }
        }
 /*       System.out.println("в бакомате");
        for(Map.Entry<Integer,Integer> denom : denominations.entrySet()){
            System.out.println(denom.getKey() + " - " + denom.getValue());}
        System.out.println("выдано");
        for(Map.Entry<Integer,Integer> denom : newMap.entrySet()){
            System.out.println(denom.getKey() + " - " + denom.getValue());}
*/



        return newMap;
    }
}
