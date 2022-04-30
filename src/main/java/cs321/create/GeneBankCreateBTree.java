package cs321.create;

import cs321.btree.BTree;
import cs321.common.ParseArgumentException;

import java.io.*;
import java.util.List;

public class GeneBankCreateBTree
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello world from cs321.create.GeneBankCreateBTree.main");
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArgumentsAndHandleExceptions(args);


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
        return null;
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
        BufferedReader br = new BufferedReader(new FileReader("test1.gbk"));
        String line = "";
        int k = 6; 
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
                                    ////////
                                    //INSERT HERE
                                    /////// 
                                }
                            }
                        }
                    }
                   
                }
            }
        } while ( (line = br.readLine()) != null);

        br.close();
    }
}
/*SOURCES TO INCLUDE INTO README*/

//https://stackoverflow.com/questions/19183423/why-cant-i-access-the-first-token-returned-from-javas-stringtokenizer
