
package cs321.btree;
import java.util.*;
public class BTreeNode {
	
	int minimumDegree;
	TreeObject keys[];
	BTreeNode children[];
	int n;
	boolean isLeaf;
	int parent;
	
	public BTreeNode(int t, Boolean leaf) {
		this.minimumDegree = t;
		this.keys  = new TreeObject[2*minimumDegree - 1];
		this.children  = new BTreeNode[2*minimumDegree];
		this.isLeaf = leaf;
		this.n = 0;
	}
	
	
	public boolean isLeaf() {
		return isLeaf;
	}
	
	
	
}
