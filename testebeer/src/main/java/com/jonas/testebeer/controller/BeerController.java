package com.jonas.testebeer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.exceptions.BeerAlreadyInDatabaseException;
import com.jonas.testebeer.exceptions.BeerNotFoundException;
import com.jonas.testebeer.repository.BeerRepository;
import com.jonas.testebeer.services.BeerServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {
    
    @Autowired
    private BeerServices beerServices;

    @GetMapping("/")
    public List<Beer> findAll(){
        return beerServices.findAll();
    }
    
    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Beer addBeerOnDatabase(@RequestBody @Valid Beer beer) throws BeerAlreadyInDatabaseException{
        beerServices.saveBeerOnDatabase(beer);
        return beer;
    }

    @GetMapping("/search/{name}")
    public Optional<Beer> searchBeerByName(@PathVariable String name) throws BeerNotFoundException{
        return beerServices.searchBeerFromName(name);
    }

}
