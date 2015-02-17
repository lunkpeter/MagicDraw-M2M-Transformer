package api;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.exception.IncQueryException;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Transition;
import com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex;


import patterns.*;
/**
 * API that wraps the usage of the IncQuery transformationh patterns
 * @author Lunk Péter
 *
 */
public class MDtransformAPI {
	
	/**
	 * Returns MD package objects that are contained by the model root
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getRootPackages(Resource model){
		Collection<RootPackagesMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			RootPackagesMatcher matcher = RootPackagesMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (Exception e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
		
	}
	
	/**
	 * Returns MD package objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getPackages(Resource model){
		Collection<PackagesMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			PackagesMatcher matcher = PackagesMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
		
	}
	
	/**
	 * Returns MD state machine objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getSMachines(Resource model){
		Collection<SmachinesMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			SmachinesMatcher matcher = SmachinesMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
		
	}
	
	/**
	 * Returns MD region objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getRegions(Resource model){
		Collection<RegionsMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			RegionsMatcher matcher = RegionsMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;		
	}
	
	/**
	 * Returns MD state objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getState(Resource model){
		Collection<StateMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			StateMatcher matcher = StateMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD composite state objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getCompositeRegions(Resource model){
		Collection<RegionsCompositeMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			RegionsCompositeMatcher matcher = RegionsCompositeMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD state objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getState(Resource model, Region region){
		Collection<StateMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			StateMatcher matcher = StateMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD region objects of the given composite state in the given MD model
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getCompositeRegions(Resource model, State state){
		Collection<RegionsCompositeMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			RegionsCompositeMatcher matcher = RegionsCompositeMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state,null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD initial state objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getInitial(Resource model){
		Collection<InitialMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			InitialMatcher matcher = InitialMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD terminate objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getTerminate(Resource model){
		Collection<TerminateMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			TerminateMatcher matcher = TerminateMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD initial state objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getInitial(Resource model, Region region){
		Collection<InitialMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			InitialMatcher matcher = InitialMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD terminate objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getTerminate(Resource model, Region region){
		Collection<TerminateMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			TerminateMatcher matcher = TerminateMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Fork objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getFork(Resource model){
		Collection<ForkMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ForkMatcher matcher = ForkMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Join objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getJoin(Resource model){
		Collection<JoinMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			JoinMatcher matcher = JoinMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Fork objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getFork(Resource model, Region region){
		Collection<ForkMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ForkMatcher matcher = ForkMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Join objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getJoin(Resource model, Region region){
		Collection<JoinMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			JoinMatcher matcher = JoinMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD choice objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getChoice(Resource model){
		Collection<ChoiceMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ChoiceMatcher matcher = ChoiceMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Deep History Indicator objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getDeepHist(Resource model){
		Collection<DHistMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			DHistMatcher matcher = DHistMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Shallow History Indicator objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getShallowHist(Resource model){
		Collection<SHistMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			SHistMatcher matcher = SHistMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Choice objects of the give Region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getChoice(Resource model, Region region){
		Collection<ChoiceMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ChoiceMatcher matcher = ChoiceMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Deep History Indicator objects of the give Region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getDeepHist(Resource model, Region region){
		Collection<DHistMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			DHistMatcher matcher = DHistMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Shallow History Indicator objects of the given Region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getShallowHist(Resource model, Region region){
		Collection<SHistMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			SHistMatcher matcher = SHistMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Transition objects in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getTransitions(Resource model){
		Collection<TransitionsMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			TransitionsMatcher matcher = TransitionsMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Transition objects with the given Source and Target Nodes in the given MD model
	 * @param model
	 * @param src
	 * @param targ
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getTransitions(Resource model, Vertex src, Vertex targ ){
		Collection<TransitionsMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			TransitionsMatcher matcher = TransitionsMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null, src,targ,null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD Junction objects, of the given Region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getJunction(Resource model, Region region){
		Collection<JunctionMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			JunctionMatcher matcher = JunctionMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns MD final state objects of the given region in the given MD model
	 * @param model
	 * @param region
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getFinalState(Resource model, Region region){
		Collection<FinalStateMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			FinalStateMatcher matcher = FinalStateMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(null,region);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the Sub State Machines of the given State, in the given MD model
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getSubSM(Resource model, State state){
		Collection<SubSMMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			SubSMMatcher matcher = SubSMMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the Entry action of the given state
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getEntry(Resource model, State state){
		Collection<EntryMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			EntryMatcher matcher = EntryMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the Exit Action of the given State
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getExit(Resource model, State state){
		Collection<ExitMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ExitMatcher matcher = ExitMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the Do Action of the given State
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getDo(Resource model, State state){
		Collection<DoActMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			DoActMatcher matcher = DoActMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the State Invariant of the given State
	 * @param model
	 * @param state
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getinvariant(Resource model, State state){
		Collection<InvariantMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			InvariantMatcher matcher = InvariantMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(state, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the guard condition of the given transition
	 * @param model
	 * @param tr
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getGuards(Resource model, Transition tr){
		Collection<GuardsMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			GuardsMatcher matcher = GuardsMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(tr, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns the Expressions contained in the given guard condition
	 * @param model
	 * @param c
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getExpression(Resource model, Constraint c){
		Collection<ExpressionMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			ExpressionMatcher matcher = ExpressionMatcher.on(engine);
			
			allMatches = matcher.getAllMatches(c, null);
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}
	
	/**
	 * Returns State invariants in the given MD model
	 * @param model
	 * @return
	 */
	public static Collection<? extends IPatternMatch> getinvariant(Resource model){
		Collection<InvariantMatch> allMatches = null;	
		try {
			IncQueryEngine engine = IncQueryEngine.on(model);
			
			InvariantMatcher matcher = InvariantMatcher.on(engine);
			
			allMatches = matcher.getAllMatches();
			
		} catch (IncQueryException e) {
			System.err.println("Could not initiate incquery engine on the given resource");
			e.printStackTrace();
		}
		
		return allMatches;
	}

}
