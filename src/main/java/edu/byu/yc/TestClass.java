package edu.byu.yc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.String;

/**
 * @author Samuel Nuttall
 */
public class TestClass {

    /**
     * Simply use Type Declaration node and see if it is missing or not !!!
     * TYPE DECLARATION ( == this): [MISSING:SomeClass]
     */

    private static Logger logger = LoggerFactory.getLogger(TestClass.class);

    public class Outer {
        public void refInner() {
            Inner inner = new Inner();
        }

        public class Inner {
        }

        public void refOuterInner() {
            Outer.Inner inner = new Outer.Inner();
            String cheese = new String("Cheese");
        }
    }


}
