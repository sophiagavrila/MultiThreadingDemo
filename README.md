# ThreadDemo
This Repository is a collection of demos for teaching MultiThreading in Java 8:

## :one: Thread Creation

- Thread Creation (method #1): `com.example.A`
- Thread Exception: `com.example.A`
- Thread Creation (method #2): `com.example.B`
- MultiThreaded Application (Vault Heist with 3 competing Threads): `com.example.B`
- MultiExecutor Challenge + Solution: `com.example.B.MultiExecutorChallenge.java`

<br>

## :two: Thread Coordination

- Thread Termination: *Why and When?* `com.example.C`
    - :question: Why stop a thread? 
        - Threads consume resources.  Even when not doing anything, they still consume memory.  When it runs it consumes CPU time and cahce space.
        
        - If a thread finished its work but is still running, we want to clean up the thread's resources.
        
        - If a thread is "misbehaving" we want to stop it (like sending a request to a server that isn't responding, or taking too long to perform a calculation).
        
        - By default, as long as one thread is running, the app will not stop as long as at least one thread is still running ***even if the main thread has already stopped running***.

    - In this section we learn how to stop all of the threads from running, gracefully, **before** closing the application

    > Solution? Use 1 Thread (A) to run an interruption method to interrupt another Thread (B).

    - :question: *When* can we interrupt a Thread?
        1. If the thread executes a method that throws an `InterruptedException`,
        2. If the thread's code is explicitly handling the `interrupt` signal. 

- Dameon Threads: `com.example.D.C_DaemonDemo.java`

    > *Setting a thread as a Daemon Thread with the method* `someThread.setDaemon(true)` *prevaents a thread from blocking our appl from exiting.  This is shown in line 19 of the appended* `B_InterruptionDemo.java`.

    - Background threads that do not prevent the application from exiting if the `main` thread terminates.
    - Background tasks that are not the "main focus" of the application.  They should not block our app from terminating.

    > Example: A thread that saves our work every few minutes to a file in a Text Editor App.  If we suddenly quit, the thread can't complete because app has exited.<br>
    > Think of a video game, when it asks you not to turn off your PS4 or quit the game while it's saving.

- Thread Coordination: `com.example.D`

    - :question: *Why do we need Thread Coordination?*
        - Different Threads run independently.
        - Order of execution is out of our control.
        
        - :thought_balloon: Scenario: Thread A, finishes all work before Thread B.  What if one thread depends on another?
            
            - Thread A calculates a value which is INPUT for Thread B.
            - Thread B relies on Thread A to completely finish its work so it doesn't provide an unfinished calculation.
            - **Best Solution** Thread B goes to sleep to completely get out of the way of Thread A doing the work it needs to accomplish.
            - Thread B only wakes up when Thread A is ready to provide the input to it.
            - **This is accomplished with the** `Thread.join()` **method**.

            > `.join()` tells the calling program to wait for the thread object upon which the method was called to finish its task.

- MultiThreaded Calculation Challenge + Solution: `com.Example.D.MultiThreadedCalculationChallenge.java`

<br>

## :three: Intro to Performance Optimization

- Performance Criteria
    - **Performance** can be measured differently. 
        
        - **Latency Example**: Performance can be measured by latency measured in units of time.  Think about an eCommerce platform. *How fast does an application process a request and deliver a reponse to the user?*.

        - **Throughput Example**: If an app uses Machine Learning to take in data and deliver a prediction, the performance metric would be **throughput** in which the more data the app can inject in 24 hours, the better.

    > These are independent metrics.  Improving one, may have no effect on the other.

### Latency:
The time it takes to complete a task, measured in time units. :brain: **How can we reduce Latency with MultiThreaded Programming?:

    1. If we have a single task, which can be completed by a single thread, sequentially, (`Time = T`) we can break that single task into *multiple tasks*.
    2. `Latency = T/N`, tasks over time. `N` = Number of Subtasks.
    3. Goal is to reduce time it takes to do the task by dividing the task into N amount.  `N = ?`.

    :question: How many subtasks can the original task be broken into? i.e what is N?
        - Typically N is closest to number of Cores of your computer. Then OS can utilize the CPU's hardware the best it can, and delegate 1 core to each subtask.
        - `# of threads = # of cores` (rarely optimal), while also assuming nothing else is running on that computer UNLESS, there is a dedicated server.

        > Remember that not all tasks can be broken, including single thread, and simple tasks.

### Optimizing for Latency - Image Processing
- Single Threaded Program: `com.example.E.A_SingleThreadedReColorer.java`;
- Multi Threaded Program: `com.example.E.MultiThreadedReColorer.java`;

    > **Take-away**: The MultiThreaded Program has an additional method, `recolorPixelMultiThreaded()` which takes in a number of threads and divided the picture into sections upon which each thread is tasked with recoloring.  By measuring `System.currentTimeMillis()` before and after invoking the recoloring method and passing `int numberOfThreads` as an argument, we can see how the time is significantly reduced, compared to our previous Single Threaded method, `recolorPixelSingleThreaded()`.


    - We can improve the performance by allowing multiple threads to do the work in less time.  This is done by partitioning the problem into multiple sub-problems.  Thus, Latency = Time divided by Number of Tasks, `L = T/N`.

## Throughput
The **amount of tasks** completed in a given period of time, measured in tasks per time unit. 