package edu.byu.yc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Samuel Nuttall
 */
public class TypeCheckerTests {

    private static final Logger log = LoggerFactory.getLogger(TypeCheckerTests.class);

    private final String root = System.getProperty("user.dir");

    private final File validJavaLangTypesFile = new File(new File(root, "test-files"), "ValidJavaLangTypes.java"); //test-files/ValidJavaLangTypes.java
    private final File invalidJavaLangTypesFile = new File(new File(root, "test-files"), "JavaLangTypesInv.java"); //test-files/JavaLangTypesInv.java

    private final File oneInnerClassFile = new File(new File(root, "test-files"), "OneInnerClass.java"); //test-files/OneInnerClass.java
    private final File multipleInnerClassesFile = new File(new File(root, "test-files"), "MultipleInnerClasses.java"); //test-files/MultipleInnerClasses.java

    private final File validTypesFile = new File(new File(root, "test-files"), "ValidTypes.java"); //test-files/ValidTypes.java
    private final File oneValidOneInvalidFile = new File(new File(root, "test-files"), "OneValidOneInvalidType.java"); //test-files/OneValidOneInvalidType.java
    private final File invalidTypesFile = new File(new File(root, "test-files"), "InvalidTypes.java"); //test-files/InvalidTypes.java

    private final String validJavaLangTypes = TypeChecker.readFile(validJavaLangTypesFile.getPath());
    private final String invalidJavaLangTypes = TypeChecker.readFile(invalidJavaLangTypesFile.getPath());

    private final String oneInnerClass = TypeChecker.readFile(oneInnerClassFile.getPath());
    private final String multipleInnerClasses = TypeChecker.readFile(multipleInnerClassesFile.getPath());

    private final String validTypes = TypeChecker.readFile(validTypesFile.getPath());
    private final String oneValidOneInvalid = TypeChecker.readFile(oneValidOneInvalidFile.getPath());
    private final String invalidTypes = TypeChecker.readFile(invalidTypesFile.getPath()); // Should contain 3 type errors

    /**
     * Tests that when java.lang classes are imported they can be used in the file without reporting
     * type errors.
     */
    @Test
    @DisplayName("Test Valid Java Lang Types")
    public void testValidJavaLangTypes() {
        assertTrue(TypeChecker.getTypeViolations(TypeChecker.parse(validJavaLangTypes)).isEmpty());
    }

    /**
     * Tests that when not Importing the String or Integer java.lang classes, two errors will be reported
     * when attempting to create one String and one Integer since it has not been explicitly imported.
     */
    @Test
    @DisplayName("Test Java Lang Types Not Imported")
    public void testInvalidJavaLangTypes() {
        assertEquals(2, TypeChecker.getTypeViolations(TypeChecker.parse(invalidJavaLangTypes)).size());
    }

    /**
     * Tests that an inner nested class can be instantiated/referenced in the scope of the file.
     */
    @Test
    @DisplayName("Test One Inner Class Valid")
    public void testOneInnerClassFile() {
        assertTrue(TypeChecker.getTypeViolations(TypeChecker.parse(oneInnerClass)).isEmpty());
    }

    /**
     * Tests multiple inner classes as well as multiple levels of nesting with valid java
     * declarations
     */
    @Test
    @DisplayName("Test Multiple Inner Classes Valid")
    public void testMultipleInnerClassFile() {
        assertTrue(TypeChecker.getTypeViolations(TypeChecker.parse(multipleInnerClasses)).isEmpty());
    }

    /**
     * Tests a mix of valid type definitions to test if the typeChecker works when using various
     * kinds of types within different environments
     */
    @Test
    @DisplayName("Test Complex Input")
    public void testComplexValidInput() {
        assertTrue(TypeChecker.getTypeViolations(TypeChecker.parse(validTypes)).isEmpty());
    }

    /**
     * Tests a mix of valid type definitions to test if the typeChecker works when using various
     * kinds of types within different environments
     */
    @Test
    @DisplayName("Test One Valid One Invalid Type")
    public void testOneValidOneInvalid() {
        assertEquals(1, TypeChecker.getTypeViolations(TypeChecker.parse(oneValidOneInvalid)).size());
    }


    /**
     * Tests a more complex mix of valid and invalid type declarations, there should be 3 errors
     * reported in total
     */
    @Test
    @DisplayName("Test Complex Invalid Input")
    public void testComplexInvalid() {
        assertEquals(3, TypeChecker.getTypeViolations(TypeChecker.parse(invalidTypes)).size());
    }




}
