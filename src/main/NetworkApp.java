package main;

import handlers.AcceptHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tree.*;

import java.io.FileWriter;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class contains the main function for starting the server
public class NetworkApp
{
    // In this main function, a binary search tree is being created
    // After that, the server is being started so clients can connect
    public static void main(String[] args)
    {
        // Create a binary search tree to save usernames and passwords
        BinarySearchTree users = new BinarySearchTree();
        users.put("Astrid", "password1");
        users.put("Bob", "password2");
        users.put("Pieter", "password3");
        users.put("Kip", "password4");
        users.put("David", "passwor");
        users.put("Mick", "daf");
        users.put("Max", "423425");
        users.put("Joost", "naam");
        users.put("Bas", "jaja");
        users.put("Stef", "oki");
        users.put("Lucas", "daar");
        users.put("Nick", "hunnn");

        JSONObject json = new JSONObject();
        JSONArray peopleArray = new JSONArray();

        json.put("Person", peopleArray);

        List<String> nameList = new ArrayList<>(users.getAllKeys());

        for (String name : nameList)
        {
            JSONObject person = new JSONObject();

            ArrayList logs = new ArrayList<>();

            person.put("id", name);
            person.put("logs", logs);

            peopleArray.add(person);
        }

        try (FileWriter file = new FileWriter("logs.json")) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start server
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
        }

        catch (IOException e)
        {
            System.out.println("Could not start the server on port 5000");
            System.exit(-1);
        }

        // Start a thread to process incoming connections
        Thread acceptThread = new Thread(new AcceptHandler(serverSocket, users));
        acceptThread.start();

        // Keep the main thread active (not the best way to do that lol)
        while (true)
        {

        }
    }
}