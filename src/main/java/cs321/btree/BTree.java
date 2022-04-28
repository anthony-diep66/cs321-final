package cs321.btree;

public class BTree{

    private int degree;
    private int lengthOfSubstring;      // 1<= k <= 31
    private BTreeNode root;                   // pointer should be of type "int"

    BTree(int degree, int substringLength){
            this.degree = degree;
            this.lengthOfSubstring = substringLength;
            this.root = null;
    }

    /**
     * (NEEDS TO BE TESTED) - I suspect there might be some indexing issues, especially 
     * when parentNode child pointers are being set.
     * split - Given a non-full parent BTreeNode, a full child node, and the index
     *      of the child node in parentNode's childPointers, this method will split the
     *      fullChildNode into two separate BTreeNodes and redirect the parentNode 
     *      child pointers to correct locations
     * @param parentNode
     * @param fullChildNode
     * @param idxOfChildNode
     */
    void split(BTreeNode parentNode, BTreeNode fullChildNode, int idxOfChildNode){
        BTreeNode newNode = new BTreeNode(this.degree);
        BTreeNode y = fullChildNode;
        if( y.getLeafStatus() == true ){
            newNode.setLeafStatus(true);
        }
        else{
            newNode.setLeafStatus(false);
        }
        newNode.setNumKeys(degree - 1);
        
        for(int i = 0; i < degree - 1; i++){
            newNode.setKeyAt(i, y.getKeyAt(i + degree));
        }

        if( y.getLeafStatus() == false ){
            for(int i = 0; i < degree; i++){
                
                newNode.setChildPointerAt(i, y.getChildPointerAt(i + degree));
            }
        }

        y.setNumKeys(degree - 1);

        for(int j = parentNode.getNumKeys(); j >= idxOfChildNode + 1; j--){
            parentNode.setChildPointerAt(j + 1, parentNode.getChildPointerAt(j));
        }


        parentNode.setChildPointerAt(idxOfChildNode + 1, newNode);
        for(int j = parentNode.getNumKeys() - 1; j >= idxOfChildNode; j--){
            parentNode.setKeyAt(j + 1, parentNode.getKeyAt(j));

        }

        parentNode.setKeyAt(idxOfChildNode, fullChildNode.getKeyAt(this.degree - 1));
        parentNode.incrementNumKeys();

    }

    /**
     * (NEEDS TO BE TESTED) - I'm 95% confident that this method is correct
     *
     * search - Recursively searches the BTree by iterating through start
     *      to see if it contains a matchiing targetKey. If it finds a match,
     *      this method increments the TreeObject's frequencyCount.
     *
     *      Returns the desired TreeObject (with the incremented frequencyCount) if
     *      this method finds a match and null if search miss
     * 
     * @param start
     * @param targetKey
     * @return
     */
    TreeObject search(BTreeNode start, String targetKey){
        int i = 0;
        TreeObject targetNode = new TreeObject(targetKey);
        while( i < start.getNumKeys() && targetNode.compareTo(start.getKeyAt(i)) == 1 ){
            i++;
        }
        if( i < start.getNumKeys() && targetNode.compareTo(start.getKeyAt(i)) == 0 ){
            start.getKeyAt(i).incrementFrequencyCount();
            return start.getKeyAt(i);
        } 
        if( start.getLeafStatus() == true ){
            return null;
        }
        else{
            return search(start.getChildPointerAt(i), targetKey);
        }
    }

    

	



}
