/*package cs321.create;

import cs321.btree.BTree;
import cs321.btree.BTreeNode;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
*/


import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class GeneBankCreateBTree
{

	private static StringTokenizer tokenizer;
	private static GeneBankCreateBTreeArguments geneBankcreateBTreeArguments;
    private static BTree tree;
	
    public static void main(String[] args) throws Exception
    {
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args);
        System.out.println(geneBankCreateBTreeArguments.toString());
        geneBankCreateBTreeArguments = geneBankCreateBTreeArguments;

    }

    private static GeneBankCreateBTreeArguments parseArgumentsAndHandleExceptions(String[] args)
    {
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = null;
        try
        {
            geneBankCreateBTreeArguments = parseArguments(args);
        }
        catch (ParseArgumentException e)
        {
            printUsageAndExit(e.getMessage());
        }
        return geneBankCreateBTreeArguments;
    }

    private static void printUsageAndExit(String errorMessage)
    {
        System.out.println(errorMessage);
        printInstructions();
        System.exit(1);
    }

    static void printInstructions() {
		System.err.println("Usage: java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> [<cache size>] [<debuglevel>]");
		System.err.println("<cache>: 0/1");
		System.err.println("<degree>: degree of the BTree");
		System.err.println("<gbk file>: GeneBank file");
		System.err.println("<sequence length> length of each subsequence");
		System.err.println("[<cache size>]: size of cache");
		System.err.println("[<debug level>]: 0/1");
	}

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        int useCache = Integer.parseInt(args[0]);
        int treeDegree = Integer.parseInt(args[1]);
        String gbkFile = args[2];
        int k = Integer.parseInt(args[3]);
        int cacheSize = 0;
        int debugLevel = 0;

        int arg4;
        if( args.length > 4 ){
            if( args.length == 6 ){
                debugLevel = Integer.parseInt(args[5]);
                if( !(debugLevel == 1 || debugLevel == 0) ){
                    printUsageAndExit("Debug level must be either 0 or 1");
                }
            }
            else{
                debugLevel = 0;
            }
            arg4 = Integer.parseInt(args[4]);
            if( useCache == 1 ){
                if( arg4 > 0 ){
                    cacheSize = Integer.parseInt(args[4]);
                }
                else{
                    printUsageAndExit("Cache size must be positive");
                }
            }
        }
        else{
            printUsageAndExit("Not enough arguments");
        }
        
        if( args.length == 4 && useCache == 1){
            printUsageAndExit("A Cache size must be specified");
        }

        if( args.length < 4 || args.length > 6 ){
            throw new ParseArgumentException("Error while parsing arguments");
        }
        if( !(useCache == 1 || useCache == 0) ){
            printUsageAndExit("Cache option must either be 1 or 0");
        }
        if( treeDegree == 0 ){
            treeDegree = calculateOptimalDegree();
        }
        else if( treeDegree < 0 ){
            printUsageAndExit("Degree cannot be negative");
        }
        if( !(k > 1 && k < 32) ){
            printUsageAndExit("Length of substrings must be between 1 or 31 inclusive");
        }
        


        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments;
        if( useCache == 1 ){
            geneBankCreateBTreeArguments = new GeneBankCreateBTreeArguments(true, treeDegree, gbkFile, k, cacheSize, debugLevel);
        }
        else{
            geneBankCreateBTreeArguments = new GeneBankCreateBTreeArguments(false, treeDegree, gbkFile, k, cacheSize, debugLevel);
        }

       
        return geneBankCreateBTreeArguments;
    }

/**
     * parseFile - given a gbk file and the desired length of each substring (k)
     *      parse through the file and insert each substring into the BTree field
     * 
     * @param fileName
     * @param lengthOfSubstring
     * @throws IOException
     */
    static void parseFile(GeneBankCreateBTreeArguments args) throws IOException{
        
        System.out.println(args.toString());
        tree = new BTree(args.getDegree(), args.getSequenceLength());
        BufferedReader br = new BufferedReader(new FileReader(args.getFileName()));
        String line = "";
        int k = args.getSequenceLength(); 

        do{
            if( line.contains("ORIGIN") ){
            
                while( line.equals("//") == false ){
                    line = br.readLine();  
                    tokenizer = new StringTokenizer(line);
                    while( tokenizer.hasMoreTokens() ){
                        String sequence = tokenizer.nextToken();
                        if( sequence.length() == 10 ){
                            //System.out.println(sequence);       // this is the 10 character sequence of genomes
                            for(int i = 0; i <= sequence.length() - k; i++){
                                String substring = sequence.substring(i, i+ k);
                                if( substring.matches("[actgACTG]+") ){
                                    //System.out.println(substring);
                                	TreeObject currentKey = new TreeObject(substring);
                                	tree.insertNonfull(tree.GetRoot(), currentKey.getDataAsLong());
                                }
                            }
                        }
                    }
                   
                }
            }
        } while ( (line = br.readLine()) != null);
        
		if (args.getDebugLevel() > 0) {
			File dumpFile = new File("dump");
			dumpFile.delete();
			dumpFile.createNewFile();
			PrintWriter writer = new PrintWriter(dumpFile);
			tree.printNode(tree.GetRoot(),writer, args.getSequenceLength());
			writer.close();
		}

        br.close();
    }

    static int calculateOptimalDegree(){
        double optimum;		
		int sizeOfPointer = 4;		
		int sizeOfObject = 12;		
		int sizeOfMetadata = 5;		
		optimum = 4096;		
		optimum += sizeOfObject;		
		optimum -= sizeOfPointer;		
		optimum -= sizeOfMetadata;		
		optimum /= (2 * (sizeOfObject + sizeOfPointer));		
		return (int) Math.floor(optimum);
    }
}



/*SOURCES TO BE ADDED TO README FILE*/
//https://stackoverflow.com/questions/19183423/why-cant-i-access-the-first-token-returned-from-javas-stringtokenizer

