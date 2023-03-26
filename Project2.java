import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Alex Kempen - abk200001
 * CS 4348.001
 */

class Project2 {
    // Global variables used to control the program
    public static int CUSTOMERS = 50;
    public static int WORKERS = 3;
    public static double TIME_SCALE = 0.1; // set to less than 1 to make time run faster

    public static void main(String[] args) throws InterruptedException {
        PrintStream out = System.out;

        Resources resources = new Resources(out);
        out.println(
                String.format("Simulating Post Office with %d customers and %d postal workers",
                        CUSTOMERS,
                        WORKERS));
        out.println();

        List<PostalWorker> workers = initializeThreads(() -> new PostalWorker(resources), WORKERS);
        List<Customer> customers = initializeThreads(() -> new Customer(resources, Task.pick()), CUSTOMERS);

        customers.forEach(Project2::join);
        workers.forEach(PostalWorker::interrupt);
    }

    /**
     * Creates count threads using the given constructor and starts them.
     */
    public static <T extends ThreadBase> List<T> initializeThreads(Supplier<T> supplier, int count) {
        return Stream.generate(supplier).limit(count).peek(T::start).collect(Collectors.toList());
    }

    /**
     * Joins the given thread.
     * Throws if the thread could not be joined.
     */
    private static void join(ThreadBase thread) {
        try {
            thread.join();
            thread.print("joined");
        } catch (InterruptedException e) {
            throw new AssertionError("Failed to join thread", e);
        }
    }
}

/**
 * An enum class defining tasks.
 * Includes methods for printing relevant messages.
 */
enum Task {
    BUY_STAMPS("buy%s stamps"),
    MAIL_LETTER("mail%s a letter"),
    MAIL_PACKAGE("mail%s a package");

    private final String message;

    private Task(final String message) {
        this.message = message;
    }

    /*
     * Randomly chooses a task.
     */
    public static Task pick() {
        return Task.values()[new Random().nextInt(Task.values().length)];
    }

    public String finishedMessage() {
        return String.format("finished " + this.message, "ing");
    }

    public String startMessage(int worker_id) {
        return "asks postal worker " + worker_id + " to " + String.format(this.message, "");
    }
}

/**
 * A class defining resources shared between threads.
 */
class Resources {
    Resources(PrintStream out) {
        this.out = out;
    }

    public Semaphore max_capacity = new Semaphore(10);

    public Queue<Integer> customers_ids = new LinkedList<>();
    public Semaphore customer_ready = new Semaphore(0);
    public Semaphore customer_mutex = new Semaphore(1);
    public List<Semaphore> workers_finished = Collections.nCopies(Project2.CUSTOMERS, new Semaphore(0));

    public List<Integer> worker_ids = new ArrayList<>(Collections.nCopies(Project2.CUSTOMERS, -1));
    public List<Semaphore> worker_ids_ready = Collections.nCopies(Project2.CUSTOMERS, new Semaphore(0));

    // create list of tasks with arbitrary value
    public List<Task> tasks = new ArrayList<>(Collections.nCopies(Project2.WORKERS, Task.BUY_STAMPS));
    public List<Semaphore> tasks_ready = Collections.nCopies(Project2.WORKERS, new Semaphore(0));

    public PrintStream out;
}

abstract class ThreadBase extends Thread {
    protected Resources resources;
    private String name;
    protected int id;

    protected ThreadBase(Resources resources, String name) {
        this.resources = resources;
        this.name = name;
    }

    protected void setId(int count) throws InterruptedException {
        this.id = count;
        print("created");
    }

    protected void print(String message) {
        resources.out.println(String.format("%s %d " + message, name, id));
    }

    protected void sleep(double seconds) throws InterruptedException {
        Thread.sleep((long) (Project2.TIME_SCALE * 1000 * seconds));
    }
}

/**
 * A class defining a customer.
 */
class Customer extends ThreadBase {
    private Task task;
    private static Semaphore mutex = new Semaphore(1);
    private static Integer count = 0;

    public Customer(Resources resources, Task task) {
        super(resources, "Customer");
        this.task = task;
    }

    @Override
    public void run() {
        try {
            mutex.acquire();
            setId(count++);
            mutex.release();
            runCustomer();
        } catch (InterruptedException e) {
            throw new AssertionError("Customer should not be interrupted", e);
        }
    }

    private void runCustomer() throws InterruptedException {
        resources.max_capacity.acquire();
        print("enters post office");

        resources.customer_mutex.acquire();
        resources.customers_ids.add(id);
        resources.customer_ready.release();
        resources.customer_mutex.release();

        resources.worker_ids_ready.get(id).acquire();
        int worker_id = resources.worker_ids.get(id);

        resources.tasks.set(worker_id, this.task);
        print(task.startMessage(worker_id));
        resources.tasks_ready.get(worker_id).release();

        resources.workers_finished.get(id).acquire();
        print(task.finishedMessage());

        print("leaves post office");
        resources.max_capacity.release();
    }
}

class PostalWorker extends ThreadBase {
    private static Semaphore mutex = new Semaphore(1);
    private static Integer count = 0;

    private static Semaphore scale_mutex = new Semaphore(1);

    public PostalWorker(Resources resources) {
        super(resources, "Postal worker");
    }

    @Override
    public void run() {
        try {
            mutex.acquire();
            setId(count++);
            mutex.release();

            while (true) {
                runPostalWorker();
            }
        } catch (InterruptedException expected) {
            // expected
        }
    }

    private void useScale(double seconds) throws InterruptedException {
        scale_mutex.acquire();
        resources.out.println("Scales in use by postal worker " + id);
        sleep(seconds);
        resources.out.println("Scales released by postal worker " + id);
        scale_mutex.release();
    }

    private void runPostalWorker() throws InterruptedException {
        resources.customer_ready.acquire();
        resources.customer_mutex.acquire();
        int customer_id = resources.customers_ids.remove();
        resources.customer_mutex.release();

        resources.worker_ids.set(customer_id, id);
        resources.worker_ids_ready.get(customer_id).release();

        resources.tasks_ready.get(id).acquire();
        Task task = resources.tasks.get(id);

        print("serving customer " + customer_id);
        if (task == Task.BUY_STAMPS) {
            sleep(1.0d);
        } else if (task == Task.MAIL_LETTER) {
            useScale(1.5);
        } else if (task == Task.MAIL_PACKAGE) {
            useScale(2);
        }
        print("finished serving customer " + customer_id);
        resources.workers_finished.get(customer_id).release();
    }
}