package test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ch_knight on 10/22/2016.
 */
public class Try {
    @Test
    public void TryA() {
        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();
        listA.add(1);
        listB.add(1);
        HashSet<List<Integer>> set = new HashSet<>();
        set.add(listA);
        set.add(listB);
        System.out.println(set.size());

    }
}
