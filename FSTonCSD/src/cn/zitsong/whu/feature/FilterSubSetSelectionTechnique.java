package cn.zitsong.whu.feature;


import weka.attributeSelection.*;

/**
 * @Description: Filter-based subset Selection techniques
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class FilterSubSetSelectionTechnique {

    /**
     * Correlation-based feature subset selection
     */
    public static ASEvaluation CfsSubset() {
        return new CfsSubsetEval();
    }


    /**
     * Consistency-based feature subset selection
     */
    public static ASEvaluation consistencySubset() {
        return new ConsistencySubsetEval();
    }



}
