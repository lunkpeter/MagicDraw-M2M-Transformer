package hu.bme.mit.inf.toolspecifictransformer.core.api;

import hu.bme.mit.inf.toolspecifictransformer.core.core.PatternMatchingManager;
import hu.bme.mit.inf.toolspecifictransformer.magicdrawtransformer.MagicDrawTransformer;

import java.util.Collection;
import java.util.Map;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.incquery.runtime.api.IPatternMatch;

/**
 * Public API class of the transformation engine. Wraps a set of methods
 * responsible pattern matching and model transformation.
 * 
 * @author Lunk Péter
 * 
 */
public class TransformationAPI {
	/**
	 * Returns a new PatternMatchingManager instance which contains the provided
	 * pattern and model providers
	 * 
	 * @param patternProvider
	 * @param modelProvider
	 * @return
	 */
	public static PatternMatchingManager getLogic(
			IPatternProvider patternProvider, IModelProvider modelProvider) {
		PatternMatchingManager logic = new PatternMatchingManager(
				patternProvider, modelProvider);
		return logic;
	}

	/**
	 * Conducts Pattern matching, using a given PatternMatchingManager instance
	 * 
	 * @param logic
	 * @return
	 */
	public static Map<String, Collection<? extends IPatternMatch>> getMatches(
			PatternMatchingManager logic) {
		return logic.getMatchResults();
	}

	/**
	 * Transforms a MagicDraw model, saves it in the outpud directory specified
	 * and returns the EMF resource containing it.
	 * 
	 * @param res: 	MD model
	 * @param outputDir:	Output directory
	 * @return:	Transformed model
	 */
	public static Resource transformMagicDrawModel(Resource res,
			String outputDir) {

		return MagicDrawTransformer.getInstance()
				.transformModel(res, outputDir);
	}

}
