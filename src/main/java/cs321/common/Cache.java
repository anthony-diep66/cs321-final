package cs321.common;
import java.util.Iterator;
import java.util.LinkedList;
import cs321.btree.BTreeNode;

public class Cache {
	private BTreeNode data;
	private int offset;
	public BTreeCache BTC;
	
	public class BTreeCache implements Iterable<BTreeNode> {

	    private final int MAX_SIZE;
	    private int numHits, numMisses;

	    private LinkedList<BTreeNode> list;

	    public BTreeCache(int MAX_SIZE){
	        this.MAX_SIZE = MAX_SIZE;
	        list = new LinkedList<BTreeNode>();
	    }
	    
	    public BTreeNode add(BTreeNode o,int offset){
	    	BTreeNode rnode = null;
	        if (isFull()){
	            rnode = list.removeLast();
	        }
	        list.addFirst(o);
	        return rnode;
	    }
	    
	    public void clearCache(){
	        list.clear();
	    }
	    
	    public BTreeNode readNode(int offset) {
	    	for (BTreeNode n : list) {
	    		if (n.getOffset() == offset) {
	    			list.remove(n);
	    			list.addFirst(n);
	    			increaseNumHits();
	    			return n;
	    		}
	    	}
	    	increaseNumMisses();
	    	return null;
	    }
	    
	    public int getNumReferences() {
	        return numHits + numMisses;
	    }

	    private void increaseNumHits() {
	        numHits++;
	    }
	    
	    private void increaseNumMisses() {
	        numMisses++;
	    }
	    
	    public int getNumHits() {
	        return numHits;
	    }
	    
	    public int getNumMisses() {
	        return numMisses;
	    }
	 
	    public double getHitRatio() {
	        double ratio = ((double) getNumHits()) / getNumReferences();
	        return ratio;
	    }
	    
	    public int getSize() {
	        return list.size();
	    }
	    
	    public boolean isFull(){
	        return getSize() == MAX_SIZE;
	    }

		@Override
		public Iterator<BTreeNode> iterator() {
			return list.iterator();
		}
	}                             
	
	public Cache(BTreeNode data, int offset, int max) {
		this.data = data;
		this.offset = offset;
		this.BTC = new BTreeCache(max);
	}
	
	public BTreeNode getData() {
		return data;
	}
	
	public int getOffset() {
		return offset;
	}
}
