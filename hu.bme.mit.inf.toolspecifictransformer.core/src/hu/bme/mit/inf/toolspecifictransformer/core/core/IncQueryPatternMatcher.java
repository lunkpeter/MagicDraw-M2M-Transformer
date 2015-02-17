package hu.bme.mit.inf.toolspecifictransformer.core.core;

import java.util.Collection;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.runtime.api.*;
/**
 * conducts IncQuery pattern matching
 * @author Lunk Péter
 *
 */
public class IncQueryPatternMatcher{
	
	protected static IncQueryPatternMatcher instance;
	
	/**
	 * Returns singleton instance
	 * @return
	 */
	public static IncQueryPatternMatcher getInstance(){
		if (instance == null)
			instance = new IncQueryPatternMatcher();
		return instance;
	}
	
	/**
	 * conducts IncQuery pattern matching on an EMF resource based on the provided patterns
	 * @param pattern	Patterns
	 * @param res	Resource
	 * @return
	 */
	public Collection<? extends IPatternMatch> GetResults(IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>> pattern, Resource res) {
		Collection<? extends IPatternMatch> matches =null;
		Resource resource = res;
		
		if (resource != null) {
			try {
				// get all matches of the pattern
				// create an *unmanaged* engine to ensure that noone else is going
				// to use our engine
				
				AdvancedIncQueryEngine engine = (AdvancedIncQueryEngine) IncQueryEngineManager.getInstance().getIncQueryEngineIfExists(resource);
				
				if (engine == null)
					engine = AdvancedIncQueryEngine.createUnmanagedEngine(resource);
			    
			    IncQueryMatcher<? extends IPatternMatch> matcher;
			    		
			    matcher = engine.getMatcher(pattern);
				
				if (matcher!=null) {
					matches = matcher.getAllMatches();	
				}
				
			} catch (IncQueryException e) {
				e.printStackTrace();
				
			}
			
		} 
		else {
			System.out.println("not valid resource");
		}
		return matches;
	}
}
