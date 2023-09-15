// Zacharia Kornas
// 05.18.2021
// CSE 142 Section AX
// TA: Paul george Druta
// Take-home Assessment #6: YazInterpreter
//
// This program allows the user to translate files in YazLang
// and store in another file
// The user may also view a YazLang file in the console
import java.util.*;
import java.io.*;

public class YazInterpreter {
   public static void main(String[] args) throws FileNotFoundException{
      Scanner console = new Scanner(System.in);
      
      intro();
      String whatDo = "A"; //Assigned 'A' to ensure that the while loop starts
      while (!whatDo.equals("Q")) {
         System.out.print("(I)nterpret YazLang program, (V)iew output, (Q)uit? ");
         whatDo = console.nextLine();
         whatDo = whatDo.toUpperCase();
         if (whatDo.equals("I")) {
            File name = validFile(console);
            interpretFile(name, console);
         } else if (whatDo.equals("V")) {
            File name = validFile(console);
            viewFile(name);
         }
      }
   }
   
   // Intro message that explains what the user can do in program
   public static void intro() {
      System.out.println("Welcome to YazInterpreter!");
      System.out.println("You may interpret a YazLang program and output");
      System.out.println("the results to a file or view a previously");
      System.out.println("interpreted YazLang program.");
      System.out.println();
   }
   
   // Interprets the YazLang file to another file
   // Will output and override an existing file or create a new file
   // Parameters
   //    name - The file name of a valid file that will be interpreted from YazLang
   //    console - Object used to read input from user in console
   public static void interpretFile(File name, Scanner console) throws FileNotFoundException {
      System.out.print("Output file name: ");
      PrintStream output = new PrintStream(new File(console.nextLine()));
      Scanner input = new Scanner(name);
      while(input.hasNextLine()) {
         String line = input.nextLine();
         Scanner lineScan = new Scanner(line);
         if (line.startsWith("REPEAT")) {
            repeat(lineScan, output);
         } else if (line.startsWith("RANGE")) {
            range(lineScan, output);
         } else if (line.startsWith("CONVERT")) {
            convert(lineScan, output);
         }
      }
      System.out.println("YazLang interpreted and output to a file!");
      System.out.println();
   }
   
   // Converts temperatures read in YazLang file to opposite temperature measurement
   // Prints to an output file
   // Ferenheit becomes Celcius
   // Celcius becomes Ferenheit 
   // Parameters:
   //    lineScan - Scans the line from the input file to take in each token
   //    output - The object used to print to the output file
   public static void convert(Scanner lineScan, PrintStream output) {
      String command = lineScan.next();
      int temp = lineScan.nextInt();
      String tempType = lineScan.next();
      tempType = tempType.toUpperCase();
      if (tempType.equals("C")) {
         temp = (int) (1.8 * temp + 32);
         tempType = "F";
      } else if (tempType.equals("F")) {
         temp = (int) ((temp - 32)/1.8);
         tempType = "C";
      }
      output.print(temp + tempType);
      output.println();
   }
   
   // Prints numbers between a start number and an end number from a YazLang file
   // incrimenting up by a specific unit
   // Prints to an output file
   // Parameters:
   //    lineScan - Scans the line from the input file to take in each token
   //    output - The object used to print to the output file
   public static void range(Scanner lineScan, PrintStream output) {
      String command = lineScan.next();
      int startRange = lineScan.nextInt();
      int endRange = lineScan.nextInt();
      int upBy = lineScan.nextInt();
      int printRange = startRange + upBy;
      if (startRange < endRange) {
         output.print(startRange + " ");
         while (printRange < endRange) {
            output.print(printRange + " ");
            printRange+=upBy;
         }
      }
      output.println();
   }
   
   // Repeats a phrase from a YazLang file a specific number of time
   // Removes start and end quotation marks
   // Replaces underscores with a space
   // Parameters:
   //    lineScan - Scans the line from the input file to take in each token
   //    output - The object used to print to the output file
   public static void repeat(Scanner lineScan, PrintStream output) {
      String command = lineScan.next();
      String fullPhrase = "";
      while(lineScan.hasNext()) {
         String phrase = lineScan.next();
         int numTimes = lineScan.nextInt();
         if (phrase.startsWith("\"")) {
            phrase = phrase.substring(1, phrase.length()-1);
         }
         for (int i = 0; i < numTimes; i++) {
            fullPhrase += phrase;
         }
         fullPhrase = fullPhrase.replace("_", " ");
      }
      output.println(fullPhrase);
   }
   
   // Views the content of a YazLang file
   // Prints content to the console
   // Parameters:
   //    name - The name of a valid file to read from
   public static void viewFile(File name) throws FileNotFoundException{
      Scanner input = new Scanner(name);
      System.out.println();
      while(input.hasNextLine()) {
         String line = input.nextLine();
         System.out.println(line);
      }
      System.out.println();
   }
   
   // Tests if a given input file exists
   // Prompts user to input file name until a given file exists
   // Returns the name of a valid file
   // Parameters:
   //    console - Object used to read input from the user in the console
   public static File validFile(Scanner console) throws FileNotFoundException{
      System.out.print("Input file name: ");
      String fileName = console.nextLine();
      File file = new File(fileName);
      while (!file.exists()) {
         System.out.print("File not found. Try again: ");
         fileName = console.nextLine();
         file = new File(fileName);
      }
      return file;
   }
}
