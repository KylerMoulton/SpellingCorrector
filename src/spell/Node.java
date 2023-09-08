package spell;

public class Node implements INode{
    private int count;
    private Node children;
    @Override
    public int getValue() {
        return count;

    }
    @Override
    public void incrementValue() {
        count +=1;
    }

    @Override
    public INode[] getChildren() {
        return new INode[0];
    }


    //getValue
    //incrementValue
    //getChildren
}
