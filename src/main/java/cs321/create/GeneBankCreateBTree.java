package cs321.create;

import cs321.btree.BTree;
import cs321.btree.BTreeNode;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class GeneBankCreateBTree
{

	private static StringTokenizer tokenizer;
	private static GeneBankCreateBTreeArguments geneBankArguments;
	
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello world from cs321.create.GeneBankCreateBTree.main");
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args);
        geneBankArguments = geneBankCreateBTreeArguments;

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
        System.exit(1);
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        int useCache = Integer.parseInt(args[0]);
        int treeDegree = Integer.parseInt(args[1]);
        String gbkFile = args[3];
        int k = Integer.parseInt(args[4]);
        int cacheSize;
        int debugLevel;

        int arg4;
        if( args.length > 4 ){
            if( args.length == 6 ){
                debugLevel = Integer.parseInt(ars[5]);
            }
            else{
                debugLevel = 0;
            }
            arg4 = Integer.parseInt(args[4]);

            if( useCache == 1 && arg4 > 0 ){
                cacheSize = Integer.parseInt(args[4]);
            }
           else if( useCache == 0 && arg4 <= 0 ){
                printUsageAndExit("Cache size must be positive");
           }

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
        if( !(k < 1 || k > 31) ){
            printUsageAndExit("Length of substrings must be between 1 or 31 inclusive");
        }

        if( args.length > 4 ){
            if( useCache == 1 ){
                GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = new GeneBankCreateBTreeArguments(true, treeDegree, gbkFile, k, cacheSize, debugLevel);
            }
            else{
                GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = new GeneBankCreateBTreeArguments(false, treeDegree, gbkFile, k, cacheSize, debugLevel);
            }
        }
       
        return GeneBankCreateBTree; 
    }

/**
     * parseFile - given a gbk file and the desired length of each substring (k)
     *      parse through the file and insert each substring into the BTree field
     * 
     * @param fileName
     * @param lengthOfSubstring
     * @throws IOException
     */
    static void parseFile(String fileName, int lengthOfSubstring) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        int k = lengthOfSubstring; 
        //Testing
        BTree test = null;
        do{
            if( line.contains("ORIGIN") ){
            	BTree tree = new BTree(geneBankArguments.getDegree(),geneBankArguments.getSequenceLength());
            
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
                                    ////////
                                    //INSERT HERE
                                    /////// 
                                	TreeObject currentKey = new TreeObject(substring);
                                	tree.insertNonful(tree.GetRoot(), currentKey);
                                }
                            }
                        }
                    }
                   
                }
                test = tree;
            }
        } while ( (line = br.readLine()) != null);
        
        test.print(test.GetRoot());

        br.close();
    }

    static int calculateOptimalDegree(){
        return 0;
    }
}



/*SOURCES TO BE ADDED TO README FILE*/
//https://stackoverflow.com/questions/19183423/why-cant-i-access-the-first-token-returned-from-javas-stringtokenizer
