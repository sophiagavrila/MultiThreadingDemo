package com.example.F;

import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.port;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * If we statically import, we can access the 
 * static members of a class directly without 
 * class name or any object - like get(), post()...
 */


public class B_SparkServer {
	
	private static final String INPUT_FILE = "C:\\Users\\SophieGavrila\\Documents\\Threads\\Threads\\src\\main\\resources\\war_and_peace.txt";
	
	public static void main(String[] args) {
		
		startServer();
		
		internalServerError("<html><body><h1>Custom 500 handling</h1></body></html>");
	}
	
	public static void startServer() {
		
		port(1234); // set a custom port.
		
		get("/search", (request, response) -> {
			
			// set thread pool

			response.type("text/html");
		
			/*
			 *  Verify that the query param = "word"
			 *  If not, send message and set status to 404.
			 */
			if(!request.queryMap().hasKey("word")) {
				response.status(422);
				return "Your query parameter key must be equal to \"word\"";
			}
			
			String input = request.queryParams("word");
			
			long count = countWord(input);
			// now search the text file with the input captured as value of param key.
			return "The word " + "\"" + input + "\"" + " was featured " + count + " times.";
		});
		
	}
	
	private static long countWord(String word) throws IOException {
		
		System.out.println("countWord() called");
		
		String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
		
        long count = 0;
        int index = 0;
        while (index >= 0) {
        	
            index = text.indexOf(word, index); // the second index takes in t

            if (index >= 0) {
                count++;
                index++;
            }
        }
        return count;
    }
	

}
