package cs321.common;

import cs321.btree.TreeObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * Turns a BTree into a binary file, and converts that file back into
 * a fully functioning BTree.
 *
 */
public class DiskReadWrite {

	private int METADATA_SIZE = Long.BYTES + Integer.BYTES * 2;
    private long nextDiskAddress = METADATA_SIZE;
    private FileChannel file;
    private ByteBuffer buffer;
    private int nodeSize;
    private int degree;
    private int subsequenceLength;
    
    private final int MAX_KEYS;
    private final int MAX_CHILDREN;

    private long rootAddress = METADATA_SIZE; // offset to the root node
    private Node root; // the root node in memory
    
    /**
     * Constructor for Disk Read/Write. Needs a properly formatted filename from
     * GeneBankCreateBTree, which contains degree and subsequenceLength in filename.
     * Will parse out meta data from filename, then will either create a new file or 
     * open an existing file, where if file already exists it will read in extra metadata.
     * @param filename with proper format
     * @throws IOException
     */
    public DiskReadWrite(File filename) throws IOException {
    	char[] pathArray = filename.getPath().toCharArray();
    	StringBuilder stringBuffer = new StringBuilder();
    	int foundArgs = 0;
    	for (int i = pathArray.length - 1; i > 0; i--) {
    		if (pathArray[i] == '.') {
    			if (foundArgs == 0) {
    				stringBuffer.reverse();
    				degree = Integer.parseInt(stringBuffer.toString());
    				stringBuffer = new StringBuilder();
    				foundArgs++;
    				continue;
    			} else if (foundArgs == 1) {
    				stringBuffer.reverse();
    				subsequenceLength = Integer.parseInt(stringBuffer.toString());
    				break;
    			}
    		} else {
    			stringBuffer.append(pathArray[i]);
    		}
    	}
    	
    	MAX_KEYS = 2 * degree - 1;
    	MAX_CHILDREN = 2 * degree;
    	
    	Node r = new Node(null);
    	nodeSize = r.getDiskSize();
    	buffer = ByteBuffer.allocateDirect(nodeSize);
    	
    	try {
    		if (!filename.exists()) {
    			filename.createNewFile();
    			RandomAccessFile dataFile = new RandomAccessFile(filename, "rw");
    			file = dataFile.getChannel();
    			writeMetaData();
    		} else {
    			RandomAccessFile dataFile = new RandomAccessFile(filename, "rw");
                file = dataFile.getChannel();
                readMetaData();
                root = diskRead(rootAddress);
    		}
    	} catch (FileNotFoundException e) {
            System.err.println(e);
    	}
    }
    
    /**
     * Node representation of a TreeObject. To store a TreeObject in a binary file,
     * we only need to know a couple of things - the number of keys, the keys themselves,
     * the number of children, the addresses for the children in the binary file, and 
     * the parent for the node. This class acts as a translator between binary and object
     * representations of a BTree,
     *
     */
    private class Node {
    	private long address;
    	private Long[] keys;
    	private int numKeys;
    	private Integer[] frequencies;
    	private Long[] children;
    	private int numChildren;
    	private Long parent;
    	
        	
    	/* Generic constructor for Node */
    	public Node(TreeObject object) {
    		if (object != null) {
	    		this.numKeys = object.getNumKeys();
	    		this.keys = new Long[numKeys];
	    		this.frequencies = new Integer[numKeys];
	    		for (int i = 0; i < numKeys; i++) {
	    			keys[i] = object.getKeyAtIndex(i);
	    			frequencies[i] = object.getFrequency(keys[i]);
	    		}
	    		this.numChildren = object.getNumChildren();
	    		this.children = new Long[numChildren];
    		}
    	}
    	
    	/* Returns the byte allocaton necessary for a Node object */
    	public int getDiskSize() {
    		int size = 0;
    		
    		size += Integer.BYTES; //numKeys
    		size += 4; //Key array pointer
    		size += Long.BYTES * MAX_KEYS; //Number of keys stored in array
    		size += Integer.BYTES; //numChildren
    		size += 4; //Children array pointer
    		size += Long.BYTES * MAX_CHILDREN; //Number of children addresses in array
    		size += Long.BYTES; //Parent address
    		size += Integer.BYTES * MAX_KEYS; //Key frequencies
    		
    		return size;
    	}
    	
    	/* Returns the next available address in the binary file */
    	public Long getNextAddress() {
    		return nextDiskAddress += this.getDiskSize();
    	}
    }
    
    /**
     * Writes meta data to the head of the file. Meta data includes
     * tree degree, subsequence length, and root address.
     * @throws IOException
     */
    public void writeMetaData() throws IOException {
    	file.position(0);
    	
    	ByteBuffer tmpbuffer = ByteBuffer.allocateDirect(METADATA_SIZE);
    	
    	tmpbuffer.clear();
    	tmpbuffer.putInt(degree);
    	tmpbuffer.putInt(subsequenceLength);
    	tmpbuffer.putLong(rootAddress);
    	
    	tmpbuffer.flip();
    	file.write(tmpbuffer);
    }
    
