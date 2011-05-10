package org.nlogo.prim.etc ;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import org.nlogo.agent.Agent;
import org.nlogo.agent.AgentSet;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _minnofwithties
	extends Reporter
{
	@Override
	public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( Syntax.TYPE_AGENTSET ,
			  new int[] { Syntax.TYPE_NUMBER , Syntax.TYPE_NUMBER_BLOCK } ,
			  Syntax.TYPE_AGENTSET ,
			  Syntax.NORMAL_PRECEDENCE ,
			  false , // left associative
			  "OTPL" ,
			  "?"    // takes reporter block of unknown agent type
				) ;
	}
	@Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
	{
		int n = argEvalIntValue( context , 1 ) ;
		if( n < 0 )
		{
			throw new EngineException
				( context , this , I18N.errors().getNJava("org.nlogo.prim.etc.$common.firstInputCantBeNegative",
                        new String [] {displayName()}));
		}
		AgentSet sourceSet = argEvalAgentSet( context , 0 ) ;
		int count = sourceSet.count() ;
		if( n > count )
		{
				throw new EngineException
				( context , this , I18N.errors().getNJava("org.nlogo.prim.etc.$common.notThatManyAgentsExist ",
                        new String [] {Integer.toString(n), Integer.toString(count)}));
		}
		args[ 2 ].checkAgentSetClass( sourceSet , context ) ;
		TreeMap<Object,LinkedList<Agent>> resultAgents =
			new TreeMap<Object,LinkedList<Agent>>() ;

		org.nlogo.nvm.Context freshContext =
			new org.nlogo.nvm.Context( context , sourceSet ) ;
		for( AgentSet.Iterator iter = sourceSet.shufflerator( context.job.random ) ;
			 iter.hasNext() ; )
		{			
			org.nlogo.agent.Agent tester = iter.next() ;
			Object result = freshContext.evaluateReporter( tester , args[ 2 ] ) ;
			if( ! ( result instanceof Double ) )
			{
				continue ;
			}
			LinkedList<Agent> resultList = resultAgents.get( result ) ;
			if( resultList == null )
			{
				resultList = new LinkedList<Agent>() ;
				resultAgents.put( result , resultList ) ;
			}
			resultList.add( tester ) ;
		}

		AgentSet resultSet = new org.nlogo.agent.ArrayAgentSet
			( sourceSet.type() , n , false , world ) ;

		for( Iterator<LinkedList<Agent>> iter = resultAgents.values().iterator() ;
			 n > 0 && iter.hasNext() ; )
		{
			for( Agent a : iter.next() )
			{
				resultSet.add( a ) ;
				n-- ;
			}
		}
		
		return resultSet ;
	}
}