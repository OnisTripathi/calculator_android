package com.example.hw3_v3;

import java.text.DecimalFormat;
import java.util.Stack;

public class Calculator {

    public double evaluate(String equation){
        //Stack for Numbers
        Stack<Double> operands = new Stack<>();

        DecimalFormat df = new DecimalFormat("0.00");

        //Stack for operators
        Stack<Character> operations = new Stack<>();

        //loop through string of curr line
        for(int i=0; i<equation.length();i++) {
            //either operand or operator
            char ch = equation.charAt(i);
            if(Character.isDigit(ch)){
                //we can't just add bc value might be greater than 9
                double num = 0.00;
                //loop through until you get to edge of number
                while (Character.isDigit(ch)) {
                    num = num*10 + (ch-'0');
                    i++;
                    if(i < equation.length())
                        ch = equation.charAt(i);
                    else
                        break;
                }
                if(ch=='.'){
                    i++;
                    if(i < equation.length()) {
                        ch = equation.charAt(i);
                    }
                    while (Character.isDigit(ch)) {
                        num = num + ((ch-'0')/10.00);
                        i++;
                        if(i < equation.length())
                            ch = equation.charAt(i);
                        else
                            break;
                    }
                }
//                if(ch=='.'){
//                    i++;
//                    if(i < equation.length()) {
//                        ch = equation.charAt(i);
//                    }
//                    while (Character.isDigit(ch)) {
//                        num = num/10 + (ch-'0');
//                        i++;
//                        if(i < equation.length())
//                            ch = equation.charAt(i);
//                        else
//                            break;
//                    }
//                }
                i--;
                //go back
                //put to operand stack
                operands.push(Double.parseDouble(df.format(num)));
            }
            //left parentheses
            else if(ch=='('){
                //operators stack
                operations.push(ch);
            }
            //right parentheses will find left one then do operation until it does
            else if(ch==')') {
                while(operations.peek()!='('){
                    double output = performOperation(operands, operations);
                    operands.push(output);
                }
                operations.pop();
            }
            //operator sign (+,-, etc)
            else if(isOperator(ch)){
                //precedence will decide which operator should get popped or not
                while(!operations.isEmpty() && precedence(ch)<=precedence(operations.peek())){
                    double output;
//                    if(ch=='%'){
//                        output = performModuloOperation(operands, operations);
//                    }
//                    else {
                        output = performOperation(operands, operations);
//                    }
                    operands.push(output);
                }
                operations.push(ch);
            }
        }
        //If here means entire expression has been processed,
        //Perform the remaining operations in stack to the numbers stack

        while(!operations.isEmpty()){
            double output = performOperation(operands, operations);
            //push it back to stack
            operands.push(output);
        }
        return operands.pop();
    }

//       order of magnitude (PEMDAS)
//        3. ^, √, log (x temp char) , ln (y temp char)
//        2. High: *, /, %
//        1. Low: +, -
//        -1. wild:
    static int precedence(char ch){
        if (ch == '+' || ch == '-') {
            return 1;
        }
        else if (ch == '*' || ch == '/'|| ch == '%') {
            return 2;
        }
        //y for ln
        else if (ch == '^'|| ch == '√' || ch == 'y') {
            return 3;
        }
        return -1;
    }

//    public double performModuloOperation(Stack<Double> numbers, Stack<Character> operations) {
//        double a = numbers.pop();
//        char operation = operations.pop();
//
//        if(operation=='%'){
//            return a/100;
//        }
//
//        return 0;
//    }

    public double performOperation(Stack<Double> numbers, Stack<Character> operations) {

        double a = numbers.pop();
        double b = numbers.pop();

        char operation = operations.pop();

        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '^':
                return Math.pow(b, a);
            case '%':
                return b % a;
            case '√':
                return (Math.sqrt(a));
            case '/':
                return b / a;
            case 'y'://ln
                return (Math.log(a));
        }
        return 0;
    }

    public boolean isOperator(char ch){
        return ch == '/' || ch == '*' || ch == '+' || ch == '-' || ch == '^' || ch == '√' || ch == 'y';
    }

