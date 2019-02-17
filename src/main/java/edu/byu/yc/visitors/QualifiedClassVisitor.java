package edu.byu.yc.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.yc.ASTClassValidator;

/**
 * @author Samuel Nuttall
 * <p>
 * Traverse ImportDeclaration and TypeDeclaration nodes and assemble a set of types that are declared
 * in the program. The PackageDeclaration node can be used to determine the fully qualified name of
 * each declared class. import statements make classes available without qualifiers, so be sure to
 * record the unqualified class names that are imported.
 */
public class QualifiedClassVisitor extends ASTVisitor {

    private static Logger logger = LoggerFactory.getLogger(QualifiedClassVisitor.class);
    private Set<String> unqualifiedClassNames = new HashSet<>();
    private Set<String> declaredTypes = new HashSet<>();
    private String packageName = "";
    private Map<String, String> simpleNameToFullyQualifiedName = new HashMap<>();
    private List<ASTClassValidator> classValidators = new ArrayList<>();

    /**
     * Stores the import Statements in declaredTypes set so that the valid classes to be used within
     * a file can be determined
     *
     * @param node ImportDeclaration node
     * @return true to visit children
     */
    @Override
    public boolean visit(ImportDeclaration node) {
        ASTNode parent = node.getParent();
        declaredTypes.add(node.getName().toString());
        return true;
    }

    /**
     * Visits TypeDeclaration nodes and stores them in the declaredTypes set so that if the type is
     * used elsewhere in the class it can be determined valid
     *
     * @param node TypeDeclaration node
     * @return true to visit all children
     */
    @Override
    public boolean visit(TypeDeclaration node) {
        String declarationName = node.getName().toString();
        //logger.info("DECLARATION NAME {}", declarationName);
        ASTNode parent = node.getParent();

        if (parent instanceof BodyDeclaration) {
            TypeDeclaration parentTD = (TypeDeclaration) parent;
            String parentQN = simpleNameToFullyQualifiedName.get(parentTD.getName().toString());
            String qualifiedName =  parentQN + "." + declarationName;
            simpleNameToFullyQualifiedName.put(declarationName, qualifiedName);
            declaredTypes.add(qualifiedName);
        }
        else {

            String qualifiedName =  packageName + "." + declarationName;
            simpleNameToFullyQualifiedName.put(declarationName, qualifiedName);
            declaredTypes.add(qualifiedName);
        }

        return true;
    }

    /**
     * For each class file, a new classValidator is added to simplify the resolving of the current
     * environment. declaredTypes, simpleNameToFullyQualifiedName, and packageName are all reset for
     * the next file to be traversed
     *
     * @param node
     */
    @Override
    public void endVisit(TypeDeclaration node) {
        ASTNode parent = node.getParent();
        if (!(parent instanceof BodyDeclaration)) {
            ASTClassValidator v = new ASTClassValidator(declaredTypes, node, packageName);
            classValidators.add(v);
            declaredTypes = new HashSet<>();
            simpleNameToFullyQualifiedName = new HashMap<>();
        }

    }


    /**
     * Visit the packageDeclaration node and get the current package name
     *
     * @param node
     * @return
     */
    @Override
    public boolean visit(PackageDeclaration node) {
        packageName = node.getName().toString();
        return true;
    }

    /**
     * @return set of unqualifiedClassNames of the Import Declarations
     */
    public Set<String> getUnqualifiedClassNames() {
        return unqualifiedClassNames;
    }

    public List<ASTClassValidator> getClassValidators() {
        return classValidators;
    }
}
