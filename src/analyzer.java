import java.io.*;

/**
 * shifted into java by esosebee5
 * for my programming languages class
 */

public class analyzer {
    static int charClass;
    static char[] lexeme = new char[100];
    static char nextChar;
    static int lexLen;
    //static int token;
    private static int nextToken;
    private static int index = 0;

    private static final int LETTER = 0;
    private static final int DIGIT = 1;
    private static final int UNKNOWN = 99;
    private static final int EOF = 100;
    private static final int INT_LIT = 10;
    private static final int IDENT = 11;
    private static final int ASSIGN_OP = 20;
    private static final int ADD_OP = 21;
    private static final int SUB_OP = 22;
    private static final int MULT_OP = 23;
    private static final int DIV_OP = 24;
    private static final int LEFT_PAREN = 25;
    private static final int RIGHT_PAREN = 26;

    // something for files
    private static String fileData = "";

    public static void main(String[] args) {
        String fileName = "lex.txt";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //String fileData = "";
            String line = "";
            while (line != null) {
                fileData = fileData + line;
                line = bufferedReader.readLine();
            }
            String[] arr = fileData.split("");

            //for debugging
            System.out.println(fileData);
            for (String a : arr) {
                //System.out.println(a);
            }

            getChar();
            do {
                lex();
            } while (nextToken != EOF);


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /*****************************************************/
    /**
     * addChar - a function to add nextChar to lexeme
     */
    private static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        } else {
            System.out.println("Error - lexeme is too long.");
        }
    }

    /*****************************************************/
    /**
     * getChar - a function to get the next character of
     *  input and determine its character class
     */
    private static void getChar() {
        try {
            nextChar = getNextCharFrom(fileData);
        } catch (StringIndexOutOfBoundsException e) {
//            System.out.println(e);
//            System.out.println("This is the end of the file . . .");
            charClass = EOF;
            return;
        }
        if (Character.isAlphabetic(nextChar)) {
            charClass = LETTER;
        } else if (Character.isDigit(nextChar)) {
            charClass = DIGIT;
        } else charClass = UNKNOWN;
    }

    private static char getNextCharFrom(String fileData) {
        char nextChar = fileData.charAt(index);
        index++;
        return nextChar;
    }

    /*****************************************************/
    /**
     * getNonBlank - a function to call getChar until it
     * returns a non-whitespace character
     */
    private static void getNonBlank() {
        while (Character.isSpaceChar(nextChar)) {
            getChar();
        }
    }

    /*****************************************************/
    /**
     *  lex - a simple lexical analyzer for arithmetic
     *  expressions
     */
    private static int lex() {
        lexeme = new char[100]; //make sure to reset lexeme to empty before you try to fill it again
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            /* Parse identifiers */
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;

            /* Parse integer literals */
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            /* Parentheses and operators */
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            /* EOF */
            case EOF:
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
        } /* End of switch */

        System.out.printf("Next token is: %d, Next lexeme is %s\n",
                nextToken, stringifyArray(lexeme));

        return nextToken;
    }   /* End of function lex */

    /*****************************************************/
    /**
     * lookup - a function to lookup operators and parentheses
     * and return the token
     */
    private static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    private static String stringifyArray(char[] arr) {
        String s = "";
        for (char a : arr) {
            s += a;
        }
        return s;
    }

}