////
////    //two numbers used in operation
////    double firstNum;
////    double lastNum;
////
////    //received from main activity
//    String currLine;
//    double answer=0;
//    DecimalFormat format = new DecimalFormat("0.##");
////
////    //operation variable
////    int operation=0; //no operation = -1
////
////    //actual text space, showing numbers
////    TextView workspace;
////
////    //tool to help round numbers to 7 decimals
////    DecimalFormat df;
//
//    //constructor
//    public Calculator(String currLine){
//        //set values
//        this.currLine=currLine;
//        this.answer=answer;
////        this.lastNum = lastNum;
////        this.firstNum=firstNum;
//
//        String postFix = infixToPostfix(currLine);
//        answer = postfixToAnswer(postFix);
//    }
//
//    //will let us know if value is number or operator
//    //decides whether we put in stack or que
//    public static boolean isDigit(char c) {
//        // boolean check for digit
//        //char was an operator
//        return Character.isLetterOrDigit(c);
//    }
//        order of magnitude (PEMDAS)
//        4. Immediate: 0, ]
//        3. ^
//        2. High: *, /, %
//        1. Low: +, -
//        -1. wild: (
//    public static int getPrecedence(char ch) {
//        if (ch == '+' || ch == '-') {
//            return 1;
//        }
//        else if (ch == '*' || ch == '/') {
//            return 2;
//        }
//        else if (ch == '^') {
//            return 3;
//        }
//        else
//            return -1;
//    }
//    //set new value on calculate
//
//    public String getAnswer(){
//        return String.valueOf(answer);
//    }
//
//    public static String infixToPostfix(String equation) {
//        // will return in postfix format
//        StringBuilder postFix = new StringBuilder();
//        //new stk to separate digit vs operator
//        Stack<Character> stk = new Stack<>();
//
//        //to handle value greater than 10
//        int multiplyAmount=10;
//
//        //go through every character
//        for (int i = 0; i<equation.length(); ++i) {
//            //operator or digit?
//            char c = equation.charAt(i);
//
//            //figure out if digit or letter (determine where to go)
//
//            if(isDigit(c)){
//                postFix.append(c);
//                for (int j = 0; j <equation.length(); j++) {
//                    char nextChar = equation.charAt(j);
//                    if(isDigit(nextChar)){
//                        int combined = (int) ((Double.parseDouble(String.valueOf(c))*multiplyAmount) + Double.parseDouble(String.valueOf(nextChar)));
//                        postFix.a
//                    }
//                }
//                //append second char
//            }
//            // auto pushed to stack bc special exception
//            else if (c == '(') {
//                stk.push(c);
//            }
//            //keep popping until ')' is reached (PEMDAS)
//            else if (c == ')') {
//                while (!stk.isEmpty() && stk.peek() != '(')
//                    postFix.append(stk.pop());
//                stk.pop();
//            }
//            //is char is operator
//            else {
//                while (!stk.isEmpty() && getPrecedence(c) <= getPrecedence(stk.peek())){
//                    postFix.append(stk.pop());
//                }
//                stk.push(c);
//            }
//        }
//        // pop all the operators from the stk(change return
//        while (!stk.isEmpty()){
//            if(stk.peek() == '(')
//                return "Invalid Expression";
//            postFix.append(stk.pop());
//        }
//        return postFix.toString();
//    }
//
//    public static int postfixToAnswer(String equation) {
//        //stk object where equation are going to happen
//        Stack<Integer> stk=new Stack<>();
//
//        // Scan all characters one by one
//        for(int i=0;i<equation.length();i++){
//            //operator or digit?
//            char c=equation.charAt(i);
//
//            //char is op
//            if(isDigit(c)){
//                stk.push(c - '0');
//            }
//            //  If the scanned character is an operator, pop two
//            // elements from stk apply the operator
//
//            else{
//                int val1 = stk.pop();
//                int val2 = stk.pop();
//
//                switch(c){
//                    case '+':
//                        stk.push(val2+val1);
//                        break;
//
//                    case '-':
//                        stk.push(val2- val1);
//                        break;
//
//                    case '/':
//                        stk.push(val2/val1);
//                        break;
//
//                    case '*':
//                        stk.push(val2*val1);
//                        break;
//                }
//            }
//        }
//        //the top is answer
//        return stk.pop();
//    }
}
