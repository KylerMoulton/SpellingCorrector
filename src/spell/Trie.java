package spell;

import java.util.Objects;

public class Trie implements ITrie{
    private Node root;
    private int wordCount;
    private int nodeCount;
    @Override
    public void add(String word) {
        wordCount +=1;
    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root, curWord, output);

        return output.toString();
    }
    private void toString_Helper(Node n, StringBuilder curWord, StringBuilder output) {
        if (n.getValue() > 0) {
            //append the node's word to the output
            output.append(curWord.toString());
            output.append("\n");
        }
        for (int i = 0;i < root.getChildren().length; i++) {
            INode child = n.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                curWord.append(childLetter);
                toString_Helper((Node) child, curWord, output);//cast child to Node from INode
                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        //is o == null?
        if (o == null) return false;
        //is o == this?
        if (o == this) return true;
        //do this and o have the same class?
        if (o.getClass() != this.getClass()) return false;
        Trie d = (Trie)o;
        //do this and d have the same wordCount and nodeCount?
        return equals_Helper(this.root, d.root);
    }
    private boolean equals_Helper(Node n1, Node n2) {
        //Compare n1 and n2 to see if they are the same
            //Do n1 and n2 have the same count?
            if (n1.getValue() != n2.getValue()) return false;
            //Do n1 and n2 have non-null children in exactly the same indexes
        //Recurse on tbe children and compare the child subtrees
            return equals_Helper((Node) n1.getChildren()[0],(Node)n2.getChildren()[0]);

    }

    @Override
    public int hashCode() {
        //return Objects.hash(root, wordCount, nodeCount);
        return (wordCount*nodeCount*root.getChildren().length*71);
    }
}
