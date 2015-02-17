# MagicDraw-M2M-Transformer
Eclipse plugin-in that transforms a MagicDraw state machine model to a standard MDT UML instance

## Prerequisites
- Java 7
- Eclipse Modeling 4.2 Juno or newer
- Xtext 2.5.3
- EMF IncQuery 0.8
- Eclipse Integrated MagicDraw 18.0.1 or newer


## How to build

1. Download Eclipse MODELING (with EMF) 4.4 (preferably 64 bit version)
2. Install XText 2.5.3 -- [Update Site](http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/)
3. Install EMF IncQuery 0.8   -- [Update Site](http://download.eclipse.org/incquery/updates/milestone)
4. Download and Install MagicDraw 18.0.1
5. Close Eclipse
6. Run MagicDraw and select Tools menu/Integrations
  1. Select Eclipse 
  1. click Integrate/Unintegrate 
  1. Specifiy the newly installed Eclipse folder
  2. Click Integrate -- [Further information about MD Eclipse integration](http://www.nomagic.com/files/manuals/MagicDraw%20Integrations%20UserGuide.pdf)
7. Start Eclipse
8. Import projects into Eclipse
9. Check if the following plug-ins have the listed jars on the classpath
	- Plug-ins
	  - hu.bme.mit.inf.toolspecifictransformer.magicdrawmodelprovider
	  - hu.bme.mit.inf.toolspecifictransformer.magicdrawtransformer
	  - hu.bme.mit.inf.toolspecifictransformer.mdtransformpatterns
	- Jars
		- %MDINSTALL%/lib/md_api.jar
		- %MDINSTALL%/lib/md_foundation.jar
		- %MDINSTALL%/lib/uml2.jar
10. Run the project as an Eclipse Application (At first, open a MagicDraw project then try to transform the model)
