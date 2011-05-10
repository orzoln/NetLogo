package org.nlogo.prim ;

import org.nlogo.agent.AgentSet;
import org.nlogo.agent.Patch;
import org.nlogo.agent.Turtle;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _neighbors4
	extends Reporter
{
	@Override public Syntax syntax()
	{
		return Syntax.reporterSyntax( Syntax.TYPE_PATCHSET , "-TP-" ) ;
	}
	@Override public Object report( Context context )
	{
		return report_1( context ) ;
	}
	public AgentSet report_1( Context context )
	{
		if( context.agent instanceof Turtle )
		{
			return ( (Turtle) context.agent ).getPatchHere().getNeighbors4() ;
		}
		return ( (Patch) context.agent ).getNeighbors4() ;
	}
}