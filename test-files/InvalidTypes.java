package edu.byu.yc.tests;

import java.lang.String;
// Should contain 3 errors
public class Outer {
    public void refInner() {
        Inner inner = new Inner();
        Outer.Inner a = new Outer.Inner();
    }

    Samuel nuttall;


    public class Inner {
        String hello;
        Hello aaa;
    }

    public class Inner2 {
        Dinosaur meow;
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