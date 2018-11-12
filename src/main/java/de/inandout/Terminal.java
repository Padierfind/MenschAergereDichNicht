package de.inandout;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.*;

/**
 * 
 * @author Philip Betzler
 * @email pb060@hdm-stuttgart.de
 * @version 1.1
 * 
 * Hardware access class
 *
 */
public class Terminal {
	
	public enum OutputTargets { PRINTLN, OUTPUTFILE, PRINTLNANDOUTPUTFILE};
	
	
	/** 
     * BufferedReader for reading from standard input line-by-line.
     */
    private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
    
    private static OutputTargets outTarget = OutputTargets.PRINTLN;
    private static File standardOutPutFile = null;
    
    private final static Logger logger = LogManager.getLogger(Terminal.class);
    
    /** 
     * Private constructor to avoid object generation. 
     */
    private Terminal() {
    }

    /**
     * Print a String to the standard output.
     * @param out The string to be printed.
     */
    public static void printLine(String out) {
    	
    	logger.debug("Terminal.println called with parameter: " + out);
        System.out.println(out);
    }

    /**
     * Reads a line from standard input.
     *
     * Returns null at the end of the standard input.
     *
     * Use Ctrl+D to indicate the end of the standard input.
     *
     * @return The next line from the standard input or null.
     * @throws IOException If an I/O error occurs
     */
    public static String readLine() throws IOException {
    	logger.debug("Terminal.readLine called.");
    	try {
    		return in.readLine();
    	} catch (IOException e) {
    		logger.error("Terminal.readLine error occoured. : " + e.toString());
    		throw e;
    	}
        
    }
    
    
    
    
    
    
    /**
     * adds String to File
     * @param filePath String description of the File Path
     * @param text the text to add
     * @throws IOException
     */
    public static void addToFile(String filePath, String text) throws IOException{
    	
    	
    	Terminal.addToFile(filePath, Terminal.createListFromString(text), Charset.defaultCharset());
    }
    
    /**
     * adds ArrayList<Strings> to File
     * @param filePath String description of the File Path
     * @param text the text to add
     * @throws IOException
     */
    public static void addToFile(String filePath, List<String> text) throws IOException{
    	Terminal.addToFile(filePath, text, Charset.defaultCharset());
    }
    
    /**
     * Adds Text to the File
     * @param file File to add
     * @param text ArrayList<String> to Add
     * @throws IOException
     */
    public static void addToFile(File file, List<String> text) throws IOException{
    	Terminal.addToFile(file, text, Charset.defaultCharset());
    }
    
    /**
     * Adds Text to the File
     * @param file File to add
     * @param text String to Add
     * @throws IOException
     */
    public static void addToFile(File file, String text) throws IOException{
    	Terminal.addToFile(file, Terminal.createListFromString(text), Charset.defaultCharset());
    }
    
    /**
     * Adds List<String> to file in specific charset
     * @param filePath String representation of Filepath
     * @param text List<String> to add
     * @param fileCode defines the charset
     * @throws IOException
     */
    public static void addToFile(String filePath, List<String> text, Charset fileCode) throws IOException{
    	
    	Terminal.addToFile(Terminal.getFileFromPath(filePath), text, fileCode);
    }
    
    /**
     * Adds String to file in specific charset
     * @param filePath String representation of Filepath
     * @param text String to add
     * @param fileCode defines the charset
     * @throws IOException
     */
    public static void addToFile(String filePath, String text, Charset fileCode) throws IOException{
    	
    	Terminal.addToFile(Terminal.getFileFromPath(filePath), Terminal.createListFromString(text), fileCode);
    }
    
    /**
     * Adds String to file in specific charset
     * @param fileToAdd File representation of the File to add
     * @param text String to add
     * @param fileCode defines the charset
     * @throws IOException
     */
    public static void addToFile(File fileToAdd, String text, Charset fileCode) throws IOException {
    	Terminal.addToFile(fileToAdd, Terminal.createListFromString(text), fileCode);
    }
    
    /**
     * Adds String to file in specific charset
     * @param fileToAdd File representation of the File to add
     * @param text List<String> to add
     * @param fileCode defines the charset
     * @throws IOException
     */
    public static void addToFile(File fileToAdd, List<String> text, Charset fileCode) throws IOException{
    	
    	if (Terminal.standardOutPutFile == null) {
    		if (!Terminal.setStandardOutputFile(fileToAdd)) {
    			throw new IOException("No valid file to add String!");
    		}
    	}
    	
    	
    	if (text.isEmpty()) {
    		return;
    	}
    	
    	
    	try (PrintWriter writer = new PrintWriter(fileToAdd, fileCode.name())) {
    		
    		Iterator<String> it = text.iterator();
        	
        	while (it.hasNext()) {
        		writer.println(it.next());			
    		}
    	} catch (IOException ie) {
    		throw ie;
    	}
    }
    
    /**
     * creates the file on the hard drive
     * @param filePath
     */
    public static void createFile(String filePath) {
    	
    	File file = new File(filePath);
    	file.getParentFile().mkdirs();
    	
    }
    
