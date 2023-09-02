package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.entity.channel.ServerTextChannel;

public class SendCommand extends Command
{
	public SendCommand()
	{
		super( "send", "s" );
	}


	@Override
	public void execute( String[] args )
	{
		try
		{
			((ServerTextChannel) Blencord.INSTANCE.pos).sendMessage( args[1] );
		}
		catch ( ClassCastException | NullPointerException e )
		{
			Blencord.INSTANCE.sendErrorMessage( "Not in a text channel!" );
		}
		catch ( ArrayIndexOutOfBoundsException oobe )
		{
			Blencord.INSTANCE.sendErrorMessage( "Expected 1 argument (message), received ", args.length - 1 );
		}
	}
}
