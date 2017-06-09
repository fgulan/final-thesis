package hr.fer.zemris.test;

import org.neuroph.nnet.learning.DynamicBackPropagation;

/**
 * @author Filip Gulan
 */
public class DBP extends DynamicBackPropagation {
    private double error = 1;

    @Override
    protected void updateNetworkWeights(double[] outputError) {
        super.updateNetworkWeights(outputError);
//        double err = getPreviousEpochError();
//        System.out.println(err);
//        if (err < error) {
//            error = err;
//        }
    }
}
