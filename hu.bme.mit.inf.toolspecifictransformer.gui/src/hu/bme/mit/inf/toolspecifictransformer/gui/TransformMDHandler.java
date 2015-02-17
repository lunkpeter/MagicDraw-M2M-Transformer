package hu.bme.mit.inf.toolspecifictransformer.gui;

import hu.bme.mit.inf.toolspecifictransformer.core.api.TransformationAPI;
import hu.bme.mit.inf.toolspecifictransformer.magicdrawmodelprovider.MagicDrawModelProvider;

import java.io.File;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class TransformMDHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Display display = Display.getDefault();
		final Shell shell = display.getActiveShell();

		DirectoryDialog dirDialog = new DirectoryDialog(shell);
		dirDialog.setText("Select the output directory");
		final String selectedDir = dirDialog.open();

		MDTransformationJob job = new MDTransformationJob(
				"MD Model Transformation", selectedDir);

		// Start the Job
		job.schedule();

		return null;
	}

	private void syncWithUi(final Shell shell, final Resource model) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(shell, "Transformation results ",
						"Model Transformation finished.");

				File fileToOpen = new File(model.getURI().devicePath());

				if (fileToOpen.exists() && fileToOpen.isFile()) {
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(
							fileToOpen.toURI());
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();

					try {
						IDE.openEditorOnFileStore(page, fileStore);
					} catch (PartInitException e) {
						// Put your exception handler here if you wish to
					}
				} else {
					// Do something if the file does not exist
				}

			}
		});

	}

	class MDTransformationJob extends Job {
		private String selectedDir;
		final Shell shell = Display.getDefault().getActiveShell();

		public MDTransformationJob(String name, String dir) {

			super(name);

			selectedDir = dir;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			Resource MDModel;
			// EiqPatternProvider.getInstance().setPatternpath("D:\\MDMODELTRANS.eiq");

			monitor.beginTask("EA Model transformation", 100);

			try {
				MDModel = TransformationAPI.transformMagicDrawModel(
						MagicDrawModelProvider.getInstance().GetModel(),
						selectedDir);

			} catch (Exception e) {
				e.printStackTrace();
				return Status.CANCEL_STATUS;
			}
			monitor.worked(100);

			syncWithUi(shell, MDModel);
			return Status.OK_STATUS;
		}
	}

}
