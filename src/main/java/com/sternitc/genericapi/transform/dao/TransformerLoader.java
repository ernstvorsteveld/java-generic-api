package com.sternitc.genericapi.transform.dao;

import com.sternitc.genericapi.domain.TreasurUpModels;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;

public interface TransformerLoader {

    TransformerSpecification getSpecificationFor(TreasurUpModels model);

    void save(TransformerSpecification specification);
}
