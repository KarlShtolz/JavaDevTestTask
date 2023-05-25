
package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
	private static final String BETON = "������";
	private static final String CEMENT = "�����";
	private static final String EMPTY_STRING = "";
	private static final String DELIMETER1 = ":";
	private static final String DELIMETER2 = ":?";
	private static final String FILE_IS_EMPTY = "File is empty";
	private static final String ERROR_IN_FILE = "Error in file";
	private static final String HEADER_IS_NOT_A_NUMER = "Header is not a number";
	private static final String FILE_NOT_FOUND = "File not found";
	private static final String INPUT_EXCEPTION = "Error input";
	private static final String OUTPUT_EXCEPTION = "Error output";
	private static final String INPUT_FILE = "src/main/input.txt";
	private static final String OUTPUT_FILE = "src/main/output.txt";

	public static void main(String[]args) throws IOException {
		int lenInputStringArray1 = 0;
		int lenInputStringArray2 = 0;
		int lenArrayMax = 0;
		int lenArrayMin = 0;
		String[] inputArrayMax;
		String[] inputArrayMin;
		String[] inputStringArray1;
		String[] inputStringArray2;
		String[] outputStringArray;
		BufferedReader bufferedReader = null;
		try {
			File file = new File(INPUT_FILE);
			if (isFileEmpty(file)) {
				System.out.println(FILE_IS_EMPTY + " or " + FILE_NOT_FOUND);
				return;
			}
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
	        } catch (FileNotFoundException e) {
				System.out.println(FILE_NOT_FOUND);
				e.printStackTrace();
				return;
			} catch (IOException e) {
				System.out.println(INPUT_EXCEPTION);
				e.printStackTrace();
				return;
		}
		try {
			lenInputStringArray1 = Integer.valueOf(bufferedReader.readLine());
			System.out.println(lenInputStringArray1);
		} catch (NumberFormatException e) {
			System.out.println(HEADER_IS_NOT_A_NUMER);
			e.printStackTrace();
		}
		inputStringArray1 = stringArrayFromFile(lenInputStringArray1, bufferedReader);
		try {
			lenInputStringArray2 = Integer.valueOf(bufferedReader.readLine());
			System.out.println(lenInputStringArray2);
		} catch (NumberFormatException e) {
			System.out.println(HEADER_IS_NOT_A_NUMER);
			e.printStackTrace();
		}
		inputStringArray2 = stringArrayFromFile(lenInputStringArray2, bufferedReader);
		if (lenInputStringArray1 >= lenInputStringArray2) {
			lenArrayMax = lenInputStringArray1;
			lenArrayMin = lenInputStringArray2;
			inputArrayMax = inputStringArray1;
			inputArrayMin = inputStringArray2;
		} else {
			lenArrayMax = lenInputStringArray2;
			lenArrayMin = lenInputStringArray1;
			inputArrayMax = inputStringArray2;
			inputArrayMin = inputStringArray1;
		}
		outputStringArray = new String[lenArrayMax];
		System.out.println("Output:");
		for (int i = 0; i < lenArrayMax; i++) {
			String tmp = DELIMETER2;
			StringBuilder stringBuilder = new StringBuilder();
			String[]arrMaxItemAsStringArray = inputArrayMax[i].split(" ");
			stringBuilder.append(inputArrayMax[i]);
			for(int j = 0; j < lenArrayMin; j++) {
				if (stringContainsItemFromArray(inputArrayMin[j], arrMaxItemAsStringArray)) {
					StringBuilder stringBuilder2 = new StringBuilder();
					tmp = stringBuilder2.append(DELIMETER1 + inputArrayMin[j]).toString();
					inputArrayMin[j] = "YYY";
					inputArrayMax[i] = "ZZZ";
				}
			}
			stringBuilder.append(tmp);
			outputStringArray[i] = stringBuilder.toString();
			tmp = EMPTY_STRING;
			System.out.println(outputStringArray[i]);
		}
		try {
			File fileOutput = new File(OUTPUT_FILE);
			FileWriter fileWriter = new FileWriter(fileOutput, false);
			for (int i = 0; i < outputStringArray.length; i++) {
				fileWriter.write(outputStringArray[i]);
				fileWriter.append('\n');
				fileWriter.flush();
			}
		} catch (Exception e) {
			System.out.println(OUTPUT_EXCEPTION);
			e.printStackTrace();
			return;
		}
	}
	/**
	 * return true if String inputStr in String[] items
	 * @param inputStr
	 * @param items
	 * @return
	 */
	public static boolean stringContainsItemFromArray(String inputStr, String[] items) {
		String inputWithNoEnd = getStringWithNoEnd(inputStr);
	    for(int i =0; i < items.length; i++) {
	        if(inputStr.toLowerCase().contains(items[i].toLowerCase()) || 
	        		(inputWithNoEnd.toLowerCase().contains(items[i].toLowerCase()) && inputStr.length() != 1) || 
	        		(inputStr.toLowerCase().contains(getStringWithNoEnd(items[i].toLowerCase())) && items[i].length() != 1) ||
	        		(inputStr.toLowerCase().equals(BETON) && items[i].toLowerCase().equals(CEMENT)) ||
	        		(inputStr.toLowerCase().equals(CEMENT) && items[i].toLowerCase().equals(BETON))) {
	            return true;
	        }
	    }
	    return false;
	}
	/**
	 * return string with length = inputStr.length() - 1
	 * @param inputStr
	 * @return
	 */
	public static String getStringWithNoEnd(String inputStr) {
		char[] input = inputStr.toCharArray();
		StringBuilder sb= new StringBuilder();
		for(int j = 0; j < input.length - 1; j++) {
			sb.append(input[j]);
		}
		String inputWithNoEnd= sb.toString();
		return inputWithNoEnd;
	}
	/**
	 * return true if file is empty
	 * @param file
	 * @return
	 */
	public static boolean isFileEmpty(File file) {
	    return file.length() == 0;
	}
	/**
	 * return array of string from file
	 * @param lenInputStringArray
	 * @param bufferedReader
	 * @return
	 */
	public static String[] stringArrayFromFile(int lenInputStringArray, BufferedReader bufferedReader) {
		String[] inputStringArray = new String[lenInputStringArray];
		for (int i = 0; i < lenInputStringArray; i++) {
			String line = null;
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				System.out.println(ERROR_IN_FILE);
				e.printStackTrace();
			}
			inputStringArray[i] = line;
			System.out.println(inputStringArray[i]);
		}
		return inputStringArray;
	}
}