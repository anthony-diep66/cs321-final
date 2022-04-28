package cs321.btree;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BTree<E>{

	private int META_SIZE = 0;
	private long nextAddress = META_SIZE;
	private FileChannel file;
	private ByteBuffer buffer;
	private BTreeNode root;
	private int nodeSize = 1;
	private int t;
	private long rootAddress;
	public BTree(int t) {
		root = new BTreeNode(t,true);
		this.t = t;
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

}

