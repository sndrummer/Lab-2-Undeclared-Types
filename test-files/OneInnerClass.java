package edu.byu.yc.tests;


public class Outer {
    public void refInner() {
        Inner inner = new Inner();
        Outer.Inner a = new Outer.Inner();
    }

    public class Inner {
        Inner i = new Inner();
    }

    public void refOuterInner() {
        Outer.Inner inner = new Outer.Inner();
    }
}