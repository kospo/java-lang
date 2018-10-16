package me.kospo.javalang.reflection;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public int t1;
    public List t2;

    public int getT1() {
        return t1;
    }

    public List getT2() {
        return t2;
    }

    public void setT1(int t11) {
        t1 = t11;
    }

    public void setT2(List t11) {
        t2 = t11;
    }

//    public int getTPIZDA() {
//        System.out.println("test");
//
//        return t2 + 1;
//    }

    @Override
    public String toString() {
        return "Test{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                '}';
    }
}
