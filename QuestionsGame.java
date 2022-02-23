//20Questions
//Creates class QuestionMain which prompts the user to play
//the 20 questions game that can also be generated from
//an existing saved game


import java.util.*;
import java.io.*;

public class QuestionsGame {

    private QuestionNode overallRoot;
    private Scanner console;
    
    //Constructs a new questions game, 
    //starting with a computer as the first
    //thing guessed by the console
    public QuestionsGame() {
        this.console = new Scanner(System.in);
        this.overallRoot = new QuestionNode("computer");
    }
    
    //takes in a standard formatted text file with
    //Scanner input and creates a template for a questions 
    //game that the computer can play
    public void read(Scanner input) {
        this.overallRoot = readHelper(input);
    }
    
    //Creates a binary tree from a standard formatted text file using
    //Scanner input, creates a Leaf QuestionNode if the passed
    //in line from input begins with an answer
    //returns a QuestionNode root
    private QuestionNode readHelper(Scanner input) {
            if (input.nextLine().equals("A:")) {
                QuestionNode answer = new QuestionNode(input.nextLine());
                return answer;
            } else {
                QuestionNode current = new QuestionNode(input.nextLine(), 
                                                        readHelper(input), readHelper(input));
                return current;
            }
    }
    
    //Saves the current questions game using PrintStream output
    //into a pre-formatted file
    public void write(PrintStream output) {
        write(output, this.overallRoot);
    }
    
    //recursively saves the QuestionNode current(s) in a pre-order traversal,
    //and prints them out to a PrintStream output file
    private void write(PrintStream output, QuestionNode current) {
        if (current != null) {
            String type = "Q:";
            if (current.left == null && current.right == null) {
                type = "A:";
            }
            output.println(type);
            output.println(current.data);
            write(output, current.left);
            write(output, current.right);
        }
    }
    
    //prompts user to play a questions game
    //if the computer guesses wrong, takes in new user
    //object and adds it to the choices of objects it considers
    public void askQuestions() { 
        overallRoot = askQuestionsHelper(overallRoot);
    }

    //recursively creates two new QuestionNodes for the tree if 
    //the computer reaches a leafNode and guesses wrong,
    //rearranges Nodes so that the new inputted question
    //will have branches to the new object and the object guessed incorrectly
    //returns a QuestionNode root to the new tree
    private QuestionNode askQuestionsHelper(QuestionNode current) {
        if (current.left == null && current.right == null) {
            if (yesTo("Would your object happen to be " + current.data + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String object = this.console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String question = this.console.nextLine();
                if (yesTo("And what is the answer for your object?")) {
                    QuestionNode qNode = new QuestionNode(question, 
                                                          new QuestionNode(object), current);
                    return qNode;
                } else {
                    QuestionNode qNode = new QuestionNode(question, current, 
                                                          new QuestionNode(object));
                    return qNode;
                }
            }
        } else {
            if (yesTo(current.data)) {
                current.left = askQuestionsHelper(current.left);
            } else {
                current.right = askQuestionsHelper(current.right);
            }
        }
        return current;
    }


    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
    
    //Creates class QuestionNode that stores a yes or no question, 
    //with its respective answers, or more yes or no questions.
    private static class QuestionNode {
        public QuestionNode left;
        public QuestionNode right;
        public String data;
    
        //Creates a QuestionNode with String data value
        public QuestionNode(String data) {
            this(data, null, null);
        }

        //Creates a QuestionNode node with String data value
        //and QuestionNode yes and QuestionNode no
            public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
            this.data = data;
            this.left = yes;
            this.right = no;
        }
    }
}
