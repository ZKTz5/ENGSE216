import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfixTracer {

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }

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

    public static boolean checkValid(String text) {
        if (text == null || text.trim().isEmpty()) return false;

        int brackets = 0;
        char prev = ' ';
        boolean hasNumber = false;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == ' ') continue;

            if (!Character.isLetterOrDigit(ch) && !isOperator(ch) && ch != '(' && ch != ')') {
                return false;
            }

            if (ch == '(') {
                brackets++;
            } else if (ch == ')') {
                brackets--;
                if (brackets < 0) return false;
                if (isOperator(prev)) return false;
            }

            if (Character.isLetterOrDigit(ch)) {
                hasNumber = true;
                if (prev == ')') return false;
            }

            if (isOperator(ch)) {
                if (prev == ' ' || prev == '(' || isOperator(prev)) return false;
            }

            prev = ch;
        }

        return brackets == 0 && !isOperator(prev) && hasNumber;
    }

    // ฟังก์ชันแปลงพร้อมพิมพ์ตารางทีละ Step
    public static void convertWithTrace(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-15s | %s\n", "Input", "Output", "Stack", "Action");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            if (ch == ' ') continue;

            String action = "";

            // 1. Operand
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch).append(" ");
                action = "Operand -> Output";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            }
            // 2. วงเล็บเปิด '('
            else if (ch == '(') {
                stack.push(ch);
                action = "Push '(' to Stack";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            }
            // 3. วงเล็บปิด ')'
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    char popped = stack.pop();
                    output.append(popped).append(" ");
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // เอา '(' ออก
                }
                action = "Pop until '(', discard both";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            }
            // 4. Operator
            else if (isOperator(ch)) {
                while (!stack.isEmpty() && inputPriority(ch) <= stackPriority(stack.peek())) {
                    char popped = stack.pop();
                    output.append(popped).append(" ");
                }
                stack.push(ch);
                action = "Push '" + ch + "' to Stack";
                printRow(String.valueOf(ch), output.toString(), getStackString(stack), action);
            }
        }

        // 5. ดึงข้อมูลที่เหลือใน Stack
        while (!stack.isEmpty()) {
            char popped = stack.pop();
            output.append(popped).append(" ");
            printRow("End", output.toString(), getStackString(stack), "Pop remaining '" + popped + "'");
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("ผลลัพธ์สุดท้าย (Postfix): " + output.toString().trim());
        System.out.println("----------------------------------------------------------------------\n");
    }

    private static String getStackString(Stack<Character> stack) {
        if (stack.isEmpty()) return "Empty";
        StringBuilder sb = new StringBuilder();
        for (char c : stack) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    private static void printRow(String in, String out, String stk, String act) {
        System.out.printf("%-10s | %-25s | %-15s | %s\n", in, out.trim(), stk, act);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ป้อนนิพจน์ Infix: ");
        String input = sc.nextLine();

        if (!checkValid(input)) {
            System.out.println("ข้อผิดพลาด: รูปแบบสมการไม่ถูกต้อง (Invalid Infix)");
        } else {
            convertWithTrace(input);
        }

        sc.close();
    }
}