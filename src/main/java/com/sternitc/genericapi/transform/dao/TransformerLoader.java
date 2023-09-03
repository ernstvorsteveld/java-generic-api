package com.sternitc.genericapi.transform.dao;

import com.sternitc.genericapi.domain.Models;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;

public interface TransformerLoader {

    TransformerSpecification getSpecificationFor(Models model);

    void save(TransformerSpecification specification);
}
