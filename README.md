gwtcog
======

GWT-compatible unofficial port of encog-java-core (official site: http://www.heatonresearch.com/encog)

Please find included two Eclipse projects: gwtcog-core and gwtcog-examples. The former is a GWT compatible port of encog-java-core, the latter contains examples from encog-java-examples that shows which parts are known to be compatible with this port.

Use gwtcog-core as a GWT module. Compile it to a jar or link with the project in Eclipse. Do not forget to add <inherits name='gwtcog.core.gwtcog_core'/> in <your application>.gwt.xml

Any feedback on this distribution may be directed at michiel.van.den.anker@gmail.com