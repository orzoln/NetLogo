package org.nlogo.prim.threed;

import org.nlogo.api.LogoException;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _towardspitchxyznowrap
	extends Reporter
{
	@Override
	public Syntax syntax()
	{
		int[] right = { Syntax.TYPE_NUMBER , 
						Syntax.TYPE_NUMBER ,
						Syntax.TYPE_NUMBER } ;
		int ret = Syntax.TYPE_NUMBER ;
		return Syntax.reporterSyntax( right , ret , "-TP-" ) ;
	}
	@Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
	{
		try
		{
			return newValidDouble
				( world.protractor().towardsPitch
				  ( context.agent ,
					argEvalDoubleValue( context , 0 ) ,
					argEvalDoubleValue( context , 1 ) ,
					argEvalDoubleValue( context , 2 ) ,
					false ) ) ; // true = don't wrap
		}
		catch( org.nlogo.api.AgentException ex )
		{
			throw new EngineException( context , this , ex.getMessage() ) ;
		}
	}
}