package fr.istic.vv;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.NodeWithDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;


public class PrivateFieldsPrinter extends VoidVisitorWithDefaults<Void> {

    private ArrayList<MethodDeclaration> publicMethods = new ArrayList<MethodDeclaration>();
    private ArrayList<FieldDeclaration> privateFields = new ArrayList<FieldDeclaration>();

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        //if(!declaration.isPublic()) return;
        //System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
            //add public methods to list
            if(method.isPublic())
                publicMethods.add(method);
        }

        for(FieldDeclaration field : declaration.getFields()) {
            field.accept(this, arg);
            //add private fields to list
            if(field.isPrivate())
                privateFields.add(field);
        }
        
        // Printing nested types in the top level
        for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
    }

    //print private attributes
    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        if(!declaration.isPrivate()) return;
    }

    //get all private fields without public getter
    public void getPrivateAttributesNamesWithoutPublicGetter() {
       //print all private fields without public getter
         for(FieldDeclaration field : privateFields) {
              //get field name
              String fieldName = field.getVariable(0).getNameAsString();
              //check if there is a public getter
              boolean hasPublicGetter = false;
              for(MethodDeclaration method : publicMethods) {
                //check if method respect the getter naming convention
                if(method.getNameAsString().equals("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1))) {
                    //if the return type is the same as the field type
                    if(method.getTypeAsString().equals(field.getVariable(0).getTypeAsString())) {
                        hasPublicGetter = true;
                        break;
                    }
                }
              }
              //print field name if there is no public getter
              if(!hasPublicGetter)
                System.out.println(fieldName);
         }

    }

    //return a list of all private fields without public getter and their class name
    public ArrayList<String> getPrivateAttributesNamesWithoutPublicGetterAndTheirClassName() {
        ArrayList<String> privateAttributesNamesWithoutPublicGetterAndTheirClassName = new ArrayList<String>();
        //print all private fields without public getter
          for(FieldDeclaration field : privateFields) {
               //get field name
               String fieldName = field.getVariable(0).getNameAsString();
               //check if there is a public getter
               boolean hasPublicGetter = false;
               for(MethodDeclaration method : publicMethods) {
                 //check if method respect the getter naming convention
                 if(method.getNameAsString().equals("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1))) {
                     //if the return type is the same as the field type
                     if(method.getTypeAsString().equals(field.getVariable(0).getTypeAsString())) {
                         hasPublicGetter = true;
                         break;
                     }
                 }
               }
               //print field name if there is no public getter
               if(!hasPublicGetter)
                privateAttributesNamesWithoutPublicGetterAndTheirClassName.add(field.getVariable(0).getTypeAsString() + " " + fieldName);
          }
          return privateAttributesNamesWithoutPublicGetterAndTheirClassName;
     }

     //return a list of all private fields without public getter and their class name
}
