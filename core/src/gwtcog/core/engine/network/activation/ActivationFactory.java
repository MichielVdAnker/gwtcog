package gwtcog.core.engine.network.activation;

public class ActivationFactory {

	public static Class<?> getActivationFunctionClass(String className) {
		
		if(className.equals("ActivationBiPolar")) {
			return ActivationBiPolar.class;
		}
		else if(className.equals("ActivationBipolarSteepenedSigmoid")) {
			return ActivationBipolarSteepenedSigmoid.class;
		}
		else if(className.equals("ActivationClippedLinear")) {
			return ActivationClippedLinear.class;
		}
		else if(className.equals("ActivationCompetitive")) {
			return ActivationCompetitive.class;
		}
		else if(className.equals("ActivationElliott")) {
			return ActivationElliott.class;
		}
		else if(className.equals("ActivationElliottSymmetric")) {
			return ActivationElliottSymmetric.class;
		}
		else if(className.equals("ActivationGaussian")) {
			return ActivationGaussian.class;
		}
		else if(className.equals("ActivationLinear")) {
			return ActivationLinear.class;
		}
		else if(className.equals("ActivationLOG")) {
			return ActivationLOG.class;
		}
		else if(className.equals("ActivationRamp")) {
			return ActivationRamp.class;
		}
		else if(className.equals("ActivationSigmoid")) {
			return ActivationSigmoid.class;
		}
		else if(className.equals("ActivationSIN")) {
			return ActivationSIN.class;
		}
		else if(className.equals("ActivationSoftMax")) {
			return ActivationSoftMax.class;
		}
		else if(className.equals("ActivationSteepenedSigmoid")) {
			return ActivationSteepenedSigmoid.class;
		}
		else if(className.equals("ActivationStep")) {
			return ActivationStep.class;
		}
		else if(className.equals("ActivationTANH")) {
			return ActivationTANH.class;
		}
		
		//defualt
		else {
			return null;
		}
		
	}

	public static ActivationFunction getActivationFunction(String className) {
		if(className.equals("ActivationBiPolar")) {
			return new ActivationBiPolar();
		}
		else if(className.equals("ActivationBipolarSteepenedSigmoid")) {
			return new ActivationBipolarSteepenedSigmoid();
		}
		else if(className.equals("ActivationClippedLinear")) {
			return new ActivationClippedLinear();
		}
		else if(className.equals("ActivationCompetitive")) {
			return new ActivationCompetitive();
		}
		else if(className.equals("ActivationElliott")) {
			return new ActivationElliott();
		}
		else if(className.equals("ActivationElliottSymmetric")) {
			return new ActivationElliottSymmetric();
		}
		else if(className.equals("ActivationGaussian")) {
			return new ActivationGaussian();
		}
		else if(className.equals("ActivationLinear")) {
			return new ActivationLinear();
		}
		else if(className.equals("ActivationLOG")) {
			return new ActivationLOG();
		}
		else if(className.equals("ActivationRamp")) {
			return new ActivationRamp();
		}
		else if(className.equals("ActivationSigmoid")) {
			return new ActivationSigmoid();
		}
		else if(className.equals("ActivationSIN")) {
			return new ActivationSIN();
		}
		else if(className.equals("ActivationSoftMax")) {
			return new ActivationSoftMax();
		}
		else if(className.equals("ActivationSteepenedSigmoid")) {
			return new ActivationSteepenedSigmoid();
		}
		else if(className.equals("ActivationStep")) {
			return new ActivationStep();
		}
		else if(className.equals("ActivationTANH")) {
			return new ActivationTANH();
		}
		
		//defualt
		else {
			return null;
		}
	}
	
}
