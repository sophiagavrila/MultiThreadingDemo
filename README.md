# ThreadDemo
This Repository is a collection of demos for teaching MultiThreading in Java 8:

- Thread Creation (method #1): `com.example.A`
- Thread Exception: `com.example.A`
- Thread Creation (method #2): `com.example.B`
- MultiThreaded Application (Vault Heist with 3 competing Threads): `com.example.B`
- MultiExecutor Challenge + Solution: `com.example.C`

- Thread Termination: *Why and When?* `com.example.D`
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

- Thread Coordination: `com.example.E`

    - :question: *Why do we need Thread Coordination?*
        - Different Threads run independently.
        - Order of execution is out of our control.
        
        - :thought_balloon: Scenario: Thread A, finishes all work before Thread B.  What if one thread depends on another?
            
            - Thread A calculates a value which is INPUT for Thread B.
            - Thread B relies on Thread A to completely finish its work so it doesn't provide an unfinished calculation.
            - **Best Solution** Thread B goes to sleep to completely get out of the way of Thread A doing the work it needs to accomplish.
            - Thread B only wakes up when Thread A is ready to provide the input to it.
            - **This is accomplished with the** `Thread.join()` **method**.

    