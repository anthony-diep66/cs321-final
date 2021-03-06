package cs321.search;
import java.io.File;
import java.util.Scanner;

import cs321.btree.BTree;
import cs321.btree.TreeObject;

public class GeneBankSearchBTree{	
	private static boolean useCache = false;
	private static String btreeFile, queryFile;	private static int cacheSize = 0;	
	public static void main(String[] args) {		
		//verify valid argument length		
		if(args.length < 3 || args.length > 5) {			
			printUsage();		
		}		
		if (args[0].equals("1")) {			
			useCache = true; //use BTree with cache		
		} 
		else if (!(args[0].equals("0") || args[0].equals("1"))) {			
			printUsage();		
		} 		
		btreeFile = args[1]; //BTree File		
		queryFile = args[2]; //Query File		
		if (useCache && args.length >= 4) {			
			cacheSize = Integer.parseInt(args[3]);		
		}		
		if(args.length == 5){}		
		String seq = "", deg = "";		//Find degree		
		for(int i = btreeFile.length()-1; i >= 0; i--) {			
			if(btreeFile.charAt(i) != '.')				
				deg += btreeFile.charAt(i);			
			else break;		
		}		
		deg = reverseString(deg);		//Find sequence length		
		for (int i = btreeFile.length()-deg.length()-2; i >= 0; i--) {			
			if(btreeFile.charAt(i) != '.')				
				seq += btreeFile.charAt(i);			
			else break;		
		}		
		seq = reverseString(seq);		
		int degree = Integer.parseInt(deg);				
		try {			
			//GeneBankConvert gbc = new GeneBankConvert();			
			BTree tree = new BTree(degree, new File(btreeFile), useCache, cacheSize);			
			Scanner scan = new Scanner(new File(queryFile));						
			while(scan.hasNext()) {				
				String query = scan.nextLine(); //what to search for								
				long q = TreeObject.convertToLong(query);				
				TreeObject result = tree.search(tree.GetRoot(), q);								
				if(result != null) 					
					System.out.println(TreeObject.convertToString(result.getDataAsLong(), Integer.parseInt(seq))+": "+ result.getFrequencyCount());			
			}						
		scan.close();		
		} 
		catch (Exception e) {			
			e.printStackTrace();		
		}	
	}	
	private static String reverseString(String s) {		
		if(s.length() == 1)			
			return s;		
		return "" + s.charAt(s.length() - 1) + reverseString(s.substring(0, s.length() - 1));	
	}	
	private static void printUsage() {		
		System.err.println("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]\n");
		System.exit(1); 	
	}
}

