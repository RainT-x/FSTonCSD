package cn.zitsong.whu.feature;

import weka.attributeSelection.*;

/**
 * @Description: Filter-based feature ranking techniques
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class FilterFeatureRankingTechnique {
	
	
	 /*
     * PCA
     */
    public static ASEvaluation pca() {
        return new PrincipalComponents();
    }


    /**
     * Statistics-based techniques
     */
    public static ASEvaluation chiSquare() {
        return new ChiSquaredAttributeEval();
    }

    public static ASEvaluation correlation() {
        return new CorrelationAttributeEval();
    }

    public static ASEvaluation clusteringVariation() {
        return new CVAttributeEval();
    }
	
    /**
     * Probability-based techniques
     */
    public static ASEvaluation probabilisticSignificance() {
        return new SignificanceAttributeEval();
    }

    public static ASEvaluation infoGain() {
        return new InfoGainAttributeEval();
    }

    public static ASEvaluation gainRatio() {
        return new GainRatioAttributeEval();
    }

    public static ASEvaluation symmetrical() {
        return new SymmetricalUncertAttributeEval();
    }

    /**
     * Instance-based techniques
     */
    public static ASEvaluation reliefF() {
        return new ReliefFAttributeEval();
    }

    public static ASEvaluation reliefFWeight() {
        ReliefFAttributeEval reliefWeighted = new ReliefFAttributeEval();
        reliefWeighted.setWeightByDistance(true);
        return reliefWeighted;
    }

    /**
     * Classifier-based techniques
     */
    public static ASEvaluation oneR() {
        return new OneRAttributeEval();
    }

    public static ASEvaluation svm() {
        return new SVMAttributeEval();
    }
}
