package org.nlogo.prim.etc ;

import org.nlogo.api.LogoException;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Pure;
import org.nlogo.nvm.Syntax;

public final strictfp class _approximatehsb extends Reporter implements Pure
{
	@Override public Syntax syntax()
	{
		int[] right = { Syntax.TYPE_NUMBER , Syntax.TYPE_NUMBER , Syntax.TYPE_NUMBER } ;
		int ret = Syntax.TYPE_NUMBER ;
		return Syntax.reporterSyntax( right , ret ) ;
	}	
	@Override public Object report( Context context ) throws LogoException
	{
		return validDouble( report_1( context , 
									  argEvalDoubleValue( context , 0 ) ,
									  argEvalDoubleValue( context , 1 ) ,
									  argEvalDoubleValue( context , 2 ) ) ) ;
	}
	public double report_1( Context context , double h , double s , double b )
	{
		return org.nlogo.api.Color.getClosestColorNumberByHSB
			( (float) h , (float) s , (float) b ) ; 
	}
}