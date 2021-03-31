package com.jonas.testebeer.mapper;

import java.util.Optional;

import com.jonas.testebeer.entity.Beer;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toBeer(Optional<Beer> optional);
    
    Optional<Beer> toOptional(Beer beer);
}
