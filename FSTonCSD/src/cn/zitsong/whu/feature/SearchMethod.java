package cn.zitsong.whu.feature;

import cn.zitsong.whu.constant.SuperParameter;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;

/**
 * @Description: Common search method
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2020-01-08 12:07
 * @Since: version 1.0.0
 **/
public class SearchMethod {

    private SearchMethod() {}

    public static BestFirst bestFirst() {
        return new BestFirst();
    }

    /**
    public static GeneticSearch geneticSearch() {
        return new GeneticSearch();
    } */

    public static GreedyStepwise greedyStepwise() {
        GreedyStepwise greedyStepwise =  new GreedyStepwise();
        greedyStepwise.setNumToSelect(SuperParameter.FEATURE_NUM);
        greedyStepwise.setSearchBackwards(true);
        return greedyStepwise;
    }

}
