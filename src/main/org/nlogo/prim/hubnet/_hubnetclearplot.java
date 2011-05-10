package org.nlogo.prim.hubnet ;

import org.nlogo.api.LogoException;
import org.nlogo.nvm.Syntax;

public final strictfp class _hubnetclearplot
	extends org.nlogo.nvm.Command
{
	@Override
	public Syntax syntax()
	{
		return Syntax.commandSyntax
			( new int[] { Syntax.TYPE_STRING } ) ;
	}
	@Override
	public void perform( final org.nlogo.nvm.Context context ) throws LogoException
	{
		final String name  = argEvalString( context , 0 ) ;

		workspace.waitFor
			( new org.nlogo.api.CommandRunnable() {
					public void run() {
						workspace.getHubNetManager().clearPlot( name ) ;
					} } ) ;
		context.ip = next ;
	}
}