package hu.bme.mit.inf.toolspecifictransformer.magicdrawtransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.Vertex;

import patterns.*;

import api.MDtransformAPI;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

public class MagicDrawTransformer {

	protected static MagicDrawTransformer instance = null;
	List<Package> packages;
	List<StateMachine> SMs;
	List<Region> regions;
	List<Vertex> vertices;
	List<com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Transition> mdtransitions;
	List<com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex> mdvertices;
	List<com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State> mdstates;

	/**
	 * Returns the Singleton instance of this plug-in class
	 * 
	 * @return -- The singleton EATransformer instance
	 */
	public static MagicDrawTransformer getInstance() {
		if (instance == null) {
			instance = new MagicDrawTransformer();
		}
		return instance;
	}

	private MagicDrawTransformer() {
		packages = new ArrayList<>();
		SMs = new ArrayList<>();
		regions = new ArrayList<>();
		vertices = new ArrayList<>();
		mdtransitions = new ArrayList<>();
		mdvertices = new ArrayList<>();
		mdstates = new ArrayList<>();

	}

	/**
	 * Transforms a given MD model and saves the transformed model in the
	 * specified output directory
	 * 
	 * @param mdmodel
	 * @param outputDir
	 * @return
	 */
	public Resource transformModel(Resource mdmodel, String outputDir) {

		System.out.println("Model transformation started");
		Model model = UMLFactory.eINSTANCE.createModel();
		packages = new ArrayList<>();
		SMs = new ArrayList<>();
		regions = new ArrayList<>();
		vertices = new ArrayList<>();
		mdtransitions = new ArrayList<>();
		mdvertices = new ArrayList<>();
		mdstates = new ArrayList<>();

		Collection<? extends IPatternMatch> matches = MDtransformAPI
				.getRootPackages(mdmodel);

		// create model and root packages

		List<String> rootpackagenames = new ArrayList<>();
		String modelname = "";

		for (IPatternMatch match : matches) {

			com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model tempM = (com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model) match
					.get(0);
			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package tempP = (com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) match
					.get(1);

			modelname = tempM.getName();
			if (!rootpackagenames.contains(tempP.getName())) {
				rootpackagenames.add(tempP.getName());
			}

		}

		model.setName(modelname);

		for (String name : rootpackagenames) {
			packages.add(addPackage(model, name));
		}

		// create other packages

		matches = MDtransformAPI.getPackages(mdmodel);

		for (IPatternMatch match : matches) {

			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package tempparent = (com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) match
					.get(0);
			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package tempP = (com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) match
					.get(1);

			List<Package> packagestoadd = new ArrayList<>();

			for (Package pack : packages) {

				if (pack.getQualifiedName()
						.equals(getQualifiedName(tempparent))) {
					Package temp = addPackage(pack, tempP.getName());
					packagestoadd.add(temp);

				}
			}

			packages.addAll(packagestoadd);
		}

		// create state machines

		matches = MDtransformAPI.getSMachines(mdmodel);
		for (IPatternMatch match : matches) {

			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package tempparent = (com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) match
					.get(0);
			com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine tempSM = (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine) match
					.get(1);

			List<StateMachine> SMstoadd = new ArrayList<>();

			for (Package pack : packages) {

				if (pack.getQualifiedName()
						.equals(getQualifiedName(tempparent))) {

					SMstoadd.add(addStateMachine(pack, tempSM.getName()));

				}
			}

			SMs.addAll(SMstoadd);
		}

		// create region and composite state structure

		matches = MDtransformAPI.getRegions(mdmodel);
		for (IPatternMatch match : matches) {

			com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine tempparent = (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine) match
					.get(0);
			com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region tempR = (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region) match
					.get(1);

			List<Region> Regionstoadd = new ArrayList<>();

			for (StateMachine sm : SMs) {

				if (sm.getQualifiedName().equals(getQualifiedName(tempparent))) {

					Regionstoadd.add(addRegion(sm, tempR.getName()));

				}
			}

			regions.addAll(Regionstoadd);

			for (Region region : Regionstoadd) {
				// traverse region-state hierarchy and add pseudo states
				traverseRegion(((RegionsMatch) match).getRegion(), region,
						mdmodel);
			}

		}

		// add transitions
		Vertex source = null;
		Vertex target = null;
		Region parent = null;

		List<com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex> verticestemp = mdvertices;
		for (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex src : mdvertices) {
			for (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Vertex targ : verticestemp) {
				Collection<? extends IPatternMatch> transitions2 = MDtransformAPI
						.getTransitions(mdmodel, src, targ);
				for (IPatternMatch iPatternMatch : transitions2) {
					com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Transition mdtr = ((TransitionsMatch) iPatternMatch)
							.getTr();
					if (!mdtransitions.contains(mdtr)) {

						for (Vertex vertex : vertices) {
							if (vertex
									.getQualifiedName()
									.equals(getQualifiedName(((TransitionsMatch) iPatternMatch)
											.getSource()))) {
								source = vertex;
							}
						}

						for (Vertex vertex : vertices) {
							if (vertex
									.getQualifiedName()
									.equals(getQualifiedName(((TransitionsMatch) iPatternMatch)
											.getTarget()))) {
								target = vertex;
							}
						}

						for (Region region : regions) {
							if (region
									.getQualifiedName()
									.equals(getQualifiedName(((TransitionsMatch) iPatternMatch)
											.getRegion()))) {
								parent = region;
							}
						}

						mdtransitions.add(mdtr);
						Transition transition = addTransition(parent,
								mdtr.getName(), source, target);
						Collection<? extends IPatternMatch> guards = MDtransformAPI
								.getGuards(mdmodel, mdtr);
						for (IPatternMatch iPatternMatch2 : guards) {
							com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint con = ((GuardsMatch) iPatternMatch2)
									.getCon();

							Collection<? extends IPatternMatch> expression = MDtransformAPI
									.getExpression(mdmodel, con);
							List<String> bodies = new ArrayList<>();

							for (IPatternMatch iPatternMatch3 : expression) {
								com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification ex = ((ExpressionMatch) iPatternMatch3)
										.getEx();
								bodies = ((com.nomagic.uml2.ext.magicdraw.classes.mdkernel.OpaqueExpression) ex)
										.getBody();
							}

							addGuard(transition, con.getName(), bodies);

						}
					}
				}
			}
		}

		for (com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State state : mdstates) {
			Collection<? extends IPatternMatch> subSMs = MDtransformAPI
					.getSubSM(mdmodel, state);
			for (IPatternMatch iPatternMatch : subSMs) {
				com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.StateMachine mdsm = ((SubSMMatch) iPatternMatch)
						.getSm();
				com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State mdstate = ((SubSMMatch) iPatternMatch)
						.getState();

				for (Vertex vert : vertices) {
					if ((vert instanceof State)
							&& vert.getQualifiedName().equals(
									getQualifiedName(mdstate))) {
						for (StateMachine statemachine : SMs) {
							if (statemachine.getQualifiedName().equals(
									getQualifiedName(mdsm))) {
								addSubSM((State) vert, statemachine);
							}
						}
					}
				}

			}
		}

		// create resource
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI
				.createFileURI(outputDir + "\\" + model.getName() + ".uml"));
		resource.getContents().add(model);
		System.out.println("Model transformation done.");
		saveModel(resource);
		return resource;
	}

	/**
	 * Method that recursively traverses every Region in the model, enabling the
	 * support of composite states
	 * 
	 * @param mdregion:	MD region to be traversed
	 * @param region: UML2 region equivalent to the MD region
	 * @param mdmodel: MD model object
	 */
	private void traverseRegion(
			com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.Region mdregion,
			Region region, Resource mdmodel) {
		Collection<? extends IPatternMatch> join = MDtransformAPI.getJoin(
				mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : join) {
			mdvertices.add(((JoinMatch) iPatternMatch).getPs());
			vertices.add(addJoin(region, ((JoinMatch) iPatternMatch).getPs()
					.getName()));
		}

		Collection<? extends IPatternMatch> fork = MDtransformAPI.getFork(
				mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : fork) {
			mdvertices.add(((ForkMatch) iPatternMatch).getPs());
			vertices.add(addfork(region, ((ForkMatch) iPatternMatch).getPs()
					.getName()));
		}

		Collection<? extends IPatternMatch> initial = MDtransformAPI
				.getInitial(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : initial) {
			mdvertices.add(((InitialMatch) iPatternMatch).getPs());
			vertices.add(addInit(region, ((InitialMatch) iPatternMatch).getPs()
					.getName()));
		}

		Collection<? extends IPatternMatch> finalState = MDtransformAPI
				.getFinalState(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : finalState) {
			mdvertices.add(((FinalStateMatch) iPatternMatch).getPs());
			vertices.add(addFinalState(region,
					((FinalStateMatch) iPatternMatch).getPs().getName()));
		}

		Collection<? extends IPatternMatch> terminate = MDtransformAPI
				.getTerminate(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : terminate) {
			mdvertices.add(((TerminateMatch) iPatternMatch).getPs());
			vertices.add(addTerminate(region, ((TerminateMatch) iPatternMatch)
					.getPs().getName()));
		}

		Collection<? extends IPatternMatch> choice = MDtransformAPI.getChoice(
				mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : choice) {
			mdvertices.add(((ChoiceMatch) iPatternMatch).getPs());
			vertices.add(addDecision(region, ((ChoiceMatch) iPatternMatch)
					.getPs().getName()));
		}

		Collection<? extends IPatternMatch> deepHist = MDtransformAPI
				.getDeepHist(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : deepHist) {
			mdvertices.add(((DHistMatch) iPatternMatch).getPs());
			vertices.add(addDeepHist(region, ((DHistMatch) iPatternMatch)
					.getPs().getName()));
		}

		Collection<? extends IPatternMatch> shallowHist = MDtransformAPI
				.getShallowHist(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : shallowHist) {
			mdvertices.add(((SHistMatch) iPatternMatch).getPs());
			vertices.add(addShallowHist(region, ((SHistMatch) iPatternMatch)
					.getPs().getName()));
		}
		Collection<? extends IPatternMatch> junctList = MDtransformAPI
				.getJunction(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : junctList) {
			mdvertices.add(((JunctionMatch) iPatternMatch).getPs());
			vertices.add(addjunction(region, ((JunctionMatch) iPatternMatch)
					.getPs().getName()));
		}

		Collection<? extends IPatternMatch> statematches = MDtransformAPI
				.getState(mdmodel, mdregion);
		for (IPatternMatch iPatternMatch : statematches) {
			com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.State mdstate = ((StateMatch) iPatternMatch)
					.getState();
			mdvertices.add(mdstate);
			mdstates.add(((StateMatch) iPatternMatch).getState());

			Vertex stateToAdd = addState(region, ((StateMatch) iPatternMatch)
					.getState().getName());

			Collection<? extends IPatternMatch> entry = MDtransformAPI
					.getEntry(mdmodel, mdstate);
			for (IPatternMatch iPatternMatch2 : entry) {
				addEntry((State) stateToAdd, ((EntryMatch) iPatternMatch2)
						.getB().getName());
			}

			Collection<? extends IPatternMatch> exit = MDtransformAPI.getExit(
					mdmodel, mdstate);
			for (IPatternMatch iPatternMatch2 : exit) {
				addExit((State) stateToAdd, ((ExitMatch) iPatternMatch2).getB()
						.getName());
			}

			Collection<? extends IPatternMatch> doAct = MDtransformAPI.getDo(
					mdmodel, mdstate);
			for (IPatternMatch iPatternMatch2 : doAct) {
				addDo((State) stateToAdd, ((DoActMatch) iPatternMatch2).getB()
						.getName());
			}

			Collection<? extends IPatternMatch> invariants = MDtransformAPI
					.getinvariant(mdmodel, mdstate);
			for (IPatternMatch iPatternMatch2 : invariants) {

				com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint c = ((InvariantMatch) iPatternMatch2)
						.getC();

				Collection<? extends IPatternMatch> expression = MDtransformAPI
						.getExpression(mdmodel, c);
				List<String> bodies = new ArrayList<>();

				for (IPatternMatch iPatternMatch3 : expression) {
					com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ValueSpecification ex = ((ExpressionMatch) iPatternMatch3)
							.getEx();
					bodies = ((com.nomagic.uml2.ext.magicdraw.classes.mdkernel.OpaqueExpression) ex)
							.getBody();
				}

				addSInvariant((State) stateToAdd, c.getName(), bodies);

			}

			vertices.add(stateToAdd);
			Collection<? extends IPatternMatch> compositeRegions = MDtransformAPI
					.getCompositeRegions(mdmodel,
							((StateMatch) iPatternMatch).getState());
			for (IPatternMatch iPatternMatch2 : compositeRegions) {
				Region compregiontoadd = addCompRegion((State) stateToAdd,
						((RegionsCompositeMatch) iPatternMatch2).getRegion()
								.getName());
				regions.add(compregiontoadd);
				traverseRegion(
						((RegionsCompositeMatch) iPatternMatch2).getRegion(),
						compregiontoadd, mdmodel);
			}
		}

	}
	
	/**
	 * Saves the UML2 model object
	 * @param model
	 */
	private void saveModel(Resource model) {
		try {
			model.save(null); // no save options needed
			System.out.println("Model saving done.");
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	/**
	 * Helper method that adds an EMF State to the given EMF Region
	 * 
	 * @param region
	 *            -- Region containing the State
	 * @param name
	 *            -- Name of the new State
	 * @return -- The created State
	 */
	private Vertex addState(Region region, String name) {

		Vertex st = region.createSubvertex(name, UMLPackage.Literals.STATE);
		return st;

	}
	
	/**
	 * Helper method that adds a final State instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addFinalState(Region region, String name) {

		Vertex st = region.createSubvertex(name,
				UMLPackage.Literals.FINAL_STATE);
		return st;

	}
	
	
	/**
	 * Helper method that adds a Fork instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addfork(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.FORK_LITERAL);
		ps.setContainer(region);
		return ps;
	}

	/**
	 * Helper method that adds a junction instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addjunction(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.JUNCTION_LITERAL);
		ps.setContainer(region);
		return ps;
	}
	
	/**
	 * Helper method that adds a Join instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addJoin(Region region, String name) {
		// Vertex st = region.createSubvertex(name,
		// UMLPackage.Literals.JOIN_NODE);
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.JOIN_LITERAL);
		ps.setContainer(region);
		return ps;
	}
	
	/**
	 * Helper method that adds an Initial State instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addInit(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.INITIAL_LITERAL);
		ps.setContainer(region);
		// Vertex st = region.createSubvertex(name,
		// UMLPackage.Literals.INITIAL_NODE);

		return ps;
	}
	
	/**
	 * Helper method that adds a terminate instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addTerminate(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.TERMINATE_LITERAL);
		ps.setContainer(region);
		// Vertex st = region.createSubvertex(name,
		// UMLPackage.Literals.FINAL_STATE);

		return ps;
	}
	
	/**
	 * Helper method that adds a Choice instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addDecision(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.CHOICE_LITERAL);
		ps.setContainer(region);

		// Vertex st = region.createSubvertex(name,
		// UMLPackage.Literals.DECISION_NODE);

		return ps;
	}

	/**
	 * Helper method that adds a Deep History Indicator instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addDeepHist(Region region, String name) {

		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.SHALLOW_HISTORY_LITERAL);
		ps.setContainer(region);

		return ps;
	}
	
	
	/**
	 * Helper method that adds a Shallow History Indicator instance to the EMF model
	 * @param region -- Region containing the state
	 * @param name -- name of the state
	 * @return -- the created Pseudo state
	 */
	private Vertex addShallowHist(Region region, String name) {
		Pseudostate ps = UMLFactory.eINSTANCE.createPseudostate();
		ps.setName(name);
		ps.setKind(PseudostateKind.SHALLOW_HISTORY_LITERAL);
		ps.setContainer(region);
		return ps;
	}
	
	/**
	 * Helper method that adds an EMF Transition to the given EMF Region, having
	 * the specified EMF States as source and target
	 * 
	 * @param reg
	 *            -- Region containing the transition
	 * @param name
	 *            -- Name of the transition
	 * @param source
	 *            -- Source state of the transition
	 * @param target
	 *            -- Target state of the transition
	 * @return -- The created transition
	 */
	private Transition addTransition(Region reg, String name, Vertex source,
			Vertex target) {
		Transition tran = UMLFactory.eINSTANCE.createTransition();
		tran.setName(name);
		tran.setContainer(reg);
		tran.setSource(source);
		tran.setTarget(target);

		if (source.getContainer().getQualifiedName()
				.equals(target.getContainer().getQualifiedName())) {
			tran.setKind(TransitionKind.LOCAL_LITERAL);
		}
		if (source.getQualifiedName().equals(target.getQualifiedName())) {
			tran.setKind(TransitionKind.INTERNAL_LITERAL);
		}
		return tran;
	}
	
	/**
	 * Helper method that adds an EMF PAckage to the given EMF Package
	 * 
	 * @param parent
	 *            -- Parent Package
	 * @param name
	 *            -- Name of the created EMF Package
	 * @return -- The created Package
	 */
	private Package addPackage(Package parent, String name) {
		Package p = UMLFactory.eINSTANCE.createPackage();
		p.setName(name);
		p.setNestingPackage(parent);
		return p;
	}
	
	/**
	 * Helper method that adds an EMF StateMachine to a given EMF Package
	 * 
	 * @param parent
	 *            -- Parent Package
	 * @param name
	 *            -- Name of the StateMachine
	 * @return -- the created StateMachine
	 */
	private StateMachine addStateMachine(Package parent, String name) {
		StateMachine sm = UMLFactory.eINSTANCE.createStateMachine();
		sm.setName(name);
		sm.setPackage(parent);
		return sm;
	}
	
	/**
	 * Helper method that adds an EMF Region to a given EMF StateMachine
	 * 
	 * @param parent
	 *            -- Parent State Machine
	 * @param name
	 *            -- Name of the Region
	 * @return -- The Created Region
	 */
	private Region addRegion(StateMachine parent, String name) {
		Region reg = UMLFactory.eINSTANCE.createRegion();
		reg.setName(name);
		reg.setStateMachine(parent);
		return reg;
	}
	
	/**
	 * Helper method that adds an EMF Region to a given EMF State
	 * 
	 * @param parent
	 *            -- Parent State
	 * @param name
	 *            -- Name of the Region
	 * @return -- The Created Region
	 */
	private Region addCompRegion(State parent, String name) {
		Region reg = UMLFactory.eINSTANCE.createRegion();
		reg.setName(name);
		reg.setState(parent);
		return reg;
	}
	
	/**
	 *  Helper method that adds a Guard Constraint element to the given Transition
	 * @param parent -- Parent Transition
	 * @param name -- Name of the element
	 * @param bodies -- Conditions
	 * @return
	 */
	private Constraint addGuard(Transition parent, String name,
			List<String> bodies) {
		Constraint guard = parent.createGuard(name);
		ValueSpecification specification = guard.createSpecification("",
				UMLFactory.eINSTANCE.createOpaqueExpression().getType(),
				UMLPackage.Literals.OPAQUE_EXPRESSION);
		((OpaqueExpression) specification).getBodies().addAll(bodies);

		return guard;
	}
	
	/**
	 * Adds the given State Machine to the given State as a SubStatemachine
	 * @param parent -- Parent State
	 * @param sm -- Sub State Machine
	 */
	private void addSubSM(State parent, StateMachine sm) {
		parent.setSubmachine(sm);
	}
	
	/**
	 * Adds an Entry Action object to the given State with the given name
	 * @param parent -- parent state
	 * @param name -- name
	 * @return
	 */
	private Behavior addEntry(State parent, String name) {
		FunctionBehavior createFunctionBehavior = UMLFactory.eINSTANCE
				.createFunctionBehavior();
		createFunctionBehavior.setName(name);
		parent.setEntry(createFunctionBehavior);
		return createFunctionBehavior;
	}

	/**
	 * Adds an Exit Action object to the given State with the given name
	 * @param parent -- parent state
	 * @param name -- name
	 * @return
	 */
	private Behavior addExit(State parent, String name) {
		FunctionBehavior createFunctionBehavior = UMLFactory.eINSTANCE
				.createFunctionBehavior();
		createFunctionBehavior.setName(name);
		parent.setExit(createFunctionBehavior);
		return createFunctionBehavior;
	}
	
	/**
	 * Adds a Do Action object to the given State with the given name
	 * @param parent -- parent state
	 * @param name -- name
	 * @return
	 */
	private Behavior addDo(State parent, String name) {
		FunctionBehavior createFunctionBehavior = UMLFactory.eINSTANCE
				.createFunctionBehavior();
		createFunctionBehavior.setName(name);
		parent.setDoActivity(createFunctionBehavior);
		return createFunctionBehavior;
	}
	
	/**
	 * Adds a State Invariant Object to the given State with the given name and condition bodies
	 * @param parent -- parent state
	 * @param name -- name
	 * @param bodies -- condition body
	 * @return
	 */
	private Constraint addSInvariant(State parent, String name,
			List<String> bodies) {
		// Constraint guard = parent.createStateInvariant(name);
		Constraint guard = UMLFactory.eINSTANCE.createConstraint();
		guard.setName(name);
		ValueSpecification specification = guard.createSpecification("",
				UMLFactory.eINSTANCE.createOpaqueExpression().getType(),
				UMLPackage.Literals.OPAQUE_EXPRESSION);
		((OpaqueExpression) specification).getBodies().addAll(bodies);
		parent.setStateInvariant(guard);

		return guard;
	}
	
	/**
	 * Returns the qualified name of the given MD object
	 * @param elem
	 * @return
	 */
	private String getQualifiedName(
			com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element elem) {
		String qname = "";
		com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement temp = (NamedElement) elem;
		while (true) {
			if (temp instanceof com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model) {
				qname = temp.getName() + qname;
				return qname;
			}
			qname = "::" + temp.getName() + qname;
			temp = (NamedElement) temp.getOwner();
		}
	}

}
