//Ali Gökçek   10.11.2023

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static PrintWriter out;

    public static void main(String[] args) throws FileNotFoundException {
        //CREATING SCANNER AND WRITER OBJECTS
        String input_file_name = args[0];
        String output_file_name = args[1];
        File file = new File(input_file_name);
        Scanner scanner = new Scanner(file);
        out = new PrintWriter(output_file_name);

        //TAKING INFO OF THE BOSS
        String line = scanner.nextLine();
        String[] lineArray = line.split(" ");
        String name = lineArray[0];
        Float gms = Float.parseFloat(lineArray[1]);


        Family family = new Family();   //CREATING CUSTOM AVL TREE
        family.insert(gms, name);

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] strArray = line.split(" ");
            switch (strArray[0]) {

                //FUNCTIONALITIES
                case "MEMBER_IN":
                    String name1 = strArray[1];
                    Float gms1 = Float.parseFloat(strArray[2]);
                    family.insert(gms1, name1);
                    break;

                case "MEMBER_OUT":
                    Float gms2 = Float.parseFloat(strArray[2]);
                    family.remove(gms2);
                    break;

                case "INTEL_TARGET":  //CMD TO FIND THE LOWEST COMMON ANCESTOR
                    Float gms3 = Float.parseFloat(strArray[2]);
                    Float gms4 = Float.parseFloat(strArray[4]);
                    family.findTarget(gms3, gms4);
                    break;

                case "INTEL_DIVIDE":   //CMD TO FIND THE MAX NUMBER OF INDEPENDENT MEMBERS
                    family.divide();
                    break;

                case "INTEL_RANK":     //CMD TO FIND THE NEIGHBORS WITH THE SAME LEVEL OF GIVEN MEMBER
                    Float gms5 = Float.parseFloat(strArray[2]);
                    family.rankAnalysis(gms5);
                    break;

            }

        }
        out.close();
        scanner.close();
    }

}