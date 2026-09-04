import java.util.Stack;

public class InfixConverter {

    private static int stackPriority(char op) {
        if (op == '^') return 3;
        if (op == '*' || op == '/' || op == '%') return 2;
        if (op == '+' || op == '-') return 1;
        if (op == '(') return 0;
        return -1;
    }

    private static int inputPriority(char op) {
        if (op == '^' || op == '(') return 4;
        if (op == '*' || op == '/' || op == '%') return 2;
        if (op == '+' || op == '-') return 1;
        return -1;
    }

    public static String convertWithTrace(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-15s | %s\n", "Input", "Output", "Stack", "Action");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            if (ch == ' ') continue;

            String action = "";

            if (Character.isLetterOrDigit(ch)) {
                output.append(ch).append(" ");
                action = "Operand -> Output";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            } else if (ch == '(') {
                stack.push(ch);
                action = "Push '(' to Stack";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    char popped = stack.pop();
                    output.append(popped).append(" ");
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
                action = "Pop until '(', discard both";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            } else if (Validator.isOperator(ch)) {
                while (!stack.isEmpty() && inputPriority(ch) <= stackPriority(stack.peek())) {
                    char popped = stack.pop();
                    output.append(popped).append(" ");
                }
                stack.push(ch);
                action = "Push '" + ch + "' to Stack";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            }
        }

        while (!stack.isEmpty()) {
            char popped = stack.pop();
            output.append(popped).append(" ");
            printRow("End", output.toString(), getStackString(stack), "Pop remaining '" + popped + "'");
        }

        System.out.println("----------------------------------------------------------------------");
        return output.toString().trim();
    }

    private static String getStackString(Stack<Character> stack) {
        if (stack.isEmpty()) return "Empty";
        StringBuilder sb = new StringBuilder();
        for (char c : stack) sb.append(c).append(" ");
        return sb.toString().trim();
    }

    private static void printRow(String in, String out, String stk, String act) {
        System.out.printf("%-10s | %-25s | %-15s | %s\n", in, out.trim(), stk, act);
    }
}