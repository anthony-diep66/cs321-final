package cs321.btree;
import java.util.ArrayList;


public class BTreeNode {
  
    private ArrayList<TreeObject> keys;
    private ArrayList<BTreeNode> childPointers;
    private int currentNumberOfKeysStored;
    private boolean isLeaf;
    private int degree;

    BTreeNode(int degree){
        this.keys = new ArrayList<TreeObject>();
        this.childPointers = new ArrayList<BTreeNode>();
        this.currentNumberOfKeysStored = 0;
        this.isLeaf = true;
        this.degree = degree;

    }

  
}
