import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Task 
{
	private JFrame frame;
	
	// Text fields in which user can type text.
	private JTextField firstLineTextField;
	private JTextField secondLineTextField;
	private JTextField firstLineWordTextField;
	private JTextField secondLineWordTextField;
	//////////////////////////////////////////////
	
	// Constant text fields in which user can't type text.
	private JTextPane txtpnFirstLine;
	private JTextPane txtpnSecondLine;
	private JTextPane txtpnFirstLineWord;
	private JTextPane txtpnSecondLineWord;
	///////////////////////////////////////////////
	
	private static String path; // Path to the text file.
	
	// Stores words from the text file.
	private static ArrayList<ArrayList<String>>words = new ArrayList<ArrayList<String>>();
	
	/* Fills the arrayList words with the words from the file.
	 * 
	 * arguments: final String path
	 * 
	 * return type: void
	 */
	private static void getWordsFromFile(final String path)
	{
		try 
		{
			 File file = new File(path);
			 Scanner line = new Scanner(file);// Used to scan lines of the file.
			 
			 int lineIndex = 0;// Index of file's line.
			 
			// Scans line by line.
			 while(line.hasNextLine())
			 {
				words.add(new ArrayList<String>());
				Scanner word = new Scanner(line.nextLine());// Used to scan words from each line of the file. 
				
				// Scans word by word.
			 	while (word.hasNext()) 
			 	{
			 		words.get(lineIndex).add(word.next());
			 	}
			 	word.close();
			 	lineIndex++;
			 }
			 line.close();
	    } 
		catch(FileNotFoundException e) 
	    {
	      System.err.println("File cannot be open.");
	    }
	}
	
	/* Checks if the file is in right the format. Throws FileFormatException.
	 * 
	 * arguments: final String forCheck
	 * 
	 * return type: void
	 */
	private static void checkFileFormat(final String forCheck) throws FileFormatException
	{
		Pattern pattern = Pattern.compile(".txt$");
	    Matcher matcher = pattern.matcher(forCheck);
	    boolean matchFound = matcher.find();
	    
	    if(matchFound == false) 
	    {
	        throw new FileFormatException("This is not text file.");
	    }
	}
	
	/* Swaps two lines of arrayList words.
	 * 
	 * arguments: final int firstLineIndex
	 * 			  final int secondLineIndex
	 * 
	 * return type: void
	 */
	private void SwapLines(final int firstLineIndex, final int secondLineIndex)
	{
		ArrayList<String> temp = words.get(firstLineIndex);
		words.set(firstLineIndex, words.get(secondLineIndex));
		words.set(secondLineIndex, temp);
	}
	
	/* Swaps two words of arrayList words.
	 * 
	 * arguments: final int firstLineIndex
	 * 			  final int secondLineIndex
	 * 			  final int firstWordIndex
	 * 			  final int secondWordIndex
	 * 
	 * return type: void
	 */
	private void SwapWords(final int firstLineIndex,final  int secondLineIndex,
			final int firstWordIndex, final int secondWordIndex)
	{
		String temp = words.get(firstLineIndex).get(firstWordIndex);
		words.get(firstLineIndex).set(firstWordIndex, words.get(secondLineIndex).get(secondWordIndex));
		words.get(secondLineIndex).set(secondWordIndex, temp);
	}
	
	/* Checks if the number is in given range. Throws RangeException.
	 * 
	 * arguments: final int forCheck
	 * 			  final int maxValue
	 * 			  final String textfieldName
	 * 
	 * return type: void
	 */
	private void checkForRange(final int forCheck, final int maxValue, final String textfieldName) throws RangeException
	{
		if(forCheck < 0 || forCheck > (maxValue - 1))
		{
			System.err.println(textfieldName);
			throw new RangeException("Must be between 0 and " + (maxValue - 1));
		}
	}
	
	/* Checks input from text fields. If input is incorrect throws InputException
	 * 
	 * arguments: final String forCheck
	 * 			  final String textfieldName
	 * 			  final int maxRange
	 * 
	 * return type: void
	 */
	private void checkInput(final String forCheck, final String textfieldName, final int maxValue) 
			throws InputException, RangeException
	{
		Pattern pattern = Pattern.compile("^[0-9]{0,9}$");
	    Matcher matcher = pattern.matcher(forCheck);
	    boolean matchFound = matcher.find();
	    
	    // When in text field aren't only numbers.
	    if(matchFound == false) 
	    {
	        System.err.println(textfieldName);
	        throw new InputException("There must be only numbers not larger than 9 digits.");
	    }
	    
	    // Prevents empty strings that have not been catch by the upper check for going through the number range check.
	    if(forCheck.isEmpty() == false)
	    {
	    	int temp = Integer.parseInt(forCheck);
	 	    checkForRange(temp, maxValue, textfieldName);
	    }
	}
	
	/* Write data from array list words to file. Throws IOException
	 * 
	 * arguments: none
	 * 
	 * return type: void
	 */
	private void writeToFile() throws IOException
	{
		try 
		{
			FileWriter file = new FileWriter(path);
			BufferedWriter bufferWriter = new BufferedWriter(file);
			 
			for(ArrayList<String> line : words)// line by line
			{
				for(String word : line)// word by word
				{
					bufferWriter.write(word + " ");
				}
				bufferWriter.newLine();
			}
			
			bufferWriter.close();
			file.close();
	    } 
		catch(FileNotFoundException e) 
	    {
	      System.err.println("File cannot be open.");
	    }
	}
	
	/* Checks to swap lines or to swap words.
	 * 
	 * arguments: final String firstLineWordIndex
	 * 			  final String secondLineWordIndex
	 * 			  final int firstLineIndexInt
	 * 			  final int secondLineIndexInt
	 * 
	 * return type: void
	 */
	private void Swap(final String firstLineWordIndex, final String secondLineWordIndex,
			final int firstLineIndexInt, final int secondLineIndexInt)
	{
		// Swap lines.
		if((secondLineWordIndex.length() == 0) && (firstLineWordIndex.length() == 0))
		{
			SwapLines(firstLineIndexInt, secondLineIndexInt);
		}
		else // Swap words.
		{
			int firstLineWordIndexInt = Integer.parseInt(firstLineWordIndex);
			int secondLineWordIndexInt = Integer.parseInt(secondLineWordIndex);
			
			SwapWords(firstLineIndexInt, secondLineIndexInt, firstLineWordIndexInt, secondLineWordIndexInt);
		}
	}
	
	/* Action performed when start button is clicked.
	 * 
	 * arguments: none
	 * 
	 * return type: void
	 */
	private void startButtonAction()
	{
		String firstLineIndex = firstLineTextField.getText();
		String secondLineIndex = secondLineTextField.getText();
		String firstLineWordIndex = firstLineWordTextField.getText();
		String secondLineWordIndex = secondLineWordTextField.getText();
		
		// If first line and second line indexes are empty swap is not possible.
		if((firstLineIndex.isEmpty() == true) || (secondLineIndex.isEmpty() == true))
		{
			System.err.println(txtpnFirstLine.getText() + " and " + txtpnSecondLine.getText() + " must NOT be empty!");
		}
		else
		{
			try
			{
				checkInput(firstLineIndex, txtpnFirstLine.getText(), words.size());
				checkInput(secondLineIndex, txtpnSecondLine.getText(), words.size());
			
				int firstLineIndexInt = Integer.parseInt(firstLineIndex);
				int secondLineIndexInt = Integer.parseInt(secondLineIndex);
				
				checkInput(firstLineWordIndex, txtpnFirstLineWord.getText(), words.get(firstLineIndexInt).size());
				checkInput(secondLineWordIndex, txtpnSecondLineWord.getText(), words.get(secondLineIndexInt).size());
			
				Swap(firstLineWordIndex, secondLineWordIndex, firstLineIndexInt, secondLineIndexInt);
				
				try
				{
					writeToFile();
				}
				catch(IOException e)
				{
					System.err.println(e.getMessage());
				}
				
				System.out.println("Swap was done!");
			}
			catch(InputException e)
			{
				System.err.println(e.getMessage());
			}
			catch(RangeException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
	
	/* Set up of text fields.
	 * 
	 * arguments: none
	 * 
	 * return type: void
	 */
	private void textFieldsSetUps()
	{
		firstLineTextField.setBounds(121, 71, 86, 20);
		firstLineTextField.setColumns(10);
		
		secondLineTextField.setBounds(393, 71, 86, 20);
		secondLineTextField.setColumns(10);
		
		firstLineWordTextField.setBounds(121, 126, 86, 20);
		firstLineWordTextField.setColumns(10);

		secondLineWordTextField.setBounds(393, 126, 86, 20);
		secondLineWordTextField.setColumns(10);
	}
	
	/* Set ups for text panes.
	 * 
	 * arguments: none
	 * 
	 * return type: void
	 */
	private void textPanesSetUps()
	{
		txtpnFirstLine.setBounds(37, 71, 74, 20);
		txtpnFirstLine.setEditable(false);
		txtpnFirstLine.setText("first line index");
		
		txtpnSecondLine.setBounds(294, 71, 89, 20);
		txtpnSecondLine.setEditable(false);
		txtpnSecondLine.setText("second line index");
		
		txtpnFirstLineWord.setBounds(10, 126, 101, 20);
		txtpnFirstLineWord.setEditable(false);
		txtpnFirstLineWord.setText("first line word index");
		
		txtpnSecondLineWord.setBounds(267, 126, 116, 20);
		txtpnSecondLineWord.setEditable(false);
		txtpnSecondLineWord.setText("second line word index");
	}

	public static void main(String[] args) 
	{
		try
		{
			checkFileFormat(args[0]);
			path = args[0];
			getWordsFromFile(path);
		
			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					try 
					{
						Task window = new Task();
						window.frame.setVisible(true);
					} 
					catch (Exception e) 
					{
						System.err.println("Can't create the window.");
					}
				}
			});
		}
		catch(FileFormatException e)
		{
			System.err.println(e.getMessage());
		}
	}

	public Task() 
	{
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Texts fields in which user can type text.
		firstLineTextField = new JTextField();
		secondLineTextField = new JTextField();
		firstLineWordTextField = new JTextField();
		secondLineWordTextField = new JTextField();
		
		textFieldsSetUps();
		//////////////////////////////////////////////
		
		// Constant text fields in which user can't type text.
		txtpnFirstLine = new JTextPane();
		txtpnSecondLine = new JTextPane();
		txtpnFirstLineWord = new JTextPane();
		txtpnSecondLineWord = new JTextPane();
		
		textPanesSetUps();
		///////////////////////////////////////////////////////
		
		// The start button.
		JButton startButton = new JButton("Start");
		startButton.setBounds(118, 186, 299, 43);
		startButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				startButtonAction();
			}
		});
		////////////////////////////////////////
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(txtpnFirstLineWord);
		frame.getContentPane().add(txtpnFirstLine);
		frame.getContentPane().add(firstLineTextField);
		frame.getContentPane().add(txtpnSecondLineWord);
		frame.getContentPane().add(secondLineWordTextField);
		frame.getContentPane().add(firstLineWordTextField);
		frame.getContentPane().add(secondLineTextField);
		frame.getContentPane().add(startButton);
		frame.getContentPane().add(txtpnSecondLine);
	}
}
