
//package cs321.btree;
package btree;

public class BTreeNode {
  
    private TreeObject[] keys;
    private BTreeNode[] childPointers;
    private int numKeys;
    private boolean isLeaf;
    private int degree;

    BTreeNode(int degree){
        this.keys = new TreeObject[2*degree - 1];
        this.childPointers = new BTreeNode[2*degree];
        this.numKeys = 0;
        this.isLeaf = true;
        this.degree = degree;

    }



    /**
     * getChildPointerAt - Takes a given index and returns the BTreeNode
     *      stored in that position of childPointers
     * @param idx
     * @return
     */
    BTreeNode getChildPointerAt(int idx){
        return this.childPointers[idx];
    }

    /**
     * setChildPointerAt - Takes a given index and a BTreeNode, sets the index
     *       of this BTreeNode.childPointers to that passed in value
     * @param idx
     * @param value
     */
    void setChildPointerAt(int idx, BTreeNode value){
        this.childPointers[idx] = value;
    }

    /**
     * setKeyAt - Takes a given index and a TreeObject to be set. Sets the 
     *      passed TreeObject into this BTreeNode.keys at position idx
     * @param idx
     * @param value
     */
    void setKeyAt(int idx, TreeObject value){
        this.keys[idx] = value;
    }

    /**
     * getKeyAt - Takes a given index and returns the key stored in
     *      this BTreeNode.keys.
     * @param idx
     * @return
     */
    TreeObject getKeyAt(int idx){
        return this.keys[idx];
    }

    /**
     * setNumKeys - Takes a value and set the numKeys field to that value
     * @param value
     */
    void setNumKeys(int value){
        this.numKeys = value;
    }

    /**
     * getNumKeys - returns the current number of keys that this node has
     * @return
     */
    int getNumKeys(){
        return this.numKeys;
    }

    /**
     * incrementNumKeys - increments the number of keys of this node
     */
    void incrementNumKeys(){
        this.numKeys++;
    }

    /**
     * setLeafStatus - sets the isKey field of this node
     *
     * @param newStatus
     */
    void setLeafStatus(boolean newStatus){
        this.isLeaf = newStatus;
    }
	
    /**
     * getLeafstatus - returns the current value of this node's isLeaf field
     *
     * @return
     */
    boolean getLeafStatus(){
        return this.isLeaf;
    }


}
