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

        System.exit(1);
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        int debugLevel = 0;
        if( args.length < 1 || args.length > 6 ){
            throw new ParseArgumentException("Error while parsing arguments");
        }

        //check if cache is needed
        if( Integer.parseInt(args[0]) == 1 ){
            if( args.length < 5 ){
                throw new ParseArgumentException("Error: must specify a cache size if a cache is to be used");
            }
            else if( (args.length == 5) && (Integer.parseInt(args[5]) > 1) ){       //cache option chosen, no debug level (debug = 0)
                return new GeneBankCreateBTreeArguments(true, Integer.parseInt(args[1]), args[2], 
                                                    Integer.parseInt(args[3]), Integer.parseInt(args[4]), 0);
            }
            else{
                debugLevel = Integer.parseInt(args[6]);
                return new GeneBankCreateBTreeArguments(true, Integer.parseInt(args[1]), args[2], 
                                                    Integer.parseInt(args[3]), Integer.parseInt(args[4]), debugLevel);    
                
            }
        }
        else{
            if( args.length > 5 ){
                throw new ParseArgumentException("Error: too many arguments");      // a cache size shouldnt be specified if no cache is chosen
            }
            else if (args.length == 5 ){
                debugLevel = Integer.parseInt(args[5]);
                return new GeneBankCreateBTreeArguments(true, Integer.parseInt(args[1]), args[2], 
                                                    Integer.parseInt(args[3]), Integer.parseInt(args[4]), debugLevel);
            }
            else{
                return new GeneBankCreateBTreeArguments(true, Integer.parseInt(args[1]), args[2], 
                                                    Integer.parseInt(args[3]), Integer.parseInt(args[4]), debugLevel);
            }

        }
  
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
}
/*SOURCES TO BE ADDED TO README FILE*/
//https://stackoverflow.com/questions/19183423/why-cant-i-access-the-first-token-returned-from-javas-stringtokenizer
