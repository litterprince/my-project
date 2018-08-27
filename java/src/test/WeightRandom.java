package test;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeightRandom<K,V extends Number> {
    private TreeMap<Double, K> weightMap = new TreeMap<Double, K>();

    public WeightRandom(List<Pair<K, V>> list) {
        for (Pair<K, V> pair : list) {
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey().doubleValue();//统一转为double
            this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
        }
    }

    public K random() {
        double randomWeight = this.weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }

    public static void main(String[] args) {
        List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
        list.add(new Pair<>("host1",1));
        list.add(new Pair<>("host2",3));
        list.add(new Pair<>("host3",6));
        WeightRandom weightRandom = new WeightRandom(list);
        System.out.println("random = " + weightRandom.random());
    }
}
