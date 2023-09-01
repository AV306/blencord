package me.av306.blencord.commands;

public class LoginCommand extends Command
{
    public LoginCommand()
    {
        super( "login" );
    }

    @Overrice
    public boolean execute( String[] args )
    {
        return true;
    }
}