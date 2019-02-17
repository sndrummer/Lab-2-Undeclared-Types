package edu.byu.yc.tests;

import java.lang.String;
import java.lang.Integer;

public class Outer {
    public void refInner() {
        Inner inner = new Inner();
        Outer.Inner a = new Outer.Inner();
        Integer b = 12;
    }


    public class Inner {
        String hello;
    }

    public class Inner2 {
        private String cheese1 = "";

        public class Cheese {
            private String cheese2 = "";

        }
    }

    private String cheese = "";

    public void refOuterInner() {
        Outer.Inner inner = new Outer.Inner();
        String cheese = new String("Cheese");
    }
}