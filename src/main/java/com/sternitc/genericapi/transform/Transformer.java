package com.sternitc.genericapi.transform;

import com.sternitc.genericapi.domain.Transformable;
import com.sternitc.genericapi.transform.domain.TransformerSpecification;

public interface Transformer<T extends Transformable> {

    T transformAdd(TransformerSpecification specification, byte[] source);
}
