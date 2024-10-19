package sockets;
/*
 * File: TCPClient.java
 * Class: CSCI 5332
 * Author: Junior Meador
 * Created on: 10/15/2024
 * Last Modified: 10/16/2024
 * Description: TCP Client Socket
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) {
		/*
		 * Check if an argument is provided —argument will have to be provided via run
		 * as > run configurations > arguments tab > insert string in program arguments
		 * form
		 */
		if (args.length < 1) {
			System.out.println("Usage: java TCPClient <lowercase string>");
			return;
		}

		// Store the data in a string to send
		String data = args[0];
		System.out.println(data);

		// Server's IP address and port number
		String serverName = "127.0.0.1"; // For testing purpose, using local computer
		int serverPort = 12000;

		// Create a socket to connect with the server
		try (Socket clientSocket = new Socket(serverName, serverPort);

				// Output stream to send data to the server
				DataOutputStream dataToServer = new DataOutputStream(clientSocket.getOutputStream());

				// Input stream to receive data back from the server
				BufferedReader dataFromServer = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()))) {

			/*
			 * Send the data to the server. Use writeBytes to send data in bytes over the
			 * network and append a newline ('\n') to indicate the end of the data for the
			 * server
			 */
			dataToServer.writeBytes(data + '\n');

			/*
			 * Receive the modified data from the server. The server will send back the data
			 * after processing it, and read it from the input stream.
			 */
			String modifiedData = dataFromServer.readLine(); // Reading the server's response
			System.out.println("From TCP Server: " + modifiedData); // Output data

		} catch (IOException e) {
			// Handles network related errors with socket or communication
			System.out.println("An error occurred while communicating with the server!");
			e.printStackTrace();
		}
	}
}
