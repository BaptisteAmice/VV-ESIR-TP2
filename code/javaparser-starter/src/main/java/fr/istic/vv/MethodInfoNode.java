package fr.istic.vv;

import java.util.ArrayList;

public class MethodInfoNode{
    private EnumTypeInfo type = EnumTypeInfo.ROOT; //type of information (exemple package)
    private String value = "none";
    private ArrayList<MethodInfoNode> childs;
    private int nbChilds = 0;

    public MethodInfoNode() {
        childs = new ArrayList<>();
    }
    public MethodInfoNode(EnumTypeInfo typeInfo, String value) {
        this.value = value;
        this.type = typeInfo;
        childs = new ArrayList<>();
    }

    public EnumTypeInfo getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void addChild(MethodInfoNode m) {
        childs.add(m);
        nbChilds++;
    }

    public MethodInfoNode getChild(int i) {
        if (nbChilds <= 0)
            return null;
        return childs.get(i);
    }

    public ArrayList<MethodInfoNode> getAllChilds() {
        if (nbChilds <= 0)
            return null;
        return childs;
    }

    public int getNbChilds() {
        return nbChilds;
    }

}
