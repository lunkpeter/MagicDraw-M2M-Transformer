package hu.bme.mit.inf.toolspecifictransformer.eiqpatternprovider.core;

import hu.bme.mit.inf.toolspecifictransformer.core.api.IPatternProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.incquery.patternlanguage.emf.EMFPatternLanguageStandaloneSetup;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PatternModel;
import org.eclipse.incquery.patternlanguage.emf.specification.SpecificationBuilder;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;

/**
 * Loads IncQuery patterns form an external .eiq file
 * 
 * @author Lunk Péter
 * 
 */
public class EiqPatternProvider implements IPatternProvider {

	private static EiqPatternProvider instance = null;
	private String patternpath;

	public static EiqPatternProvider getInstance() {
		if (instance == null) {
			instance = new EiqPatternProvider();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>> GetPatterns() {

		Map<String, Resource> patternresources = new HashMap<String, Resource>();

		Map<String, Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>> patterns = new HashMap<String, Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>>();

		SpecificationBuilder builder = new SpecificationBuilder();
		try {
			new EMFPatternLanguageStandaloneSetup()
					.createInjectorAndDoEMFRegistration();

			// use a trick to load Pattern models from a file
			ResourceSet resourceSet = new ResourceSetImpl();

			URI patternsURI = URI.createFileURI(patternpath);

			patternresources.put(patternpath,
					resourceSet.getResource(patternsURI, true));

			Collection<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>> temp = new ArrayList<IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>>();
			// navigate to the pattern definition that we want
			if (patternresources != null) {
				if (patternresources.get(patternpath).getErrors().size() == 0
						&& patternresources.get(patternpath).getContents()
								.size() >= 1) {
					EObject topElement = patternresources.get(patternpath)
							.getContents().get(0);
					if (topElement instanceof PatternModel) {
						for (Pattern _p : ((PatternModel) topElement)
								.getPatterns()) {
							if (!temp.contains(_p))
								temp.add((IQuerySpecification<IncQueryMatcher<? extends IPatternMatch>>) builder
										.getOrCreateSpecification(_p));
						}
					}
				}
			}

			patterns.put(patternpath, temp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return patterns;
	}

	public String getPatternpath() {
		return patternpath;
	}

	public void setPatternpath(String patternpath) {
		this.patternpath = patternpath;
	}
}
