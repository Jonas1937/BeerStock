package com.jonas.testebeer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.exceptions.BeerAlreadyInDatabaseException;
import com.jonas.testebeer.exceptions.BeerMaxQuantityException;
import com.jonas.testebeer.exceptions.BeerNotFoundException;
import com.jonas.testebeer.services.BeerServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @DeleteMapping("/delete/{id}")
    public String deleteBeerById(@PathVariable int id) throws BeerNotFoundException{
        beerServices.deleteBeerById(id);
        return "Beer sucessfull deleted";
    }

    @PutMapping("/{id}/{quantity}")
    public Beer incrementQuantityForABeer(@PathVariable Long id, @PathVariable int quantity) throws BeerNotFoundException, BeerMaxQuantityException{
        return beerServices.increment(id, quantity);
    }

    @PutMapping("/dec/{id}/{quantity}")
    public Beer decrementQuantityForABeer(@PathVariable Long id, @PathVariable int quantity) throws BeerNotFoundException, BeerMaxQuantityException{

        return beerServices.decrement(id, quantity);
    }


}
