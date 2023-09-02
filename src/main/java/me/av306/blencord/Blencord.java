package me.av306.blencord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import me.av306.blencord.commands.*;
import org.javacord.api.*;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.fusesource.jansi.Ansi.*;

public enum Blencord
{
	INSTANCE;

	public HashMap<String, Command> commands = new HashMap<>();

	public DiscordApi api;

	public DiscordEntity pos;

	private void registerCommands()
	{
		new StatusCommand();
		new LoginCommand();
		new LogoutCommand();
		new HintCommand();
		new ListCommand();
		new JoinCommand();
		new SendCommand();
	}

	public void run() throws IOException
	{
		// Get Scanner for stdin
		BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );

		System.out.println( ansi().render( "@|green Hello JVM!|@" ).reset() );

		String line;
		String[] command;
		do
		{
			System.out.print(
					ansi()
							.a( '(' )
							.fgBrightGreen()
							.a( this.getCurrentAccountNameOrDefault( "Not logged in" ) )
							.reset()
							.a( ") " )
							.fgBrightYellow()
							.a( this.getPath() )
							.reset()
							.a( " $ " )
			);

			line = reader.readLine();

			// oh god this is xenon CP all over again
			command = line.split( " " );

			// Find command
			Command cmd = this.commands.get( command[0] );

			if ( cmd == null )
			{
				// Command not found
				System.out.println( "Command not found :(" );
				continue;
			}

			cmd.execute( command );
		}
		while ( !line.equals( ":q" ) ); // ":q" is a special command

		// Quit
		System.out.println( "Quitting Blencord! Goodbye!" );
	}

	public String getPath()
	{
		StringBuilder builder = new StringBuilder( "/" );
		if ( this.api == null ) return ""; // Not logged in, no point

		// Can't wait for switch patterns :(
		if ( this.pos == null )
		{
			// Not in a server
		}
		else if ( this.pos instanceof Server server )
		{
			// In a server, get its name
			builder.append( server.getName() ).append( '/' );
		}
		else if ( this.pos instanceof ServerTextChannel serverTextChannel )
		{
			builder.append( serverTextChannel.getServer().getName() )
					.append( '/' )
					.append( serverTextChannel.getName() )
					.append( '/' );
		}
		else
		{
			builder.append( "unknown/" );
		}

		return builder.toString();
	}

	public void sendInfoMessage( String message )
	{
		System.out.println( ansi().fgBrightCyan().a( message ).reset() );
	}

	public void sendInfoMessage( Object... messages )
	{
		var ansi = ansi().fgBrightCyan();
		for ( Object m : messages ) ansi.a( m );

		System.out.println( ansi );
	}

	public void sendErrorMessage( String message )
	{
		System.out.println( ansi().fgBrightRed().a( message ).reset() );
	}

	public void sendErrorMessage( Object... messages )
	{
		var ansi = ansi().fgBrightRed();
		for ( Object m : messages ) ansi.a( m );

		System.out.println( ansi );
	}

	@NotNull
	public String getCurrentAccountNameOrDefault( String defaultName )
	{
		try
		{
			return this.api.getYourself().getName();
		}
		catch ( NullPointerException npe )
		{
			return defaultName;
		}
	}

	@Nullable
	public String getCurrentAccountName()
	{
		return this.api.getYourself().getName();
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

		System.exit( 0 );
	}
}
