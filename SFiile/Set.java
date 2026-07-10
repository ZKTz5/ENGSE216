package SFiile;
public class Set {

    int data[] = new int[5];
    int count;
    boolean found = false;

    boolean search(int item) {
        for (int i = 0; i < count; i++) {
            if (data[i] == item) {
                i = count;
                found = true;
            }
        }
        return found;
    }

    public void add(int d) {
        if (!isFull()) {
            if (!search(d)) {
                data[count] = d;
                count++;
            }
        } else {
            System.out.println("Set is Full.");
        }
    }

    int remove(int id) {
        int temp = -1;
        if (!isEmpty()) {
            temp = data[id];
            data[id] = data[count - 1];
            count--;
        }
        return temp;
    }

    int size() {
        return count;
    }

    boolean isFull() {
        return count == data.length;
    }

    boolean isEmpty() {
        return count == 0;
    }

    void showAll() {
        for (int i = 0; i < count; i++) {
            System.out.println(data[i]);
        }
    }
}
