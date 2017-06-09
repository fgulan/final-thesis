package hr.fer.zemris.test;

import org.neuroph.nnet.learning.BackPropagation;

/**
 * @author Filip Gulan
 */
public class BP extends BackPropagation {
    private double error = 1;
    public BP() {
        super();
        this.maxError = 0.1;
    }

    @Override
    protected void updateNetworkWeights(double[] outputError) {
        super.updateNetworkWeights(outputError);
        double err = getPreviousEpochError();
        if (err < error) {
            error = err;
            System.out.println(err);
        }
    }
}
