# ThreadDemo
This Repository is a collection of demos for teaching MultiThreading in Java 8:

- Thread Creation (method #1): `com.revature.A`
- Thread Exception: `com.revature.A`
- Thread Creation (method #2): `com.revature.B`
- MultiThreaded Application (Vault Heist with 3 competing Threads): `com.revature.B`
- MultiExecutor Challenge + Solution: `com.revature.C`

- Thread Termination: *Why and When?*
    - Why stop a thread? Threads consume resources.  Even when not doing anything, they still consume memory.  When it runs it consumes CPU time and cahce space.
    - If a thread finished its work but is still running, we want to clean up the thread's resources.
    - If a thread is "misbehaving" we want to stop it (like sending a request to a server that isn't responding, or taking too long to perform a calculation).
    - By default, as long as one thread is running, the app will not stop as long as at least one thread is still running ***even if the main thread has already stopped running***.

    - In this section we learn how to stop all of the threads from running, gracefully, **before** closing the application


    > Solution? Use 1 Thread (A) to run an interruption method to interrupt another Thread (B).
