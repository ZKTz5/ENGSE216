package SFiile;
public class Stack {
    private int arr[];
    int top = -1;

    Stack() {
        this(5);
    }

    public Stack(int size) {
        arr = new int[size];
    }

    void push(int item) {
        if (isFull()) {
            System.out.println("Stack is full.");
        } else {
            top++;
            arr[top] = item;
        }
    }

    int top() {
        int temp = 0;
        if (top < 0) {
            System.out.println("Stack is empty.");
            temp = -1;
        } else {
            temp = arr[top];
        }
        return temp;
    }
    int size() {return top + 1;}

    int pop(){ 
        int temp = 0;
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return -1;
        } else {
            temp = arr[top];
            top--;
        }
        return temp;
    }

    boolean isEmpty() {return top < 0;}

    boolean isFull() {return top+1 == arr.length;}

}