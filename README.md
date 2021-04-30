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

<br>

### :one: Latency: 
The time it takes to complete a task, measured in time units. :brain: **How can we reduce Latency with MultiThreaded Programming?:

    1. If we have a single task, which can be completed by a single thread, sequentially, (`Time = T`) we can break that single task into *multiple tasks*.
    2. `Latency = T/N`, tasks over time. `N` = Number of Subtasks.
    3. Goal is to reduce time it takes to do the task by dividing the task into N amount.  `N = ?`.

    :question: How many subtasks can the original task be broken into? i.e what is N?
        - Typically N is closest to number of Cores of your computer. Then OS can utilize the CPU's hardware the best it can, and delegate 1 core to each subtask.
        - `# of threads = # of cores` (rarely optimal), while also assuming nothing else is running on that computer UNLESS, there is a dedicated server.

        > Remember that not all tasks can be broken, including single thread, and simple tasks.

<br>

### Optimizing for Latency - Image Processing :camera:
- Single Threaded Program: `com.example.E.A_SingleThreadedReColorer.java`;
- Multi Threaded Program: `com.example.E.MultiThreadedReColorer.java`;

    > **Take-away**: The MultiThreaded Program has an additional method, `recolorPixelMultiThreaded()` which takes in a number of threads and divided the picture into sections upon which each thread is tasked with recoloring.  By measuring `System.currentTimeMillis()` before and after invoking the recoloring method and passing `int numberOfThreads` as an argument, we can see how the time is significantly reduced, compared to our previous Single Threaded method, `recolorPixelSingleThreaded()`.


    - We can improve the performance by allowing multiple threads to do the work in less time.  This is done by partitioning the problem into multiple sub-problems.  Thus, Latency = Time divided by Number of Tasks, `L = T/N`.

<br>

### :two: Throughput
The **number of tasks** completed in a given period of time, measured in tasks per time unit.  (Throughput = # of tasks / time). `Throughput = N/T`.

- **By service each task on a different thread, in paralles, we improve throughput by `N`.** `N` = # of threads *or* cores in cpu

<br>

### Thread Pooling
Creating the thread once and re-using them for future tasks, instead of recreating and restarting thehm. Thread Pools us achieve optimal throughput. 

- Once threads are created, they sit in a pool.
- Tasks are lined up in a queue and distributed to each thread within the pool.
    - If all threads within pool are busy, the tasks wait in the queue for one to free up.

> JDK comes with a few implementations of thread pools, including `Fixed Thread Pool Executor` which creates a thread pool with a fixed # of threads in the pool.

```java
int numberOfThreads = 4;
Executor executor = Executor.newFixedThreadPool(numberOfThreads);

Runnable task = ...;
executor.execute(task);
```
<br>

### Optimizing for Throughput: HTTP Server + Measure Throughput with Apache Jmeter :chart_with_upwards_trend:

- Http Server will send a flow of requests as input.
- Http Server will load a large book from the disc (War and Peace).
- Application acts as a search engine: Client sends us a word like "talk" and the application will search for that word in the book and count how many times that word appears in the novel.
    - The Http Request: `http://127.0.0.1:8000/search?word=talk` :arrow_right: Http Server
- Http Server sends the count of how many times that appears as a response to the user.
    - HttpResponse: `status: 200, body: 3443` :arrow_right:  Client

<br>

### JMeter Testing our Web Server:
[Apache JMeter](https://github.com/jmeter-maven-plugin/jmeter-maven-plugin) is an Apache project that can be used as a load testing tool for analyzing and measuring the performance of a web service.

- Add the plugin to your `pom.xml`:
```xml
<plugin>
    <groupId>com.lazerycode.jmeter</groupId>
    <artifactId>jmeter-maven-plugin</artifactId>
    <version>3.4.0</version>
    <executions>
        <!-- Generate JMeter configuration -->
        <execution>
            <id>configuration</id>
            <goals>
                <goal>configure</goal>
            </goals>
        </execution>
        <!-- Run JMeter tests -->
        <execution>
            <id>jmeter-tests</id>
            <goals>
                <goal>jmeter</goal>
            </goals>
        </execution>
        <!-- Fail build on errors in test -->
        <execution>
            <id>jmeter-check-results</id>
            <goals>
                <goal>results</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
<br>

- To open the JMeter GUI run this command in your project terminal: `mvn jmeter:configure jmeter:gui`.  Do the following once the gui has opened:

    1. Name the Test Plan `Word Count`
    2. Right click on the test plan in the left side > `Add` > `Thread Group`
        - A thread group is a group of Jmeter Threads that are going to send Http Requests to our Http Server.
    3. In `Number of Threads`, make it 200.
    4. Right click on `Thread Group` > `Add` > `Logic Controller` > `While Controller`
        - In `Condition (fucntion or variable)`, add the following `jexl` function: 
        ```javascript
        ${__jexl3("${WORD}" != "<EOF>")}
        ```
    5. Right click on `While Controller` > `Add` > `Config Element` > `CSV Data Set Config
        - Inside the while loop, we add a CSV Data Set Config which reads the words from a file and feed it into a variable.
    6. In  `CSV Data Set Config`, in the `Filename:` input, click `Browse` and select the `search_words.csv` document from wherever it's stored on your computer.  Make sure that you have the following configurations:

        ```
        Variable Names: WORD
        Delimeter: \n
        Allow quoted data?: FALSE
        Recycle on EOF? FALSE
        Stop thread on EOF? TRUE
        Sharing Mode: All threads
        ```
    
    7. Right click on `While Controller` > `Add` > `Sampler` > `HTTP Request`.  Config details should be as follows:

        ```
        protocol: http
        Server Name or IP: localhost
        PortNumber: 8000
        GET Path: /search?word=${WORD}
        ```
    
    8. Right click on `While Controller` and add two `Listeners` : (1) `View Results Tree` and (2) `Summary Report`.

Note that when you change the thread pool to `1` in your application and run it -- then run JMeter -- the throughput is significantly less than when you change the pool to `4` and run both the tests and app again, concurrently.  This is because, due to more threads, we are able to process more data at a time. **Make sure that you clean your results between test runs**.
