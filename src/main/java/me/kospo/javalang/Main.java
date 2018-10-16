package me.kospo.javalang;

import me.kospo.javalang.generics.CountMap;
import me.kospo.javalang.generics.CountMapImpl;

public class Main {
    public static void main(String[] args) {
        CountMap<Integer> map = new CountMapImpl<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);

        System.out.println(map);
        System.out.println(map.getCount(5));
        System.out.println(map.getCount(6));
        System.out.println(map.getCount(10));
    }
}