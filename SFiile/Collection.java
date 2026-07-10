package SFiile;
public class Collection {

    int data[] = new int[5];
    int count;

    public void add(int d) {
        if (!isFull()) {
            data[count] = d;
            count++;
        } else {
            System.out.println("Collection is full.");
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