    /**
     * Reads meta data from the head of a file. Meta data includes
     * tree degree, subsequence length, and root address.
     * @throws IOException
     */
    public void readMetaData() throws IOException{
    	file.position(0);
    	
    	ByteBuffer tmpbuffer = ByteBuffer.allocateDirect(METADATA_SIZE);
    	
    	tmpbuffer.clear();
    	file.read(tmpbuffer);
    	
    	tmpbuffer.flip();
    	degree = tmpbuffer.getInt();
    	subsequenceLength = tmpbuffer.getInt();
    	rootAddress = tmpbuffer.getLong();
    }
    
    /**
     * Reads a Node from a binary file at specified address. Node variables are stored
     * in the following order: number of keys, list of keys, number of children,
     * list of children addresses, parent address. Leaf status is calculated by 
     * number of children. 
     * @param diskAddress of node
     * @return Node object containing information at address
     * @throws IOException
     */
    public Node diskRead(long diskAddress) throws IOException {
    	if (diskAddress == 0) return null;
    	
    	TreeObject object = new TreeObject(degree);
    	    	
    	file.position(diskAddress);
    	buffer.clear();
    	
    	file.read(buffer);
    	buffer.flip();
    	
    	int numKeys = buffer.getInt();
    	Long[] keys = new Long[numKeys];
    	Integer[] frequencies = new Integer[numKeys];
    	for (int i = 0; i < numKeys; i++) {
    		Long key = buffer.getLong();
    		keys[i] = key;
    		Integer frequency = buffer.getInt();
    		frequencies[i] = frequency;
    	}
    	
    	int numChildren = buffer.getInt();
		Long[] children = new Long[numChildren];
		for (int i = 0; i < numChildren; i++) {
			Long child = buffer.getLong();
			children[i] = child;
		}
		
    	Long parent = buffer.getLong();
    	
    	Node newNode = new Node(object);
    	newNode.address = diskAddress;
    	newNode.keys = keys;
    	newNode.frequencies = frequencies;
    	newNode.numKeys = numKeys;
    	newNode.children = children;
    	newNode.numChildren = numChildren;
    	newNode.parent = parent;
    	
    	return newNode;
    }
    
    /**
     * Writes a node object to file. Node variables are written
     * in the following order: number of keys, list of keys, number of children,
     * list of children addresses, parent address. Leaf status is calculated by 
     * number of children. 
     * @param Node to be written
     * @throws IOException
     */
    public void diskWrite(Node node) throws IOException {
    	file.position(node.address);
    	buffer.clear();
    	
    	buffer.putInt(node.numKeys);
    	for (int i = 0; i < node.numKeys; i++) {
    		buffer.putLong(node.keys[i]);
    		buffer.putInt(node.frequencies[i]);
    	}
    	
    	buffer.putInt(node.numChildren);
    	for (int i = 0; i < node.numChildren; i++) {
    		buffer.putLong(node.children[i]);
    	}
    	
    	buffer.putLong(node.parent);
    	
    	buffer.flip();
    	file.write(buffer);
    }
    
    /**
     * Recursively writes a BTree to the binary file. Initially takes the root of the tree, which
     * is assigned the root address. Then, key information is written, followed by children information.
     * It is important that the child address is written before the child node is created and written 
     * because the child address is the pointer needed by the parent.
     * @param object to be written, if being called initially should be root
     * @param root if object is the root
     * @param address for object in memory
     * @param parentAddress of parent node
     * @throws IOException
     */
    public void diskWriteTree(TreeObject object, boolean root, Long address, Long parentAddress) throws IOException{
    	Node newNode = new Node(object);
    	if (root) {
    		this.root = newNode;
    		newNode.address = rootAddress;
    		newNode.parent = rootAddress; //Purposely circular
    	} else {
    		newNode.address = address;
    		newNode.parent = parentAddress;
    	}
    	
    	for (int i = 0; i < object.getNumChildren(); i++) {
    		TreeObject child = object.getChildAtIndex(i);
    		Long childAddress = newNode.getNextAddress();
    		newNode.children[i] = childAddress;
    		diskWriteTree(child, false, childAddress, newNode.address);
    	}
    	
    	diskWrite(newNode);
    }
    
    /**
     * Recursively reads a binary tree from a binary file of specific format (see above). 
     * Starts at the root, and instantiates a new tree object using specified information.
     * Once finished, returns the root to the tree.
     * @param node to be read, root initially
     * @param parent of node
     * @return TreeObject root of BTree
     * @throws IOException
     */
    public TreeObject diskReadTree(Node node, TreeObject parent) throws IOException{
    	TreeObject object = new TreeObject(degree);
    	if (node == null) {
    		node = diskRead(rootAddress);
    	}
    	
    	if (parent != null) {
    		object.setParent(parent);
    	}
    	
    	for (int i = 0; i < node.numKeys; i++) {
    		object.addKey(node.keys[i]);
    		object.setFrequency(node.keys[i], node.frequencies[i]);
    	}
    	
    	for (int i = 0; i < node.numChildren; i++) {
    		Node childNode = diskRead(node.children[i]);
    		TreeObject child = diskReadTree(childNode, object);
    		object.addChild(child);
    	}
    	
    	return object;
    }

	public int getDegree(){
		return degree;
	}

	public int getSubsequenceLength(){
		return subsequenceLength;
	}

	public int getNumChildren(Node node){
		return node.numChildren;
	}

    
    
    /**
     * Closes the data file.
     * @throws IOException
     */
    public void finishUp() throws IOException {
        file.close();
    }

}
