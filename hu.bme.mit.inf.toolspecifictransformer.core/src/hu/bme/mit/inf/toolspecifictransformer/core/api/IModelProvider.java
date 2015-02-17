package hu.bme.mit.inf.toolspecifictransformer.core.api;

import org.eclipse.emf.ecore.resource.Resource;
/**
 * Model provider interface, any class which is used for loading a model should
 * implement this interface
 * 
 * @author Lunk Péter
 * 
 */
public interface IModelProvider {
	public Resource GetModel();
}
