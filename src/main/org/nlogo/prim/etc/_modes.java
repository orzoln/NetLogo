package org.nlogo.prim.etc ;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.nlogo.agent.LogoHashObject;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.nvm.MutableInteger;
import org.nlogo.nvm.Pure;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _modes
    extends Reporter
	implements Pure
{
    @Override
	public Object report( final org.nlogo.nvm.Context context ) throws LogoException
    {
        LinkedHashMap<LogoHashObject,MutableInteger> counts =
			new LinkedHashMap<LogoHashObject,MutableInteger>();
        LogoList list = argEvalList( context , 0 ) ;
		for( Iterator<Object> it = list.iterator() ; it.hasNext() ; )
        {
            Object srcElt = it.next() ;
            LogoHashObject logoElt = new LogoHashObject( srcElt );
            if( counts.containsKey( logoElt ) )
            {
				counts.get( logoElt ).value++ ;
            }
            else
            {
                counts.put( logoElt, new MutableInteger( 1 ) ) ;
            }
        }

        Iterator<LogoHashObject> keys  = counts.keySet().iterator() ;
        int currMaxCount = 0 ;
        while( keys.hasNext() )
        {
            LogoHashObject currKey = keys.next() ;
            int currVal = counts.get( currKey ).value ;
            if( currVal > currMaxCount )
            {
                currMaxCount = currVal ;
            }
        }
        
        keys = counts.keySet().iterator() ;
        LogoListBuilder modes = new LogoListBuilder() ;
        while( keys.hasNext() )
        {
            LogoHashObject currKey = keys.next() ;
            int currVal = counts.get( currKey ).value ;
            if( currVal == currMaxCount )
            { 
                modes.add( currKey.getSourceObject() ) ;
            }
        }
        return modes.toLogoList() ;
    }
    @Override
	public Syntax syntax()
	{
	    int[] right = { Syntax.TYPE_LIST } ;
	    int ret = Syntax.TYPE_LIST ;
	    return Syntax.reporterSyntax( right , ret ) ;
	}
}