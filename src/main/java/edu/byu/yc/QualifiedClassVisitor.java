package edu.byu.yc;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Set<Type> declaredTypes = new HashSet<>();
    private String packageName = "";
    private Map<String, String> simpleNameToFullyQualifiedName = new HashMap<>();

    @Override
    public boolean visit(ImportDeclaration node) {
        //logger.debug("Import Declaration Node: {}", node);
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        // logger.debug("Type Declaration Node: {}", node);
        String declarationName = node.getName().toString();
        logger.debug("Declaration Name {}", node.getName());

        Name name = node.getName();
        ASTNode parent = node.getParent();

        if (parent instanceof BodyDeclaration) {
            logger.debug("WE HAVE A WINNER!!!!!!!!!!!!!!");
            TypeDeclaration parentTD = (TypeDeclaration) parent;

            logger.debug("NAME of parent: {}", parentTD.getName());
            String parentQN = simpleNameToFullyQualifiedName.get(parentTD.getName());

            String qualifiedName = parentQN + "." + declarationName;
            simpleNameToFullyQualifiedName.put(declarationName, qualifiedName);

            logger.debug("DECLARATION IS: {}", qualifiedName);
        }


        boolean isPackageDeclaration = node.isPackageMemberTypeDeclaration();


        //logger.debug("isPackageDeclaration? {}", isPackageDeclaration);

        if (!packageName.equals("") && isPackageDeclaration) {
            String qualifiedName = packageName + "." + declarationName;
            simpleNameToFullyQualifiedName.put(declarationName, qualifiedName);
            logger.debug("DECLARATION IS: {}", qualifiedName);
        }





        //logger.debug();

       // ASTUtilities.logProperties(node);
        //ASTUtilities.logChildren(node,3);
        return true;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        //logger.debug("Package Declaration Node: {}", node);
        logger.debug("Package Name {}", node.getName());
        packageName = node.getName().toString();
        return super.visit(node);
    }

    /**
     * @return set of unqualifiedClassNames of the Import Declarations
     */
    public Set<String> getUnqualifiedClassNames() {
        return unqualifiedClassNames;
    }
}
