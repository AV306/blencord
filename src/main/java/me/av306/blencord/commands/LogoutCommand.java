package me.av306.blencord.commands;

import me.av306.blencord.Blencord;

public class LogoutCommand extends Command
{
	public LogoutCommand()
	{
		super( "logout" );
	}

	@Override
	public void execute(String[] args )
	{
		try
		{
			Blencord.INSTANCE.sendInfoMessage(
					"Logging out of: ",
					Blencord.INSTANCE.getCurrentAccountNameOrDefault( "Unknown" )
			);

			Blencord.INSTANCE.api.disconnect();
			Blencord.INSTANCE.api = null;
			Blencord.INSTANCE.pos = null;
		}
		catch ( NullPointerException npe )
		{
			Blencord.INSTANCE.sendErrorMessage( "Not logged in, can't logout!" );
		}
	}
}
