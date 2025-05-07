class Buffer{
    int data;
    boolean isEmpty = true;
    synchronized void produce(int value) {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        data = value;
        isEmpty = false;
        System.out.println("Produced: " + data);
        notify();
    }
    synchronized int consume() {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        isEmpty = true;
        System.out.println("Consumed: " + data);
        notify();
        return data;
    }
}
class Producer extends Thread {
    Buffer buffer;
    Producer(Buffer b) {
        this.buffer = b;
    }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.produce(i);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}
class Consumer extends Thread {
    Buffer buffer;
    Consumer(Buffer b) {
        this.buffer = b;
    }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.consume();
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}



public class third {
    public static void main(String[] args) {
        Buffer b = new Buffer();
        Producer p = new Producer(b);
        Consumer c = new Consumer(b);
        p.start();
        c.start();
    }
}
