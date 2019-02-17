package edu.byu.yc.tests;


public class Outer {
    public void refInner() {
        Inner inner = new Inner();
        Outer.Inner a = new Outer.Inner();
    }

    public class Inner {
        Inner newInner = new Inner();
    }

    public class Donkey {
        Donkey d = new Donkey();

        public class Cheese {
            Cheese c = new Cheese();

        }

    }

    public void refOuterInner() {
        Outer.Inner inner = new Outer.Inner();
    }
}