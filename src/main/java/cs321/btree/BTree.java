package cs321.btree;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BTree<E>{

	private int META_SIZE = 0;
	private long nextAddress = META_SIZE;
	private FileChannel file;
	private ByteBuffer buffer;
	
    private int degree;
    private int lengthOfSubstring;      // 1<= k <= 31
    private BTreeNode root;                   // pointer should be of type "int"

    BTree(int degree, int substringLength){
            this.degree = degree;
            this.lengthOfSubstring = substringLength;
            this.root = null;
    }
	
	// Fill up TreeObject keys
	// If filled -> make a new BTreeNode and add to children 
	
	public void insertNonful(BTreeNode node, Long key) {
		int i = node.n;
		
		if(node.isLeaf()) {
			while(i>= 1 && key < node.keys[i].key) {
				node.keys[i+1] = node.keys[i];
				i--;
			}
			
			node.keys[i+1].key = key;
			node.n++;
			DiskWrite(node);
		} else {
			while(i >= 1 && key < node.keys[i].key) {
				i--;
			}
			
			i++;
			DiskRead(node.children[i]);
			
			if(node.children[i].n == 2*t - 1) {
				Split(node.children[i]);
				if(key > node.keys[i].key) {
					i++;
				}
			}
			
			insertNonful(node.children[i],key);
		}
	}
	
	public void DiskWrite(BTreeNode node) throws IOException{
		
	}
	
	public void DiskRead(long address) throw IOException{
		
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

    void print(BTreeNode startNode){
	if( startNode != null ){
		if( startNode.getLeafStatus() == true ){
		    for(int i = 0; i < startNode.getNumKeys(); i++){
			System.out.println(startNode.getChildPointerAt(i).toString());
		    }
		}
		else{
		    int i;
		    for(i = 0; i < startNode.getNumKeys(); i++){
			print(startNode.getChildPointerAt(i));
		    }

		    System.out.println(startNode.getChildPointerAt(i).toString());
		}
	}

    }
 


}

