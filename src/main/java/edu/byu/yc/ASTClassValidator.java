package edu.byu.yc;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * @author Samuel Nuttall
 *
 * Helper class to store the results of QualifiedClassVisitor and pass them to the second visitor to
 * to see if the type is declared in the current environment
 */
public class ASTClassValidator {

    private ASTNode rootNode;

}
