package sample ;

import java.util.ArrayList;
import java.util.Collection;

public class Filter<T>
{
    public ArrayList<T> filter(Collection<T> items, Specification<T> spec)
    {
        ArrayList<T> output = new ArrayList<>();
        for(T item : items ){
            if(spec.isSatisfied(item)){
                output.add(item);
            }
        }
        return output;
    }
}
