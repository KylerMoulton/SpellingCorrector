package spell;
public class Trie implements ITrie {
    private final Node root;

    public Trie() {
        this.root = new Node();
        this.nodeCount = 1;
    }

    private int wordCount;
    private int nodeCount;

    @Override
    public void add(String word) {
        String lowerWord = word.toLowerCase();
        StringBuffer remainingChar = new StringBuffer();
        remainingChar.append(lowerWord);
        add_Helper(root, remainingChar);
    }

    private void add_Helper(Node rootNode, StringBuffer remainingChar) {
        char curChar = remainingChar.charAt(0);
        if (rootNode.getChildren()[curChar - 'a'] == null) {
            rootNode.getChildren()[curChar - 'a'] = new Node();
            nodeCount += 1;
        }
        remainingChar.deleteCharAt(0);
        if (!remainingChar.isEmpty()) {
            add_Helper((Node) rootNode.getChildren()[curChar - 'a'], remainingChar);
        } else {
            if (rootNode.getChildren()[curChar - 'a'].getValue() == 0) {
                wordCount += 1;
            }
            rootNode.getChildren()[curChar - 'a'].incrementValue();
        }
    }

    @Override
    public INode find(String word) {
        String newWord = word.toLowerCase();
        if (newWord.isEmpty())return null;
        StringBuffer remainingChar = new StringBuffer();
        remainingChar.append(newWord);
        return find_Helper(root, remainingChar);
    }

    private INode find_Helper(Node rootNode, StringBuffer remainingChar) {
        char curChar = remainingChar.charAt(0);
        if (rootNode.getChildren()[curChar - 'a'] == null) {
            return null;
        }
        remainingChar.deleteCharAt(0);
        if (remainingChar.isEmpty()) {
            if (rootNode.getChildren()[curChar - 'a'].getValue() == 0) {
                return null;
            }
            return rootNode.getChildren()[curChar - 'a'];
        }

        return find_Helper((Node) rootNode.getChildren()[curChar - 'a'], remainingChar);
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
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
        for (int i = 0; i < root.getChildren().length; i++) {
            INode child = n.getChildren()[i];
            if (child != null) {
                char childLetter = (char) ('a' + i);
                curWord.append(childLetter);
                toString_Helper((Node) child, curWord, output);//cast child to Node from INode
                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        //is o == null? return false
        if (o == null) return false;
        //is o == this? return true
        if (o == this) return true;
        //do this and o have the same class?
        if (o.getClass() != this.getClass()) return false;
        Trie d = (Trie) o;
        //do this and d have the same wordCount and nodeCount?
        if (this.wordCount != d.wordCount) return false;
        if (this.nodeCount != d.nodeCount) return false;
        return equals_Helper(this.root, d.root);
    }

    //Helper function looks at specific nodes in the tree
    private boolean equals_Helper(Node n1, Node n2) {
        //Compare n1 and n2 to see if they are the same
        //Do n1 and n2 have the same count?
        if (n1 == null && n2 == null){
            return true;
        }
        if (n1 == null || n2 == null){
            return false;
        }
        if (n1.getValue() != n2.getValue()) return false;
        //Do n1 and n2 have non-null children in exactly the same indexes
        //Recurse on the children and compare the child subtrees
        //Recurse on the children and compare the child subtrees
        for (int i = 0; i <= 25; i++) {
            if (!equals_Helper((Node) n1.getChildren()[i], (Node) n2.getChildren()[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        //return Objects.hash(root, wordCount, nodeCount);
        int newNum = 0;
        for (int i = 0; i < 25; i++) {
            if (root.getChildren()[i] != null) {
                newNum += root.getChildren()[i].getValue() + i;
            }
        }
        return (newNum * wordCount * nodeCount * 71);
    }
}
