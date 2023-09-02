package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

import java.util.concurrent.ExecutionException;

public class ListCommand extends Command
{
	public ListCommand()
	{
		super( "list", "ls" );
	}

	@Override
	public void execute( String[] args )
	{
		if ( Blencord.INSTANCE.pos == null )
		{
			// Not in a server, list servers
			Blencord.INSTANCE.sendInfoMessage( "Available servers: " );
			Blencord.INSTANCE.api.getServers().forEach( Blencord.INSTANCE::sendInfoMessage );
		}
		else if ( Blencord.INSTANCE.pos instanceof Server server )
		{
			// In a server, list channels
			Blencord.INSTANCE.sendInfoMessage( "Channels in server ", server.getName(), ':' );
			server.getChannels().forEach( Blencord.INSTANCE::sendInfoMessage );
		}
		else if ( Blencord.INSTANCE.pos instanceof ServerTextChannel serverTextChannel )
		{
			try
			{
				serverTextChannel.getMessages( Integer.parseInt( args[1] ) )
						.get()
						.forEach( message -> System.out.printf( "%s: %s\n", message.getAuthor().getName(), message.getContent() ) );
			}
			catch ( ArrayIndexOutOfBoundsException oobe )
			{
				Blencord.INSTANCE.sendErrorMessage( "Expected 1 arguments (message limit), received ", args.length - 1 );
			}
			catch ( NumberFormatException nfe )
			{
				Blencord.INSTANCE.sendErrorMessage( "Expected number, received \"", args[1], '\"' );
			}
			catch ( Exception e )
			{
				Blencord.INSTANCE.sendErrorMessage( "An unknown exception occurred: ", e.getMessage() );
			}
		}
	}
}
