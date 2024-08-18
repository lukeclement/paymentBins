package org.lukario.model;

import org.lukario.model.dto.Dto;

public interface Model<D extends Dto >{
    D toDto();
}
