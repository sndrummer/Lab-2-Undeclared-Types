package edu.byu.yc.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.String;

public class Outer {
    public void refInner() {
        Inner inner = new Inner();
    }

    public class Inner {
    }

    SomeClass someClass;

    public void refOuterInner() {
        Outer.Inner inner = new Outer.Inner();
        String cheese = new String("Cheese");
    }
}