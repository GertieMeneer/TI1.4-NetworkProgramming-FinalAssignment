package mains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// This class contains the main function for starting the user app
public class NetworkClient
{
    // This main function connects and communicates with the server and the user
    public static void main(String[] args)
    {
        boolean isLoginIn = false;

        try
        {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int counter = 1;
            String name = "";
            String text = "";

            while (true)
            {
                // Read text from server
                String serverText = in.readLine();

                if(!(serverText == null))
                {
                    System.out.println(serverText);
                }

                // Send message to server
                Scanner scanner = new Scanner(System.in);

                if (counter == 1)
                {
                    text = scanner.nextLine();
                    name = text;
                    out.println(text);
                }

                if (counter == 2)
                {
                    text = scanner.nextLine();
                    out.println(text);
                }

                if(!(serverText == null))
                {
                    if (serverText.equals("Welcome " + name + "!"))
                    {
                        isLoginIn = true;
                    }

                    if (serverText.equals("Invalid username or password"))
                    {
                        break;
                    }
                }

                if (isLoginIn)
                {
                    if(!(serverText == null))
                    {
                        if (serverText.equals("These are all the actions you can execute: 'add', 'remove' or 'show': "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("Insert the text you want to add to the logs: "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("Insert the text you want to remove from the logs: "))
                        {
                            text = scanner.nextLine();
                            out.println(text);
                        }

                        if (serverText.equals("Finished - Connection gets disconnected!"))
                        {
                            break;
                        }
                    }
                }

                counter++;
            }

            // Disconnect the connection
            socket.close();
        }

        catch (IOException e)
        {
            System.out.println("Error connecting to the server. Is it running?");
            //e.printStackTrace();
        }
    }
}