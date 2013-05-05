gwtcog
======

GWT-compatible unofficial port of encog-java-core and encog-java-examples (official site: http://www.heatonresearch.com/encog)

The demo page is located at http://gwtcog.appspot.com

Currently supported examples are:
* XORNEAT
* HopfieldAssociate
* SimepleKMeans
* SimpleSOM
* BoltztSP
* PredictSunspotSVM

Please find included two Eclipse projects: gwtcog-core and gwtcog-examples. The former is a GWT compatible port of encog-java-core, the latter contains examples from encog-java-examples that shows which parts are known to be compatible with this port.

Use gwtcog-core as a GWT module. Compile it to a jar or link with the project in Eclipse. Do not forget to add &#60;inherits name='gwtcog.core.gwtcog_core'/&#60; in &#60;your application&#60;.gwt.xml"

You may need to add the standard Google App Engine jars to the project under "war/WEB-INF/lib". Please copy these from a sample GWT project. In the future I will make these jars installable through Maven.

Any feedback on this distribution may be directed at michiel.van.den.anker@gmail.com