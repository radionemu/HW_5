package hw_5;

import java.util.Comparator;

public class ListComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Word && o2 instanceof Word){
            int forward = (int)((Word) o1).corcount;
            int backward = (int)((Word) o2).corcount;
            if(forward > backward)return  1;
            else if (forward < backward) return  -1;
            else return 0;
        }
        return Integer.MAX_VALUE;
    }
}
