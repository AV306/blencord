package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.entity.user.UserStatus;

public class StatusCommand extends Command
{
	public StatusCommand()
	{
		super( "status", "st" );
	}

	@Override
	public void execute(String[] args )
	{
		try
		{
			UserStatus status = Blencord.INSTANCE.api.getStatus();
			System.out.println( status.getStatusString() );
		}
		catch ( NullPointerException npe )
		{
			Blencord.INSTANCE.sendErrorMessage( "Not logged in!" );
		}


	}
}
