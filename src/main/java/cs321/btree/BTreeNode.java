package cs321.btree;


public class BTreeNode {
  
    private TreeObject[] keys;
    private BTreeNode[] childPointers;
    private int currentNumberOfKeysStored;
    private boolean isLeaf;
    private int degree;

    BTreeNode(int degree){
        this.keys = new TreeObject[degree - 1];
        this.childPointers = new BTreeNode[degree];
        this.currentNumberOfKeysStored = 0;
        this.isLeaf = true;
        this.degree = degree;

    }

  
}
