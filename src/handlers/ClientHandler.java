package handlers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import tree.*;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.util.stream.Collectors;

// This class is responsible for handling clients connected to the server
class ClientHandler implements Runnable
{
    private Socket clientSocket;
    private BinarySearchTree users;

    public ClientHandler(Socket clientSocket, BinarySearchTree users)
    {
        this.clientSocket = clientSocket;
        this.users = users;
    }

    // This functions gets run on a new thread if a client connects to the server.
    // It handles the in- and outputs with the user.
    // For every client, this method gets executed.
    public void run()
    {
        try
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Ask for username and password
            output.println("Insert username: ");
            String username = input.readLine();
            output.println("Insert password: ");
            String password = input.readLine();

            // Check if username and passwords are valid
            if (users.containsKey(username) && users.get(username).equals(password))
            {
                output.println("Welcome " + username + "!");
                output.println("These are all the actions you can execute: 'add', 'remove' or 'show': ");
                String action = input.readLine();
                output.println("You chose: " + action);

                if (action.equals("add"))
                {
                    output.println("Insert the text you want to add to the logs: ");
                    String text = input.readLine();

                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray) {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username)) {
                            JSONArray logsArray = (JSONArray) person.get("logs");
                            logsArray.add(text);
                            break;
                        }
                    }

                    String updatedJsonString = jsonObject.toJSONString();

                    try
                    {
                        FileWriter fileWriter = new FileWriter("logs.json");
                        fileWriter.write(updatedJsonString);
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    output.println("Finished - Connection gets disconnected!");
                }

                if (action.equals("remove"))
                {
                    output.println("Insert the text you want to remove from the logs: ");
                    String text = input.readLine();

                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray)
                    {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username))
                        {
                            JSONArray logsArray = (JSONArray) person.get("logs");

                            for (int i = 0; i < logsArray.size(); i++)
                            {
                                if (logsArray.get(i).toString().equals(text))
                                {
                                    logsArray.remove(i);
                                }
                            }

                            break;
                        }
                    }

                    String updatedJsonString = jsonObject.toJSONString();

                    try
                    {
                        FileWriter fileWriter = new FileWriter("logs.json");
                        fileWriter.write(updatedJsonString);
                        fileWriter.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    output.println("Finished - Connection gets disconnected!");
                }

                if (action.equals("show"))
                {
                    BufferedReader reader = new BufferedReader(new FileReader("logs.json"));
                    String jsonString = reader.lines().collect(Collectors.joining());

                    JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
                    JSONArray personArray = (JSONArray) jsonObject.get("Person");

                    for (Object personObject : personArray) {
                        JSONObject person = (JSONObject) personObject;
                        if (person.get("id").equals(username)) {
                            JSONArray logsArray = (JSONArray) person.get("logs");

                            output.println("De logs: ");

                            for (Object o : logsArray)
                            {
                                output.println(" - " + o);
                            }

                            break;
                        }
                    }

                    output.println("Finished - Connection gets disconnected!");
                }
            }
            else
            {
                output.println("Invalid username or password");
            }

            // Close the connection
            clientSocket.close();
        }

        catch (IOException e)
        {
            System.out.println("An error has occured while processing the connection");
        }
    }
}