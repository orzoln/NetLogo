package org.nlogo.prim.etc ;

import org.nlogo.api.Dump;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _substring
	extends Reporter
	implements org.nlogo.nvm.Pure
{
	@Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
	{
		String string = argEvalString( context , 0 ) ;
		int start = argEvalIntValue( context , 1 ) ;
		int stop = argEvalIntValue( context , 2 ) ;
		if( start < 0 )
		{
			throw new EngineException
				( context , this  , I18N.errors().getNJava("org.nlogo.prim.etc._sublist.startIsLessThanZero",
                        new String[] {new Integer (start).toString()})) ;
		}
		else if( start > stop )
		{
			throw new EngineException
				( context , this  , I18N.errors().getNJava("org.nlogo.prim.etc._sublist.endIsLessThanStart",
                        new String[] {new Integer(stop).toString(), new Integer(start).toString()})) ;
		}
		else if( stop > string.length() )
		{
			throw new EngineException
				( context , this  ,  I18N.errors().getNJava("org.nlogo.prim.etc._substring.endIsGreaterThanListSize",
                        new String[] {Integer.toString(stop), Dump.logoObject(string), Integer.toString(string.length())}));
		}
		return string.substring( start , stop ) ;
	}
	@Override
	public Syntax syntax()
	{
		int[] right = { Syntax.TYPE_STRING , Syntax.TYPE_NUMBER , Syntax.TYPE_NUMBER } ;
		int ret = Syntax.TYPE_STRING ;
		return Syntax.reporterSyntax( right , ret ) ;
	}
}