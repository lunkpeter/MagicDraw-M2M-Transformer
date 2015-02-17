package hu.bme.mit.inf.toolspecifictransformer.core.core;

import hu.bme.mit.inf.toolspecifictransformer.core.api.IModelProvider;
import hu.bme.mit.inf.toolspecifictransformer.core.api.IPatternProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;

/**
 * Class responsible for conducting pattern matching and managing patterns and
 * resources.
 * 
 * @author Lunk Péter
 * 
 */
public class PatternMatchingManager {

	private IPatternProvider patternProvider;
	private IModelProvider modelProvider;
	
	/**
	 * Public constructor
	 * 
	 * @param patternprovider
	 * @param modelprovider
	 */
	public PatternMatchingManager(IPatternProvider patternprovider,
			IModelProvider modelprovider) {
		patternProvider = patternprovider;
		modelProvider = modelprovider;

	}
	
	/**
	 * Returns the match results, based on the modelprovider and patternprovider
	 * fields
	 * 
	 * @return
	 */
	public Map<String, Collection<? extends IPatternMatch>> getMatchResults() {
		Map<String, Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>> patterns = patternProvider
				.GetPatterns();
		Resource model = modelProvider.GetModel();

		Map<String, Collection<? extends IPatternMatch>> retval = new HashMap<String, Collection<? extends IPatternMatch>>();
		for (String key : patterns.keySet()) {
			Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>> p = patterns.get(key);

			for (IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>> pat : p) {

				Collection<? extends IPatternMatch> matches = IncQueryPatternMatcher
						.getInstance().GetResults(pat, model);

				retval.put(pat.getFullyQualifiedName(), matches);

			}
		}

		return retval;
	}

}
