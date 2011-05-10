package org.nlogo.prim.etc ;

import java.util.Iterator;

import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.nvm.ArgumentTypeException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;
import org.nlogo.api.Equality;

public final strictfp class _remove
	extends Reporter
	implements org.nlogo.nvm.Pure
{
	@Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
	{
		Object value = args[ 0 ].report( context ) ;
		Object obj = args[ 1 ].report( context ) ;
		if ( obj instanceof LogoList )
		{
			LogoList list = (LogoList) obj ;
			LogoListBuilder listCopy = new LogoListBuilder();
			for( Iterator<Object> it = list.iterator() ; it.hasNext() ; )
			{
				Object elt = it.next() ;
				if( ! Equality.equals( value , elt ) )
				{
					listCopy.add( elt ) ;
				}
			}
			return listCopy.toLogoList() ;
		}
		else if ( obj instanceof String )
		{
            if ( ! ( value instanceof String ) ){
				throw new ArgumentTypeException( context , this , 0 , Syntax.TYPE_STRING , value ) ;
			}
			String string = ( String ) obj ;
			String elt = (String) value ;
			StringBuilder sbCopy = new StringBuilder() ;
			if ( elt.length() <= string.length() && elt.length() != 0 )
			{
				int i = 0 ;
				while ( i <= string.length() - elt.length() )
				{
					if( ! string.regionMatches ( i , elt, 0 , elt.length() ) )
					{
						sbCopy.append( string.charAt( i ) ) ;
						i++ ;
					}
					else 
					{
						i = i + elt.length() ;
					}
				}
				if ( i > string.length() - elt.length() && ( ! string.regionMatches ( i , elt, 0 , elt.length() )))
				{
					sbCopy.append( string.substring( i ) ) ;   
				}
				return sbCopy.toString() ;
			}
			else 
			{
				return string ;
			}
		}
		else 
		{
			throw new ArgumentTypeException
				( context , this , 1 , Syntax.TYPE_LIST | Syntax.TYPE_STRING , obj ) ; 
		}
	}
	@Override
	public Syntax syntax()
	{
		int[] right = { Syntax.TYPE_WILDCARD ,
						Syntax.TYPE_LIST | Syntax.TYPE_STRING  } ;
		int ret = Syntax.TYPE_LIST | Syntax.TYPE_STRING ;
		return Syntax.reporterSyntax( right , ret ) ;
	}
}