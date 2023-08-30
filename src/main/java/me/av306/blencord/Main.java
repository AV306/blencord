package me.av306.blencord;

import java.util.Scanner;

import org.javacord.api.*;

public class Main
{
	public static void main( String[] args )
	{
		new Main().run();
	}

	public void run()
	{
		// Get Scanner for stdin
		Scanner scanner = new Scanner( System.in );

		System.out.println( "Hello JVM!\n");

		System.out.print( "Enter Discord token: " );

		// Wait for inpiut
		String token = scanner.nextLine();

		System.out.println( "Received token!" );

		// Set up discord api access

		System.out.println( "Connecting to Discord..." );

		DiscordApi api = new DiscordApiBuilder()
				.setToken( token ) // Set token
				.login() // Login to API
				.join(); // Block until login completed
		
		System.out.println( "Connected to Discord!" );
		System.out.println( "Entering command loop." );

		// Main event loop
		String line;
		boolean shouldQuit = false;
		while ( !shouldQuit )
		{
			line = scanner.nextLine();
			System.out.println( "> " + line );
			if ( line.equals( ":q" ) ) break;
		}

		// Quit
		System.out.println( "Quitting Blencord! Goodbye!" );
		System.exit( 0 );
	}
}
