import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Rank
 * 
 * 
 * Takes command link argument of a word of up two 25 letters and returned the alphabetical rank 
 * of all the permutations of the letters in the input word.
 * Solution found using additive properties of the alphabetical rank. The program narrows down the
 * possible solutions by calculating the number of permutations for the set of letters, takes the
 * first letter from the word in that set, calculates it's alphabetical rank among the set of letters
 * and calculates the first possible occurrence of that letter. If we do this for the rest of the
 * letters, shortening our set but one (the first letter in the previous set) we will eventually learn
 * the rank of the word in the total number of permutations.
 * 
 * BigInteger used because 21! exceeds long.
 * 
 * Important equation:
 * n=number of letters
 * n1=number of letters=letter1 (e.g. "A")
 * n2=number of letters=letter2 (e.g. "B")
 * ....
 * N_permutations=n!/(sum(n1)!*sum(n2)!...sum(nx)!)
 * 
 * Example:
 * BOOTH (alphabetized 	 BHOOT)
 * 						 01234
 * n=5
 * nB=1
 * nO=2
 * nH=1
 * nT=1
 * 
 * First letter:
 * N_permutations=n!/(nB!*nH!*nO!*nT!)
 * N_permutations=5!/(1!*1!*2!*1!)=120/2=60
 * Find where words beginning with rank: 	BHOOT
 * 											01234 
 * B is 0 of 4, with 0 being first.
 * Words beginning with B must rank if the first 20%(1/5) of possible permutations.
 * First 20% is 0-12 
 * rank_B=N_permutations*alpha_order_B
 * rank_B=60*0=0
 * Pop "B" from list.
 * 
 * Second letter:
 * N_permutations=(n-1)!/(nH!*nO!*nT!)
 * N_permutations=4!/(1!*2!*1!)=24/2=12
 * Find where words beginning with rank: 	HOOT
 * 											0123 
 * O is 1 of 3.
 * Words beginning with 0 must rank if the second 25%(1/4) of possible permutations.
 * Second 25% is 4-8 
 * rank_O_0=N_permutations*alpha_order_O_0
 * rank_O_0=12*1/4=3
 * Pop "O_0" from list.
 * 
 * Third letter:
 * N_permutations=(n-2)!/(nH!*(nO-1)!*nT!)
 * N_permutations=3!/(1!*1!*1!)=6/1=6
 * Find where words beginning with rank: 	HOT
 * 											012
 * O is 1 of 2.
 * Words beginning with 0 must rank if the second 33%(1/3) of possible permutations.
 * Second 33% is 2-3
 * rank_O_1=N_permutations*alpha_order_O_1
 * rank_O_1=6*1/3=2
 * Pop "O_1" from list.
 * 
 * Fourth letter:
 * N_permutations=(n-3)!/(nH!*nT!)
 * N_permutations=2!/(1!*1!)=2/1=2
 * Find where words beginning with rank: 	HT
 * 											01
 * T is 1 of 1.
 * Words beginning with 0 must rank if the second 50%(1/2) of possible permutations.
 * Second 50% is 1
 * rank_T=N_permutations*alpha_order_T
 * rank_T=2*1/2=1
 * Pop "T" from list.
 * 
 * Fifth letter:
 * N_permutations=(n-4)!/(nH!)
 * N_permutations=1!/(1!)=1
 * Find where words beginning with rank: 	H
 * 											0
 * H is 0 of 0.

 * rank_H=N_permutations*alpha_order_H
 * rank_H=1*0/1
 * Pop "H" from list.
 * 
 * Rank(BOOTH)=rank_B+rank_O_0+rank_O_1+rank_T+rank_H
 * Rank(BOOTH)=0+3+2+1+0=6+1(because there is no rank 0)
 * Rank(BOOTH)=7
 * 
 *
 * @author Collin Brown (collin.tegner.brown@gmail.com)
 */
public class Rank {
	String word;
	StringBuilder sortedWord;
	int rank=1;
	Map<Character, Integer> auxMap = new TreeMap<Character, Integer>();
	
	/**
	 * Constructor
	 * Input word (eg. "BOOKKEEPER") is placed into a Tree Map to give a count of each letter.
	 * 				"B"=1 "E"=3 "K"=2 "O"=2 "P"=1 "R"=1
	 * 				Input word is also sorted alphabetically. Sorted word stored in StringBuilder sortedWord object.
	 * @param word Word to be ranked
	 * 				
	 */
	public Rank(String word){
		this.word=word;
	    for(char c : word.toCharArray()) {//populating map
	        if(auxMap.get(c)==null){
	            auxMap.put(c, 1);//adding keys for each letter
	        }else {
	            auxMap.put(c, auxMap.get(c)+1);//adding value+1 for each key that already exists
	        }           
	    }
	    char[] chars = word.toCharArray();
	    Arrays.sort(chars);
	    sortedWord = new StringBuilder();
	    sortedWord.append(chars);
	}
	

	
	/**
	 * Letter rank allows 
	 * Returns first occurrence in sorted input word.
	 * @param input Character to find in sorted word.
	 * @return index Index of alphabetical position in input word. 
	 */
	public int letterRank(char input){
		return (sortedWord.indexOf(String.valueOf(input)));//returning first instance of character in sorted word
	}
	
	
	/**
	 * factPerLetter processes the rank for the letter. This is the main driver of the program.
	 * Letter is input, removed from sorted word
	 * @param input character from word
	 * @return BigInteger rank for input letter
	 */
	public BigInteger factPerLetter(char input){
		int num = 0, hold;
		BigInteger den=new BigInteger("1");
		Iterator<Integer> counter = auxMap.values().iterator();//iterator created to process letters in remaining in map
		int localRank=letterRank(input);//gets the multiplier for the letter [0...n]
		sortedWord.replace(localRank, localRank+1, "");//Removes processed letter from sortedWord bhoot->hoot
		while(counter.hasNext()){
			hold=(Integer) counter.next();
			num=num+hold;//total number of letters, n
			den=den.multiply(factorialBigInt(hold));//sum(n_B)!*sum(n_O)!.....
		}
		if(auxMap.get(input)>1){//checks key for letter
			auxMap.put(input, auxMap.get(input)-1);//if has more than 1 left, subtract from value
		}else{
			auxMap.remove(input);//if one or less, delete the key for the input letter
		}
		
		return factorialBigInt(num).divide(den.multiply(BigInteger.valueOf(num))).multiply(BigInteger.valueOf(localRank));
		//n!/(sum(n_0)!*sum(n_1)!...*sum(n_x)!)*(alpha_rank_input/n)
	}

	
	/**
	 * factorialBigInt will return n! as BigInterger object
	 * @param n number to be factorialized
	 * @return n! as BigInterger object
	 */
	public static BigInteger factorialBigInt(int n) {
		BigInteger fact = new BigInteger("1");
        for (int i = 1; i <= n; i++) {
        	fact=fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }
	
	

	
	public static void main(String[] args){
		

		String input = null;
		try {
			input = args[0];
		} catch (Exception e) {
			// Catches errors, mainly lack of input errors.
			System.err.println("No word input. Exiting.");
			System.exit(0);
		}
		
		Rank word=new Rank(input); //Rank object constructed and given command line input
		BigInteger rank=new BigInteger("1");//Because there is no rank 0

		for(char index:input.toCharArray()){//iterates through each letter
			rank=rank.add(word.factPerLetter(index));
		}

	    System.out.println(rank);//return to command line
	
	}

}
