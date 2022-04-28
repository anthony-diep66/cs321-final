package cs321.btree;

public class BTree{

    private int degreeOfTree;
    private int lengthOfSubstring;      // 1<= k <= 31
    private BTreeNode root;                   // pointer should be of type "int"

    BTree(int degree, int substringLength){
            this.degreeOfTree = degree;
            this.lengthOfSubstring = substringLength;
            this.root = null;
    }

}
