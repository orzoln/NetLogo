package org.nlogo.prim ;

import org.nlogo.agent.Turtle;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Command;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Syntax;

public final strictfp class _jump
	extends Command
{
	@Override public Syntax syntax()
	{
		return Syntax.commandSyntax( new int[] { Syntax.TYPE_NUMBER } ,
									 "-T--" , true ) ;
	}
	@Override public void perform( Context context ) throws LogoException
	{
		perform_1( context , argEvalDoubleValue( context , 0 ) ) ;
	}
	public void perform_1( Context context , double distance )
	{
		try
        {
			( (Turtle) context.agent ).jump( distance ) ;
		}
        catch( org.nlogo.api.AgentException e ) { } // NOPMD
        context.ip = next ;
	}
}