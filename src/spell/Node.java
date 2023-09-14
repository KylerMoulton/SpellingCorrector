package spell;

public class Node implements INode{
    private int count;
    private final Node[] children;
    public Node() {
        this.count = 0;
        this.children = new Node[26];
    }
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
        return children;
    }


    //getValue
    //incrementValue
    //getChildren
}
