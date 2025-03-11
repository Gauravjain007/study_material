package com.study.threads;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <h4>Virtual Threads:</h4>
 * <p>
 * Virtual threads are lightweight threads that greatly minimize the effort
 * required to create, operate, and manage high volumes systems that are
 * concurrent. As a result, they are more efficient and scalable than standard
 * platform threads.
 * 
 * <p>
 * Advantages of Java Virtual Threads:
 * <ul>
 * <li>Increases the availability of applications</li>
 * <li>Enhances application throughput.</li>
 * <li>Reduces the occurrence of 'OutOfMemoryError: Unable to Create New Native
 * Thread'.</li>
 * <li>Reduces the amount of memory used by the application</li>
 * <li>Enhances code quality</li>
 * <li>Platform Threads are completely compatible with them.</li>
 * </ul
 * 
 * @version JAVA 21.0
 */
public class VirtualThreadImpl {

    /**
     * Basic example of using a virtual thread builder.
     * <p>
     * This method creates a virtual thread using the Thread.Builder API and
     * waits for it to complete.
     * <p>
     * The method prints the thread details and the result of the task once it
     * is available.
     * <p>
     * This example is intended to be as simple as possible to demonstrate the
     * use of virtual threads with the Thread.Builder API.
     */
    public void virtualThreadWithThreadBuilder(String threadName) {
        System.out.println("\n=== Basic Virtual Thread Builder ===");
        try {
            Thread.Builder builder = Thread.ofVirtual().name(threadName);

            Thread thread = builder.start(() -> {
                // Simulate work
                Thread currentThread = Thread.currentThread();
                currentThread.setName(threadName);
                System.out.println("Task running in: " + currentThread);
                System.out.println("Is virtual: " + currentThread.isVirtual());
            });
            System.out.println("Thread: " + thread);

            Thread.sleep(1000);
            thread.join(); // Wait for the thread to complete
        } catch (InterruptedException e) {
            System.out.println("ERROR: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Basic example of using a virtual thread per task executor.
     * <p>
     * This method submits a single task to the executor and waits for the result.
     * The task simply prints its name and the fact that it is a virtual thread,
     * then
     * sleeps for a second and returns a message.
     * <p>
     * The method prints the result of the task once it is available.
     * <p>
     * This example is intended to be as simple as possible to demonstrate the
     * use of virtual threads with an executor.
     */
    public void basicVirtualThreadExecutor(String threadName) {
        System.out.println("\n=== Basic Virtual Thread Executor ===");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit a task
            Future<String> future = executor.submit(() -> {
                // Simulate work
                Thread currentThread = Thread.currentThread();
                currentThread.setName(threadName);
                System.out.println("Task running in: " + currentThread);
                System.out.println("Is virtual: " + currentThread.isVirtual());
                Thread.sleep(1000);
                return "Task completed by " + currentThread.getName();
            });

            // Get the result (waits for the task to complete)
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("ERROR: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Compares the performance of platform threads and virtual threads.
     *
     * This method tests the execution of a specified number of concurrent tasks
     * using
     * two types of executors: a fixed thread pool for platform threads and a
     * virtual
     * thread per task executor for virtual threads. It delegates the task execution
     * to the `testExecutor` method, which measures and prints the time taken to
     * complete
     * all tasks for each executor type.
     *
     * @param threadCount the number of concurrent tasks to be executed
     */
    public void compareThreadTypes(int threadCount) {
        System.out.println("\n=== Platform Threads vs Virtual Threads ===");

        // Number of concurrent tasks
        final int TASK_COUNT = threadCount;

        // Test with platform threads
        testExecutor("Platform Threads", Executors.newFixedThreadPool(100), TASK_COUNT);

        // Test with virtual threads
        testExecutor("Virtual Threads", Executors.newVirtualThreadPerTaskExecutor(), TASK_COUNT);
    }

    /**
     * Tests the performance of the given executor type with the given number of
     * tasks.
     * 
     * Submits tasks that block for 50ms each and measures the time taken to
     * complete all tasks.
     * Prints the result, including the number of tasks completed and the duration
     * in milliseconds.
     * 
     * @param type      the type of executor to test (e.g. "Platform Threads" or
     *                  "Virtual Threads")
     * @param executor  the executor to test
     * @param taskCount the number of tasks to submit
     */
    public void testExecutor(String type, ExecutorService executor, int taskCount) {
        try {
            System.out.println("Testing " + type + "...");
            long startTime = System.currentTimeMillis();

            List<Future<?>> futures = new ArrayList<>();
            // Submit tasks that block for 50ms each
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(() -> {
                    try {
                        // Simulate I/O blocking
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
            }
            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                future.get();
            }
            long duration = System.currentTimeMillis() - startTime;
            System.out.println(type + " completed " + taskCount +
                    " blocking tasks in " + duration + "ms");

        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            // Shutdown the executor
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Simulates handling 10,000 concurrent HTTP requests with a virtual thread per
     * task executor. This demonstrates how virtual threads can be used to improve
     * the performance and scalability of web servers by allowing them to handle
     * many more concurrent requests than would be possible with platform threads.
     *
     * <p>
     * Each request is simulated with certain steps and after there completion for
     * every 1000th request, the method logs a message to the console.
     * </p>
     */
    public void simulateWebServer() {
        // Create a virtual thread per task executor for handling HTTP requests
        try (ExecutorService requestExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Simulate handling 10,000 concurrent HTTP requests
            for (int i = 0; i < 10_000; i++) {
                final int requestId = i;
                requestExecutor.submit(() -> {
                    try {
                        // Simulate request processing steps

                        // 1. Parse request (CPU-bound) - fast
                        Thread.sleep(1);

                        // 2. Query database (I/O-bound) - slow
                        Thread.sleep(Duration.ofMillis(100));

                        // 3. Process business logic (CPU-bound) - medium
                        Thread.sleep(20);

                        // 4. Call external service (I/O-bound) - slow
                        Thread.sleep(Duration.ofMillis(200));

                        // 5. Generate response (CPU-bound) - fast
                        Thread.sleep(5);

                        // Log completion (in real app, would return HTTP response)
                        if (requestId % 1000 == 0) {
                            System.out.println("Request " + requestId + " completed");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                });
            }

            // In a real server, we would not shutdown like this,
            // but would keep the executor running until server shutdown
        }
    }

    public static void main(String[] args) {
        VirtualThreadImpl virtualThreadImpl = new VirtualThreadImpl();

        // Create a virtual thread using Thread Builder
        virtualThreadImpl.virtualThreadWithThreadBuilder("Thread-1");

        // Create a virtual thread using Executor
        virtualThreadImpl.basicVirtualThreadExecutor("Thread-2");

        // Compare platform threads vs virtual threads
        virtualThreadImpl.compareThreadTypes(1000);

        // Simulate a web server with virtual threads
        virtualThreadImpl.simulateWebServer();
    }
}
