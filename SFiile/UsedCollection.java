package SFiile;
public class UsedCollection {
    public static void main(String[] args) {
        Stack st = new Stack(10);
        st.push(1);
        st.push(2);
        st.push(3);
        st.push(4);
        st.push(5);
        st.push(6);
        System.out.println(st.pop());
    }
}