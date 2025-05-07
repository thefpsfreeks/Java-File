import java.util.*;

public class first{
    public static void main(String[] args) {
         Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack: " + stack);
        System.out.println("Popped from stack: " + stack.pop());
        System.out.println("New Stack: " +stack);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(100);
        queue.add(200);
        queue.add(300);
        System.out.println("Queue: " + queue);
        System.out.println("Removed from queue: " + queue.remove());
        System.out.print("New Queue: " + queue);

    }
}