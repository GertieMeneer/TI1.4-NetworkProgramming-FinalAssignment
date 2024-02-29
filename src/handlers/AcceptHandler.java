package handlers;

import tree.*;

import java.net.*;
import java.io.IOException;

// This class is responsible for handling new connections made with the server
public class AcceptHandler implements Runnable
{
    private ServerSocket serverSocket;
    private BinarySearchTree users;

    public AcceptHandler(ServerSocket serverSocket, BinarySearchTree users)
    {
        this.serverSocket = serverSocket;
        this.users = users;
    }

    @Override
    public void run()
    {
        // Wait for incoming connections
        while (true)
        {
            Socket clientSocket = null;

            try
            {
                clientSocket = serverSocket.accept();
                System.out.println("New connections from " + clientSocket.getInetAddress().getHostAddress());

                // Make new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, users);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

            catch (IOException e)
            {
                System.out.println("Could not accept connection");
            }
        }
    }
}
