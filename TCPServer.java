package sockets;
/*
 * File: TCPServer.java
 * Class: CSCI 5332
 * Author: Junior Meador
 * Created on: 10/15/2024
 * Last Modified: 10/16/2024
 * Description: TCP Server Socket
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
		int serverPort = 12000; // Port # for the TCP server

		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			System.out.println("The TCP server is ready to receive");

			while (true) {

				/*
				 * The server accepts a connection from the client. Once the connection is
				 * accepted, the server can communicate with the client continuously using the
				 * accepted socket.
				 */
				try (Socket connectionSocket = serverSocket.accept(); // Agreement to accept connection

						// Input stream for receiving data from the client
						BufferedReader inFromClient = new BufferedReader(
								new InputStreamReader(connectionSocket.getInputStream()));

						// Output stream for sending data back to the client
						DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream())) {

					/*
					 * Receive the client's message, convert data to upper case(example to
					 * demonstrate server processing)
					 */
					String clientSentence = inFromClient.readLine();
					String capitalizedSentence = clientSentence.toUpperCase();

					/*
					 * Send the processed data back to the client. The newline character ('\n') is
					 * appended to the message to signal the end of the message. This is necessary
					 * because the client uses BufferedReader.readLine() to read the server's
					 * response. The readLine() method expects input to be terminated by a newline
					 * character and without it the client would continue waiting for more input
					 * which could potentially cause the program to hang.
					 */
					outToClient.writeBytes(capitalizedSentence + '\n');

				} catch (IOException e) {
					// Handles client specific connection issues
					System.out.println("An error occurred while processing client connection.");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// Handles server specific errors
			System.out.println("An error occurred while starting the TCP server.");
			e.printStackTrace();
		}
	}
}
