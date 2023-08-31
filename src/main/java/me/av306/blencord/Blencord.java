package me.av306.blencord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import me.av306.blencord.commands.*;
import org.javacord.api.*;

public enum Blencord
{
	INSTANCE;

	public HashMap<String, Command> commands = new HashMap<>();

	public DiscordApi api;

	private void registerCommands()
	{
		new StatusCommand();
	}

	public void run() throws IOException
	{
		// Get Scanner for stdin
		BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );

		System.out.println( "Hello JVM!");

		System.out.print( "Enter Discord token: " );

		// Wait for inpiut
		String token = reader.readLine();

		System.out.println( "Received token!" );

		// Set up discord api access

		System.out.println( "Connecting to Discord..." );

		this.api = new DiscordApiBuilder()
				.setToken( token ) // Set token
				.login() // Login to API
				.join(); // Block until login completed
		
		System.out.println( "Connected to Discord!" );
		System.out.println( "Entering command loop." );

		String line;
		String[] command;
		do
		{
			line = reader.readLine();

			System.out.println( "> " + line );

			// oh god this is xenon CP all over again
			command = line.split( " " );

			// Find command
			Command cmd = this.commands.get( command[0] );

			if ( cmd == null )
			{
				// Command not found
				System.out.println("Command not found :(");
				continue;
			}

			cmd.execute( command );
		}
		while ( !line.equals( ":q" ) ); // ":q" is a special command

		// Quit
		System.out.println( "Quitting Blencord! Goodbye!" );
		System.exit( 0 );
	}

	public static void main( String[] args )
	{
		Blencord.INSTANCE.registerCommands();
		try
		{
			Blencord.INSTANCE.run();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
