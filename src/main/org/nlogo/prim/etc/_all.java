package org.nlogo.prim.etc ;

import org.nlogo.api.Dump;
import org.nlogo.agent.Agent;
import org.nlogo.agent.AgentSet;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _all
	extends Reporter
{
	@Override
	public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( new int[] { Syntax.TYPE_AGENTSET , Syntax.TYPE_BOOLEAN_BLOCK } ,
			  Syntax.TYPE_BOOLEAN , "OTPL" , "?" ) ;
	}
	@Override
	public Object report( final Context context )
		throws LogoException
	{
		return report_1
			( context , argEvalAgentSet( context , 0 ) , args[ 1 ] )
			? Boolean.TRUE
			: Boolean.FALSE ;
	}
	public boolean report_1( final Context context , AgentSet sourceSet , Reporter reporterBlock )
		throws LogoException
	{
		Context freshContext = new Context( context , sourceSet ) ;
		reporterBlock.checkAgentSetClass( sourceSet , context ) ;		
		for( AgentSet.Iterator iter = sourceSet.iterator() ; iter.hasNext() ; )
		{
			Agent tester = iter.next() ;
			Object value = freshContext.evaluateReporter( tester , reporterBlock) ;
			if( ! ( value instanceof Boolean ) )
			{
				throw new EngineException
				( context , this , I18N.errors().getNJava("org.nlogo.prim.$common.expectedBooleanValue",
                            new String [] {displayName(),  Dump.logoObject( tester ),Dump.logoObject( value ) }));
			}
			if( ! ( ( Boolean ) value ).booleanValue() )
			{
				return false ;
			}
		}
		return true ;
	}
}