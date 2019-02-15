package edu.byu.yc;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Samuel Nuttall
 * <p>
 * Enforces Linting rule:
 * Switch statements may contain no more than six cases (not counting default).
 */
public class SwitchStatementVisitor extends ASTVisitor {

    private static Logger logger = LoggerFactory.getLogger(SwitchStatementVisitor.class);
    private Set<String> violations = new HashSet<>();

    /**
     * Checks if there are more than six switch statement cases, adds violations to HashMap
     *
     * @param node A Switch Statement ASTNode to be analyzed
     * @return true after visiting a Switch Statement
     */
    @Override
    public boolean visit(SwitchStatement node) {
        List statements = node.statements();
        if (statements == null)
        {
            return true;
        }
        int caseAmt = 0;
        if (!statements.isEmpty() && statements.get(0) instanceof Statement) {
            for (Statement statement : (List<Statement>) statements) {
                if (statement.getClass() == SwitchCase.class && !isDefaultStatement(statement)) {
                    caseAmt++;
                }
                if (caseAmt > 6) {
                    logger.error("There are more than 6 cases in this switch statement:\n" +
                            "{}", statement.getParent());
                    violations.add(node.toString());
                    return true;
                }
            }

        }
        return true;
    }

    /**
     * Checks an AST statement to see if it is a default statement in a switch statement or not
     *
     * @param statement A statement is passed in to determine whether it is a default statement or not
     * @return boolean true or false whether it is a default statement or not
     */
    private boolean isDefaultStatement(Statement statement) {
        List list = statement.structuralPropertiesForType();
        statement.getProperty("expression");
        StructuralPropertyDescriptor propertyDescriptor = (StructuralPropertyDescriptor) list.get(0);
        if (propertyDescriptor.getId().equals("expression")) {
            Object child = statement.getStructuralProperty(propertyDescriptor);
            return (child == null && statement.toString().contains("default :"));
        }
        return false;
    }


    /**
     * @return set of violations of the Switch Statement linting rule
     */
    public Set<String> getViolations() {
        return violations;
    }
}


