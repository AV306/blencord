package me.av306.blencord.commands;

import me.av306.blencord.Blencord;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginCommand extends Command
{
    public LoginCommand()
    {
        super( "login" );
    }

    @Override
    public void execute( String[] args )
    {
        // Login to account
	    try
        {
            switch ( args[1] )
            {
                case "token" ->
                {
                    try
                    {
                        this.loginWithToken( args[2] );
                    }
                    catch ( ArrayIndexOutOfBoundsException oobe )
                    {
                        Blencord.INSTANCE.sendErrorMessage( "Expected 1 arguments (token), received ", args.length - 1 );
                    }
                }

                /*case "acc", "account" ->
                {
                    try
                    {
                        this.loginWithToken( this.getTokenFromAccountLogin( args[2], args[3] ) );
                    }
                    catch ( ArrayIndexOutOfBoundsException oobe )
                    {
                        Blencord.INSTANCE.sendErrorMessage( "Expected 2 arguments (email, password), received ", args.length - 1 );
                    }
                }*/

                default -> Blencord.INSTANCE.sendErrorMessage( "Unknown login method: ", args[1] );
            }
        }
        catch ( ArrayIndexOutOfBoundsException oobe )
        {
            Blencord.INSTANCE.sendErrorMessage( "Expected at least 1 argument (login method, args), received ", args.length - 1 );
        }
    }

    private String getTokenFromAccountLogin( String email, String password )
    {
        String requestBody = String.format(
                "{\"login\":\"%s\",\"password\":\"%s\",\"undelete\":false}",
                email, password
        );

        Blencord.INSTANCE.sendInfoMessage( "POSTing data: ", requestBody );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri( URI.create( "https://discord.com/api/v9/auth/login" ) )
                .POST( HttpRequest.BodyPublishers.ofString( requestBody ) )
                .build();

        try
        {
            Blencord.INSTANCE.sendInfoMessage( "Attempting to fetch token..." );
            return client.send( request, HttpResponse.BodyHandlers.ofString() ).body();
        }
        catch ( Exception e )
        {
            Blencord.INSTANCE.sendErrorMessage( "An unknown exception occurred: ", e.getMessage() );
            return "error";
        }
    }

    private void loginWithToken( String token )
    {
        try
        {
            Blencord.INSTANCE.api = new DiscordApiBuilder()
                    .setToken( token )
                    .addIntents( Intent.MESSAGE_CONTENT, Intent.GUILD_MESSAGES, Intent.GUILD_MEMBERS )
                    .login()
                    .join();

            Blencord.INSTANCE.sendInfoMessage( "Logged in to: ", Blencord.INSTANCE.getCurrentAccountName() );
        }
        catch ( Exception e )
        {
            Blencord.INSTANCE.sendErrorMessage( "An unknown exception occurred: ", e.getMessage() );
            e.printStackTrace();
        }
    }
}