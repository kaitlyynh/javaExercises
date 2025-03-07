/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package huynhxk;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;
import java.io.FileWriter;
/**
 *
 * @author kaitlynhuynh
 */
public class HuynhXK {
    private static int intShift = 5;
    private static int alphaShift = 13;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//         Check for valid input
        if (args.length != 2) {
            System.out.println("Invalid input, please provide the task number and 2 text files.");
            return;
        }
        String firstFileName = args[0].trim();
        String secondFileName = args[1].trim();
        System.out.println("Args are " + firstFileName + " " + secondFileName);
        try {
            File inputFile = new File(firstFileName);
            Scanner inputReader = new Scanner(inputFile);
            String fileContents = "";
            while (inputReader.hasNextLine()) {
              String data = inputReader.nextLine();
              fileContents += data + "\n";
            }
            inputReader.close();
            System.out.println("Contents before:");
            System.out.println(fileContents);
            String outputContents = "";
            String shiftedContent = shift(fileContents);
            
            System.out.println("Contents after:");
            System.out.println(shiftedContent);
            File outputFile = new File(secondFileName);
            if (outputFile.createNewFile()) {
                System.out.println("File created");
            } else {
                outputFile.delete();
                outputFile.createNewFile();
                System.out.println("File already existed, remade it");
            }
            // Write to new file
            System.out.println("Writing to file");
            FileWriter myWriter = new FileWriter(outputFile);
            myWriter.write(shiftedContent);
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println("An error occured");
            return;
        }
        
    } 
    public static String shift(String orig) {
        String encrypted = "";
        for (char c : orig.toCharArray()) {
                if (Character.isDigit(c)) {
                    System.out.println((Character.getNumericValue(c) + intShift) % 10);
                    encrypted += ((Character.getNumericValue(c) + intShift) % 10);
                } else if (Character.isAlphabetic(c)) {
                    if (Character.isUpperCase(c)) {
                        encrypted += (char) ((c + alphaShift - 65) % 26 + 65);
                    } else { // Lowercase
                        encrypted += (char) ((c + alphaShift - 97) % 26 + 97);
                    }
                } else {
                    encrypted += c;
                }
        }
        return encrypted;
    }
}

