// Zach Ogburn
// COSC 5366.001
// 3/20/2021
// Dr. Brown
//Assignment 3 A
// This program acts as a PDA and will determine whether or not a string is accepted in a defined language

import java.util.*;
public class Assignment3a
{
    public static void main(String[] args) {
        //create list of the alphabet of the language
        List<String> terminalList = Arrays.asList("z","x","e","a","y","$");
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter a string to test: ");
        //get input string to test
        String inputStr = myScanner.nextLine();

        //splint input string into a list of chars
        List<String> inputList = new ArrayList<String>(Arrays.asList(inputStr.split("")));
        inputList.add("$");

        //create nested parser table and add all relevant rows to it
        Map<String, Map<String, String>> parserTable = new HashMap<>();

        List<String> stack = new ArrayList<String>();
        stack.add("G");

        Map<String, String> G = new HashMap<>();
        G.put("z", "S$");
        G.put("x", "S$");
        G.put("e", "S$");
        Map<String, String> S = new HashMap<>();
        S.put("z", "zaT");
        S.put("x", "Ue");
        S.put("e", "Ue");
        Map<String, String> T = new HashMap<>();
        T.put("x", "epsilon");
        T.put("e", "epsilon");
        T.put("a", "aS");
        T.put("y", "yaT");
        T.put("$", "epsilon");
        Map<String, String> U = new HashMap<>();
        U.put("x", "xaTU");
        U.put("e", "epsilon");
        U.put("$", "epsilon");

        parserTable.put("G", G);
        parserTable.put("S", S);
        parserTable.put("T", T);
        parserTable.put("U", U);

        int x = 0;

//loop through the input string and perform the algorithm to check if it is accepted
        while (x < inputList.size()) {
            //input character currently being read
            String ch = inputList.get(x);
            //pop top of stack
            String topStack = stack.remove(stack.size()-1);
            //check if top of stack is a terminal
            if(terminalList.contains(topStack)) {
                if(topStack.equals(ch)){
                    x += 1;
                    //end of input string, stack empty
                    if (topStack.equals("$")) {
                        System.out.println("String accepted!");
                        break;
                    }
                    continue;
                } else {
                    //issue with input string, not accepted
                    System.out.println("String NOT accepted!");
                    break;
                }
                //top of stack is a nonterminal, check parser table and add to stack
            } else if (!terminalList.contains(topStack)) {
                try {
                    if (parserTable.get(topStack).get(ch).equals("epsilon")) {
                        continue;
                    } else {
                        String tempWord = parserTable.get(topStack).get(ch);

                        for (int i = tempWord.length() - 1; i >= 0; i -= 1) {
                            stack.add(Character.toString(tempWord.charAt(i)));
                        }

                    }
                } catch(Exception e) {
                    System.out.println("Character not in alphabet of language!");
                }
            }
        }
    }
}