    /**
     * Checks if the file is existing
     * 
     * @param filePath
     * String of the file path
     * 
     * @return
     * Boolean if it exists
     */
    public static boolean checkIfFileExists(String filePath) {
    	if (new File(filePath).exists()) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * creats File Object from file String
     * @param filePath
     * @return
     * The created File
     */
    public static File getFileFromPath (String filePath) {
    	if (!Terminal.checkIfFileExists(filePath)) {
    		Terminal.createFile(filePath);
    	}
    	
    	
    	return new File(filePath);
    }
    
    /**
     * clears specific file
     * @param filePath
     * String of the file path
     */
    public static void clearFile(String filePath) {
    	Terminal.getFileFromPath(filePath).delete();
    	Terminal.getFileFromPath(filePath);
    }
    
    /**
     * delets specific file
     * @param filePath
     * String of the file path
     */
    public static void deleteFile(String filePath) {
    	Terminal.getFileFromPath(filePath).delete();
    }
    
    /**
     * Read all Lines from a file! Everything is saved as a String! (null in file means String "null")
     * @param filePath
     * String of the file path
     * 
     * @return
     * List<List> the content of the file
     * 
     * @throws IOException
     * if something went wrong.
     */
    public static List<String> readFromFile (String filePath) throws IOException {
    	
    	List<String> lines = Files.readAllLines(Paths.get(filePath));
        
    	return lines;
    }
    
    /**
     * Read all Lines from a file! Not everything is saved as a String! (null in file means null Pointer in return List!)
     * @param filePath
     * String of the file path
     * 
     * @return
     * List<List> the content of the file
     * 
     * @throws IOException
     * if something went wrong.
     */
    public static List<String> readFromFileStrinNullASNullPointer (String filePath) throws IOException {
    	
    	List<String> lines = Terminal.readFromFile(filePath);
    	
    	if (lines == null) {
    		return lines;
    	}
    	
    	for (int i = 0; i < lines.size(); i++) {
    		if (lines.get(i).equals("null")) {
    			lines.set(i, null);
    		}
    	}
        
    	return lines;
    }
    
    /**
     * returns a List<String> representation of the parameter String. 
     * @param textString
     * String parameter
     * 
     * @return
     * List representation List.size always == 1 !
     */
    private static List<String> createListFromString (String textString) {
    	    	
    	List<String> tempList = new ArrayList<String>();
    	tempList.add(textString);
    	
    	return tempList;
    }
    
    /**
     * returns the String representation of todays exact date
     * @return
     * String date
     */
    private static String getTodaysTimeAndDateAsString() {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	Date today = Calendar.getInstance().getTime();
    	String reportDate = df.format(today);

    	return reportDate;
    }
    
    /**
     * returns the output target
     * @return
     * OutputTargets the target
     */
    public static OutputTargets getOutputTarget () {
    	return Terminal.outTarget;
    }
    
    /**
     * sets the output target 
     * @param newOutputTarget
     * OutputTargets the new target
     */
    public static void setOutputTarget (Terminal.OutputTargets newOutputTarget) {
    	Terminal.outTarget = newOutputTarget;
    }
    
    /**
     * prints the parameter output in the art specified by outTarget
     * @param output
     */
    public static void out (String output) {
    	
    	switch (outTarget) {
    		case PRINTLN:     						
    						Terminal.printLine(output); 
    						break;
    		case OUTPUTFILE: 
    						try {
    							Terminal.addToFile(Terminal.standardOutPutFile, output);
    						} catch (IOException e) {
    							String errorMessage = "Error: " + e.getMessage() + " Unable to prin message on File. Message was: " +output;
    							logger.error(errorMessage);
    							Terminal.printLine(errorMessage);
							}
    						break;
    		case PRINTLNANDOUTPUTFILE:
    						try {
    							Terminal.printLine(output);
    							Terminal.addToFile(Terminal.standardOutPutFile, output);
    						} catch (IOException e) {
    							String errorMessage = "Error: " + e.getMessage() + " Unable to prin message on File. Message was: " +output;
    							logger.error(errorMessage);
    							Terminal.printLine(errorMessage);
							}
    						break;
    		default:
    						String errorMessage = "Something went wrong while deciding where to put the output stream. The stream was: " +output;
    						logger.error(errorMessage);
    						Terminal.printLine(errorMessage);
    						break;
    						
    						
    	}
    }
    
    /**
     * sets standard Output File wich is used, if the output is set to OUTPUTFILE or PRINTLNANDOUTPUTFILE and out is called.
     * @param newStandardOutputFile
     * File representation of the output file
     * 
     * @return
     * Boolean if the change was succesfull.
     */
    public static boolean setStandardOutputFile (File newStandardOutputFile) {
    	if (newStandardOutputFile != null) {
    		Terminal.standardOutPutFile = newStandardOutputFile;
    		return true;
    	}
    	
    	return false;
    }
    

}
