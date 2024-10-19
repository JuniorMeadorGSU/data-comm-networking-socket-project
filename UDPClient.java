package sockets;
/*
 * File: UDPClient.java
 * Class: CSCI 5332
 * Author: Junior Meador
 * Created on: 10/15/2024
 * Last Modified: 10/16/2024
 * Description: UDP Client Socket
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPClient {

	public static void main(String[] args) {
		/*
		 * Check if an argument is provided —argument will have to be provided via run
		 * as > run configurations > arguments tab > insert string in program arguments
		 * form
		 */
		if (args.length < 1) {
			System.out.println("Usage: java UDPClient <lowercase string>");
			return;
		}

		// Store the data in a string to send
		String data = args[0];
		System.out.println(data);

		String serverName = "127.0.0.1"; // For testing purpose, using local computer
		int serverPort = 12000; // Server port number

		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress serverAddress = InetAddress.getByName(serverName);

			// Convert string to bytes because UDP sends data as bytes
			byte[] sendData = data.getBytes();
			byte[] receiveData = new byte[2048]; // Buffer can receive up to 2048 bytes

			/*
			 * Send data in a packet to the server. A packet contains the data, its length,
			 * the server address, and the port number.
			 */
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
			clientSocket.send(sendPacket);

			/*
			 * Wait for the server's response. The server will send back data in a packet,
			 * which will be placed in the receiveData buffer.
			 */
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket); // Received response from server

			// Extract the modified data from received packet and convert back to string for
			// displaying
			String modifiedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println("From UDP Server: " + modifiedData);

		} catch (IOException e) {
			// Handles network related errors like socket issues
			System.out.println("An error occurred while communicating with the server!");
			e.printStackTrace();
		}
	}
}
