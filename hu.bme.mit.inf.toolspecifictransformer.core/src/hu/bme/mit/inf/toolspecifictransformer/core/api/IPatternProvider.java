package hu.bme.mit.inf.toolspecifictransformer.core.api;

import java.util.Collection;
import java.util.Map;

import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;
/**
 * pattern provider interface
 * @author Lunk Péter
 *
 */
public interface IPatternProvider {
	/**
	 * returns a map of Query specifications
	 * @return
	 */
	public Map<String, Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>> GetPatterns();
}
