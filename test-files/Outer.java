package edu.byu.yc.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.String;

public class Outer {
    public void refInner() {
        Inner inner = new Inner();
        Outer.Inner a = new Outer.Inner();
        Outer.Poo p = new Outer.Poo();
        Outer.Poo.Cheese c = new Outer.Poo.Cheese();
    }

    public void aaa() {
        Poo p = new Poo();
        Outer.Poo ppp = new Outer.Poo();

        Outer.Poo.Cheese c = new Outer.Poo.Cheese();
    }


    public class Inner {
        OtherClass otherClass;
        String hello;
    }

    public class Poo {
        private String cheese1 = "";

        public class Cheese {
            private String cheese2 = "";

        }

    }

    private String cheese = "";


    SomeClass someClass;

    public void refOuterInner() {
        Outer.Inner inner = new Outer.Inner();
        String cheese = new String("Cheese");
        Poo poo = new Poo();
        poo.cheese1 = "sdf";
    }
}