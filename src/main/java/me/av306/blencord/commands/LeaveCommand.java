package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

public class LeaveCommand extends Command
{
	public LeaveCommand()
	{
		super( "leave", "lv" );
	}

	@Override
	public void execute( String[] args )
	{
		if ( Blencord.INSTANCE.pos instanceof ServerTextChannel serverTextChannel )
		{
			// Leave channel
			Blencord.INSTANCE.pos = serverTextChannel.getServer();
		}
		else if ( Blencord.INSTANCE.pos instanceof Server )
		{
			// Leave server
			Blencord.INSTANCE.pos = null;
		}
		else
		{
			Blencord.INSTANCE.pos = null;
			Blencord.INSTANCE.sendErrorMessage( "Not in a server! (Use :q to quit)" );
		}
	}
}
