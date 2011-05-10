package org.nlogo.prim.etc ;

import org.nlogo.agent.Agent;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _isagent
	extends Reporter
	implements org.nlogo.nvm.Pure
{
	@Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
	{
		Object thing = args[ 0 ].report( context ) ;
		if( ! ( thing instanceof Agent ) )
		{
			return Boolean.FALSE ;
		}
		Agent agent = (Agent) thing ;
		return agent.id == -1
			? Boolean.FALSE
			: Boolean.TRUE ;
	}
	@Override
	public Syntax syntax()
	{
		int[] right = { Syntax.TYPE_WILDCARD } ;
		int ret = Syntax.TYPE_BOOLEAN ;
		return Syntax.reporterSyntax( right , ret ) ;
	}
}



