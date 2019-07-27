import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
/**
 * This class implement the interface DictionaryModelInterfaces
 * Through This class the dictionary is able to predict the possible words (or prefixes of words) 
 * the user is trying to enter, and store them internally. This class contains number of methods to enable the 
 * user to make changes in the text type and these are, nextMatch, acceptWord
 * addCharacter and removeLastCharacter
 * Here are the required instance methods for the DictionaryModel class.
 * @author ShukriAli
 * @version 18/2/2018
 */
public class DictionaryModel extends Observable implements DictionaryModelInterface {

	private SampleDictionary dict;
	private String lastWord;//last word being updated
	private List<String> possibleMatches; // possible matches to the lastword
	private List<String> message; // message diplayed on window
	private int pivot;//index of the word displayed from current matches

	/**
	 * Contructor that reads the dictionary file and initialises some of the field variables
	 * @param dictionaryFile is the dictionary file
	 * @throws java.io.IOException throws exception if it cant read the file
	 */
	public DictionaryModel(String dictionaryFile) throws java.io.IOException{

		this.dict = new SampleDictionary(dictionaryFile);
		this.message = new ArrayList<>();
		this.lastWord = "";
	}
	/**
	 * constructor with empty parameter list and it initialises the dict object with a file
	 * @throws java.io.IOException throws exception if it cant read the file
	 */
	public DictionaryModel() throws java.io.IOException{
		this.dict = new SampleDictionary("/Users/ShukriAli/Downloads/words-2.txt");
	}
	/**
	 * this is a getter for the message variable
	 * @return Returns a list of the words typed in so far, including
	 * the last word (or prefix) which has not yet been accepted.
	 */
	@Override
	public List<String> getMessage() {
		return message;//
	}
	/**
	 * Adds a numeric key that has been typed in by the user. 
	 * Extends the current word (or prefix) (lastWord) with possible matches 
	 * for the new key.
	 */
	@Override
	public void addCharacter(char key) { 
		String sig = this.lastWord; //new variable to hold the signature
		sig+= key; //we add the key to the signature
		if (!dict.signatureToWords(sig).isEmpty()) { // if the signature does not have possible matched then it does not add char
			this.lastWord=sig;// since the signature has a possible match in the list we assign it the lastword
			System.out.println("last word add char ->"+lastWord);
			possibleMatches = new ArrayList<>(dict.signatureToWords(this.lastWord));//we update the possible matches with the new signature
			if (getMessage().isEmpty() ) {//if message is empty then it adds the word as first element
				getMessage().add(possibleMatches.get(0));
			}
			else {
				getMessage().set(getMessage().size()-1, possibleMatches.get(0));// else resets the last word in the message
			}
		}
		System.out.println(getMessage());
		setChanged();
		notifyObservers();

	}
	/**
	 * Removes the last character typed in. This should remove the last 
	 * character from the current match, but it would in general enlarge 
	 * the possible matches for the current word.
	 */
	@Override
	public void removeLastCharacter() {
		if ((!lastWord.isEmpty()) && (lastWord!=" ")) //it only deletes if the current word contains char
			lastWord=lastWord.substring(0, lastWord.length()-1);// it deletes the last char
		System.out.println("last word remove char ->"+lastWord);
		possibleMatches = new ArrayList<>(dict.signatureToWords(lastWord));//update possible matches with the signature
		System.out.println("possible matches remove char ->"+ possibleMatches);
		if (!possibleMatches.isEmpty())//if possible matches is not empty
			pivot = 0;//reset the index to 0
		getMessage().set(getMessage().size()-1, possibleMatches.get(pivot));// reset the last word in the message to the first word in the possible matches
		System.out.println("MESSAGE remove char ->" + getMessage());
		setChanged();
		notifyObservers();
	}
	/**
	 * Cycles through the possible matches for the current word, 
	 * i.e., changes the current word to the next match if there is one, 
	 * or goes back to the first match otherwise.
	 */
	@Override
	public void nextMatch() {
		pivot++;//adds one to the index of the possible matches so that it selects the next word in the list
		if (pivot == possibleMatches.size()) { // once we have increased the index and if the index is equal to size of the list(ArrayOutOfBound))
			pivot = 0;// reset to zero
		}
		if (pivot < possibleMatches.size())//if index is less than the size of the list
			getMessage().set(getMessage().size()-1, possibleMatches.get(pivot));//change the current word to the next word in the list
		System.out.println(" next mathch "+ getMessage());
		setChanged();
		notifyObservers();
	}
	/**
	 * Accepts the current matched word and adds it to the composed message.
	 */
	@Override
	public void acceptWord() {
		possibleMatches = new ArrayList<>();// resets the possible matches for the current signature
		possibleMatches.add(" ");// adds the space between the last word and the new word
		lastWord = ""; // last word is reset to empty
		getMessage().add(possibleMatches.get(0));// empty space is added to the message
		setChanged();
		notifyObservers();
	}
}
