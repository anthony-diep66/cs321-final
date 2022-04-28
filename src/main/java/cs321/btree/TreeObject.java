package cs321.btree;

public class TreeObject<E>{

	Long key;
	private final Long val;
	private int frequency;
	
	
	public TreeObject(Long key, Long val) {
		this.key = key;
		this.val = val;
		this.frequency = 1;
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
                    System.out.println("ERROR: unexpected character encountered in \"convertToLong\" function... exiting\n");
                    exit(1);
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
    static long convertToString(long substringInDigits){
        String res = "";
        String inputAsString = String.valueOf(substringInDigits);

        // length of inputAsString should be even
        if( inputAsString.length() % 2 != 0 ){
            System.out.println("ERROR: length of substringInDigits = " + inputAsString.length() + " (should be even)");
            exit(1);
        }

        // since each letter takes two digits, iterate by 2
        for(int i = 0; i < inputAsString.length(); i += 2){   
            
            // take two digits at a time, check which character it corresponds to, and append to res
            String c1 = inputAsString.charAt(i);
            String c2 = inputAsString.charAt(i + 1);

            switch( c1+c2 ){
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
                    System.out.println("ERROR: unexpected character encountered in \"convertToString\" function... exiting\n");
                    exit(1);
            }
        }

        return res;

    }


