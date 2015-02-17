package hu.bme.mit.inf.toolspecifictransformer.magicdrawmodelprovider;

import hu.bme.mit.inf.toolspecifictransformer.core.api.IModelProvider;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.integrations.eclipse.rcp.EclipseUMLPlugin;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Class responsible for loading the active model from an Eclipse integrated
 * MagicDraw instance
 * 
 * @author Lunk Péter
 * 
 */
@SuppressWarnings("deprecation")
public class MagicDrawModelProvider implements IModelProvider {

	private static MagicDrawModelProvider instance = null;
	
	/**
	 * Returns singleton instance
	 * @return
	 */
	public static MagicDrawModelProvider getInstance() {
		if (instance == null) {
			instance = new MagicDrawModelProvider();
		}
		return instance;
	}
	
	/**
	 * Returns the active MD model
	 * @return Resource of the active MD model
	 */
	public Resource GetModel() {
		EclipseUMLPlugin plugin = EclipseUMLPlugin.getEclipseUMLPlugin();

		plugin.startMagicDraw();

		Project project = Application.getInstance().getProjectsManager()
				.getActiveProject();

		if (project == null) {
			project = Application.getInstance().getProjectsManager()
					.createProject();
		}

		// getting project model
		Model model = project.getModel();

		if (model != null) {
			Collection<Element> elems = model.getOwnedElement();

			Stereotype BSStereotype = StereotypesHelper.getStereotype(project,
					"auxiliaryResource");

			try {
				for (Element element : elems) {
					if (StereotypesHelper.hasStereotype(element, BSStereotype)
							&& (!element.getHumanName().contains("SysML"))) {
						model.getOwnedElement().remove(element);
					}
				}
			} catch (Exception e) {
			}

			elems = model.getOwnedElement();
			for (Element element : elems) {
				if (StereotypesHelper.hasStereotype(element, BSStereotype)
						&& (!element.getHumanName().contains("SysML"))) {
					model.getOwnedElement().remove(element);
				}
			}

		}

		if (model == null) {
			System.out.println("die");
		}

		return model.eResource();
	}

}
