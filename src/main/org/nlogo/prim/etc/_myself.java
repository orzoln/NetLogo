package org.nlogo.prim.etc ;

import org.nlogo.agent.Agent;
import org.nlogo.agent.Observer;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _myself
	extends Reporter
{
	@Override
	public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( Syntax.TYPE_AGENT , "-TPL" ) ;
	}
	@Override
	public Object report( final Context context ) throws LogoException
	{
		Agent myself = context.myself() ;
		if( myself == null || myself instanceof Observer )
		{
			throw new EngineException
				( context , this , I18N.errors().get("org.nlogo.prim.etc._myself.noAgentMyself")) ;
		}
		return myself ;
	}
	public Agent report_1( Context context ) throws LogoException
	{
		Agent myself = context.myself() ;
		if( myself == null || myself instanceof Observer )
		{
			throw new EngineException
				( context , this , I18N.errors().get("org.nlogo.prim.etc._myself.noAgentMyself") ) ;
		}
		return myself ;
	}
}