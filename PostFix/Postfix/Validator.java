public class Validator {
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^';
    }

    public static boolean isValidInfix(String text) {
        if (text == null || text.trim().isEmpty()) return false;

        int brackets = 0;
        char prev = ' ';
        boolean hasOperand = false;

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
                hasOperand = true;
                if (prev == ')') return false;
            }

            if (isOperator(ch)) {
                if (prev == ' ' || prev == '(' || isOperator(prev)) return false;
            }

            prev = ch;
        }

        return brackets == 0 && !isOperator(prev) && hasOperand;
    }
}