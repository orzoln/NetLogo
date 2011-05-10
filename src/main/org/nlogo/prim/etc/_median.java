package org.nlogo.prim.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nlogo.api.Dump;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _median
	extends Reporter
	implements org.nlogo.nvm.Pure
{
	@Override
	public Object report( final Context context ) throws LogoException
	{
		LogoList list = argEvalList( context , 0 ) ;
		int badElts = 0 ;
		List<Double> nums =
			new ArrayList<Double>( list.size() ) ;
		for( Object elt : list )
		{
			if( ! ( elt instanceof Double ) )
			{
				++badElts ;
				continue ;
			}
			nums.add( (Double) elt ) ;
		}
		int listSize = list.size() ;
		if( listSize == badElts )
		{
			throw new EngineException
				( context , this , I18N.errors().getNJava("org.nlogo.prim.etc.median.cantFindMedianOfListWithNoNumbers",
                        new String [] {Dump.logoObject( list )}));
		}
		Collections.sort( nums ) ;
		int medianPos = (listSize - badElts) / 2 ;
		if( ( listSize - badElts ) % 2 == 1 )
		{
			return nums.get( medianPos ) ;
		}
		Double middle1 = nums.get( medianPos - 1 ) ;
		Double middle2 = nums.get( medianPos ) ;
		return newValidDouble
			( ( middle1.doubleValue() + middle2.doubleValue() ) / 2 ) ;
	}
	@Override
	public Syntax syntax()
	{
		return Syntax.reporterSyntax
			( new int[] { Syntax.TYPE_LIST } ,
			  Syntax.TYPE_NUMBER ) ;
	}
}