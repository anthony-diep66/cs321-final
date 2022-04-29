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

}
   
