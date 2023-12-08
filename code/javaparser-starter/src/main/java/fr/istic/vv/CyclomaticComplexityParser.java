package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

public class CyclomaticComplexityParser extends VoidVisitorWithDefaults<Void> {
    
    MethodInfoNode infos = new MethodInfoNode();
    
    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        
        System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        String packageName = declaration.findCompilationUnit().get().getPackageDeclaration()
        .map(pkg -> pkg.getName().asString()).orElse("[None]");
        System.out.println(packageName);

        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
            System.out.println(computeComplexity(method));
            for( Parameter param : method.getParameters()) {
                param.accept(this, arg);
            }
        }
        // Printing nested types in the top level
        for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }
    }

    private int computeComplexity(MethodDeclaration method) {
            int complexity = 1;

            complexity+=method.findAll(IfStmt.class).size();
            complexity+=method.findAll(WhileStmt.class).size();
            complexity+=method.findAll(ForStmt.class).size();
            complexity+=method.findAll(ConditionalExpr.class).size();
            complexity+=method.findAll(DoStmt.class).size();

            return complexity;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        System.out.println("  " + declaration.getDeclarationAsString(true, true));
    }
    
    @Override
    public void visit(Parameter declaration, Void arg) {
        System.out.println("   " + declaration.getTypeAsString());
    }

}
