package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

import java.nio.channels.Channel;
import java.util.NoSuchElementException;

public class JoinCommand extends Command
{
	public JoinCommand()
	{
		super( "join", "jn", "j" );
	}

	@Override
	public void execute( String[] args )
	{
		if ( Blencord.INSTANCE.api == null )
		{
			Blencord.INSTANCE.sendErrorMessage( "Not logged in!" );
			return;
		}

		try
		{
			String[] ids = args[1].split( "/" );

			for ( String idString : ids )
			{
				try
				{
					long id = Long.parseLong( idString );
					this.joinEntity( id );
				}
				catch ( NumberFormatException nfe )
				{
					Blencord.INSTANCE.sendErrorMessage( "Expected number, received \"", args[1], '\"' );
				}
			}
		}
		catch ( ArrayIndexOutOfBoundsException oobe )
		{
			Blencord.INSTANCE.sendErrorMessage( "Expected 1 or more arguments (ids), received ", args.length - 1 );
		}
	}

	private void joinEntity( long id )
	{
		if ( Blencord.INSTANCE.pos == null )
		{
			// Join a server
			try
			{
				Blencord.INSTANCE.pos = Blencord.INSTANCE.api.getServerById( id ).orElseThrow();
				//Blencord.INSTANCE.sendInfoMessage( "Joined server: ", ((Server) Blencord.INSTANCE.pos).getName() );
			}
			catch ( NoSuchElementException noSuchElementException )
			{
				Blencord.INSTANCE.sendErrorMessage( "Could not find server with id: ", id );
			}
		}
		else if ( Blencord.INSTANCE.pos instanceof Server currentServer )
		{
			// In a server, join a channel
			try
			{
				Blencord.INSTANCE.pos = currentServer.getChannelById( id ).orElseThrow();
				//Blencord.INSTANCE.sendInfoMessage( "Joined channel: ", id );
			}
			catch ( NoSuchElementException noSuchElementException )
			{
				Blencord.INSTANCE.sendErrorMessage(
						"Could not find channel in server ",
						currentServer.getName(),
						" with id: ",
						id
				);
			}
		}
		else
		{
			Blencord.INSTANCE.sendErrorMessage( "Nothing to join!" );
		}
	}
}
