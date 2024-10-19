package sockets;

/*
 * File: UDPServer.java
 * Class: CSCI 5332
 * Author: Junior Meador
 * Created on: 10/15/2024
 * Last Modified: 10/17/2024
 * Description: UDP Server Socket
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

	public static void main(String[] args) {
		int serverPort = 12000; // Port # for the UDP server

		try (DatagramSocket serverSocket = new DatagramSocket(serverPort)) {
			System.out.println("The UDP server is ready to receive");

			byte[] receiveData = new byte[2048]; // Buffer to store the incoming data, up to 2048 bytes currently
			byte[] sendData; // Buffer to store the data that will be sent back to the client

			while (true) {
				try {
					// Receive packet from client
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);

					// Process the received client message
					String data = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

					/*
					 * InetAddress is a class in Java that will resolve host names into IP address
					 * if needed but can also handle an IP address directly
					 */
					InetAddress clientAddress = receivePacket.getAddress();
					int clientPort = receivePacket.getPort();

					// Convert data to upper case and send it back (example to demonstrate server
					// processing)
					String modifiedData = data.toUpperCase();
					sendData = modifiedData.getBytes();

					/*
					 * Send the processed data back to the client in a packet. The packet contains
					 * the processed data, its length, and the client's address and port.
					 */
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress,
							clientPort);
					serverSocket.send(sendPacket); // Send the packet back to the client

				} catch (IOException e) {
					// Handles client packet specific errors, if any occur while receiving or
					// sending
					System.out.println("An error occurred while processing the client packet(s).");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// Handles any server level errors, if any occur
			System.out.println("An error occurred while starting the UDP server.");
			e.printStackTrace();
		}
	}
}
