package me.av306.blencord.commands;

import me.av306.blencord.Blencord;

public class HintCommand extends Command
{
	public HintCommand()
	{
		super( "listcommands", "listcmd", "hint", "\t" );
	}

	@Override
	public void execute( String[] args )
	{
		Blencord.INSTANCE.sendInfoMessage( "Available commands: " );
		Blencord.INSTANCE.commands.keySet().forEach( Blencord.INSTANCE::sendInfoMessage );
	}
}
