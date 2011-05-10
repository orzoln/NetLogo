package org.nlogo.prim ;

import java.util.Iterator;

import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Pure;
import org.nlogo.nvm.Syntax;

public final strictfp class _sum extends Reporter implements Pure
{
	@Override public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( new int[] { Syntax.TYPE_LIST } ,
			  Syntax.TYPE_NUMBER ) ;
	}
	@Override public Object report( Context context ) throws LogoException
	{
		return report_1( context , argEvalList( context , 0 ) ) ;
	}
	public double report_1( Context context , LogoList l0 ) throws LogoException
	{
		double sum = 0 ;
		for( Iterator<Object> it = l0.iterator() ; it.hasNext() ; )
		{
			Object elt = it.next() ;
			if( elt instanceof Double )
			{
				sum += ( (Double) elt ).doubleValue() ;
			}
		}
		return validDouble( sum ) ;
	}
}