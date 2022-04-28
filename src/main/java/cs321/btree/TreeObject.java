package cs321.btree;

public class TreeObject{

    private long data;
    private int frequencyCount;

    TreeObject(long substringInDigits){
        this.data = substringInDigits;
        this.frequencyCount = 1;
    }

    TreeObject(String substring){
        long longData = convertToLong(substring);
        this.data = longData;
        this.frequencyCount = 1;
    }
             

    /**
     * convertToLong - Converts the given substring into a long
     *      returns a long, throws error if substring contains anything
     *      other than A, T, C, G
     * @param substring
     * @return
     */
    static long convertToLong(String substring){
        String res = "";
        for(int i = 0; i < substring.length(); i++){
            char letter = substring.charAt(i);

            switch( letter ){
                case 'A':
                    res += "00";
                    break;
                case 'T':
                    res += "11";
                    break;
                case 'C':
                    res += "01";
                    break;
                case 'G':
                    res += "01";
                    break;
                default:
                    System.out.println("ERROR: unexpected character encountered in \"convertToLong\" method... exiting\n");
                    System.exit(1);
            }
        }

        return Long.parseLong(res);

    }

    /**
     * convertToString - Converts the given substringInDigits into a long
     *      returns a String which corresponds to the long representation.
     *      Throws error if length of substringInDigits is not even or 
     *      substringInDigits contains a digit other than "0" or "1"
     * @param substringInDigits
     * @return
     */
    
    static String convertToString(long substringInDigits){
        String res = "";
        String inputAsString = String.valueOf(substringInDigits);

        // length of inputAsString should be even
        if( inputAsString.length() % 2 != 0 ){
            System.out.println("ERROR: length of substringInDigits = " + inputAsString.length() + " (should be even)");
            System.exit(1);
        }

        // since each letter takes two digits, iterate by 2
        for(int i = 0; i < inputAsString.length(); i += 2){   
            
            // take two digits at a time, check which character it corresponds to, and append to res
            String c1 = String.valueOf(inputAsString.charAt(i));
            String c2 = String.valueOf(inputAsString.charAt(i + 1));
        
            switch( c1 + c2 ){
                case "00":
                    res += "A";
                    break;
                case "11":
                    res += "T";
                    break;
                case "01":
                    res += "C";
                    break;
                case "10":
                    res += "G";
                    break;
                default:
                    System.out.println("ERROR: unexpected character encountered in \"convertToString\" method... exiting\n");
                    System.exit(1);
            }
        }

        return res;

    }

    public int compareTo(TreeObject other){
	
	long rValue = this.data - other.getDataAsLong();
	if( rValue > 0 ){
		return 1;
	}
	else if( rValue == 0 ){
		return 0;
	}
	else{
		return -1;
	}

    }

    public String toString() {
	return "TreeObject[value = " + this.data + ", frequency = " + this.frequencyCount + "]";
	
    }

    /**
     * returns this object's data as a long
     * @return
     */
    long getDataAsLong(){
        return this.data;
    }

    /**
     * returns this object's data as a String
     * @return
     */
    String getDataAsString(){
        return convertToString(this.data);
    }

    /**
     * return this object's frequency count
     * @return
     */
    int getFrequencyCount(){
        return this.frequencyCount;
    }

    /**
     * increment this.frequencyCount by 1
     */
    void incrementFrequencyCount(){
        this.frequencyCount++;
    }


}
