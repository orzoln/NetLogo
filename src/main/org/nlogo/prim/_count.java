package org.nlogo.prim ;

import org.nlogo.agent.AgentSet;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _count extends Reporter
{
	@Override public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( new int[] { Syntax.TYPE_AGENTSET } ,
			  Syntax.TYPE_NUMBER ) ;
	}
	@Override public Object report( Context context )
		throws LogoException
	{
		return report_1( context , argEvalAgentSet( context , 0 ) ) ;
	}
	public double report_1( Context context , AgentSet arg0 )
	{
		return arg0.count() ;
	}
}