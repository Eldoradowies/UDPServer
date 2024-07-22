import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        int port = 9876; // Server will listen on this port

        try (DatagramSocket socket = new DatagramSocket(port)) { // Create a DatagramSocket to listen on the specified port
            System.out.println("UDP server is listening on port " + port);

            byte[] buffer = new byte[1024]; // Buffer to store incoming data
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); // Reader to read input from the console

            while (true) { // Infinite loop to keep the server running
                // Receive packet from client
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // Create a DatagramPacket to receive data
                socket.receive(packet); // Receive data from a client and store it in the packet

                InetAddress clientAddress = packet.getAddress(); // Get the address of the client
                int clientPort = packet.getPort(); // Get the port of the client
                String received = new String(packet.getData(), 0, packet.getLength()); // Convert the received data to a string

                System.out.println("Received from client: " + received); // Print the received message

                // Send response to the client
                System.out.print("Enter message to send: "); // Prompt the server user to enter a response message
                String response = consoleReader.readLine(); // Read the response message from the console
                byte[] responseData = response.getBytes(); // Convert the response message to a byte array
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort); // Create a DatagramPacket to send the response
                socket.send(responsePacket); // Send the response to the client
            }
        } catch (Exception ex) { // Catch any exceptions that occur
            System.out.println("Server exception: " + ex.getMessage()); // Print the exception message
            ex.printStackTrace(); // Print the stack trace of the exception
        }
    }
}
