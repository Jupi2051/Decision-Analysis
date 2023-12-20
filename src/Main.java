import jdk.jshell.execution.Util;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Utilities.print("Please enter the array's X length:");
        int xLength = userInput.nextInt();
        Utilities.print("Please enter the array's Y Length:");
        int yLength = userInput.nextInt();

        ArrayEditor editor = new ArrayEditor(xLength, yLength);
        Utilities.println("Please use the command id=value to insert your data.");

        int ElementsCount = editor.GetItemsCount();
        editor.DisplayToConsole();

        while (true) {
            String set_command = userInput.next();
            set_command = set_command.replaceAll(" ", "");
            if (set_command.equals("done")) {
                if (editor.areAllValuesSet()) {
                    editor.DisplayToConsole();
                    break;
                } else {
                    Utilities.println("Please set all the values first before proceeding.");
                    continue;
                }
            }
            boolean isCommandValid = Utilities.isValidSetCommand(set_command, ElementsCount);
            if (!isCommandValid) {
                Utilities.println("Invalid format please use id=value");
                continue;
            }
            SetCommand command = Utilities.ParseSetCommand(set_command);
            if (command == null) { Utilities.println("Failed to parse this command make sure it's in this format id=value"); continue;}
            editor.setCellValue(command.getId(), command.getValue());
            editor.DisplayToConsole();
            Utilities.println("Write 'done' if you're finished from inputting values.");
            Utilities.println("-----------------------------------------------");
        }

        MatrixContainer calculationArray = new MatrixContainer(editor.GetEditedArray(), xLength, yLength);
        Approach approach = null;
        Utilities.println("Please select the approach you want by inputting the number:");
        Utilities.println("1. Maximax");
        Utilities.println("2. Maximin");
        Utilities.println("3. Criterion of realism (Hurwicz)");
        Utilities.println("4. Equally Likely (Laplace)");
        Utilities.println("5. Minimax regret");

        while (true) {
            int userApproachNumber = userInput.nextInt();
            if (userApproachNumber <= 0 || userApproachNumber >= 6) {
                Utilities.println("Approach number is out of range.");
                continue;
            }

            approach = Approach.values()[userApproachNumber - 1];

            if (approach != null)
                break;
        }

        Utilities.println("Selected method " + approach.toString());

        int optimalChoice = calculationArray.CalculateForApproach(approach);

        Utilities.println("Optimal Choice is " + optimalChoice);

    }
}