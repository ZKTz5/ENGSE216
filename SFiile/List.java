package SFiile;
public class List {

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
            data[count] = d;
            count++;
        } else {
            System.out.println("Collection is full.");
        }
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

    int size() {
        return count;
    }

    void delete(int k) {
        for (int i = k; i < count - 1; i++) {
            data[i] = data[i + 1];
        }
        count--;
    }

    void insert(int k, int item) {
        if(!isFull()){
            if (k >= 0 && k < count) {
                for (int n = count - 1; n >= k; n--) {
                    data[n + 1] = data[n];
                }
                data[k] = item;
                count++;
            } 
        }
    

    }

}
