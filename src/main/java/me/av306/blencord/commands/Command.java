package me.av306.blencord.commands;

import me.av306.blencord.Blencord;

public abstract class Command
{
	public Command( String... aliases )
	{
		for ( String alias : aliases )
			Blencord.INSTANCE.commands.put( alias, this );
	}

	/**
	 * Execute the action(s) represented by this command.
	 * @param args: The arguments passed to the command. Argument #0 (<code>args[0]</code>) is the alias entered.
	 * @return Success flag
	 */
	public abstract boolean execute( String[] args );
}
