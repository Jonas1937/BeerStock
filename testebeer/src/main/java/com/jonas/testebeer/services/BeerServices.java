package com.jonas.testebeer.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.exceptions.BeerAlreadyInDatabaseException;
import com.jonas.testebeer.exceptions.BeerNotFoundException;
import com.jonas.testebeer.repository.BeerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeerServices {

    @Autowired
    private BeerRepository beerRepository;

    public BeerServices(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public Beer saveBeerOnDatabase(Beer beer) throws BeerAlreadyInDatabaseException {
        checkIfExistsABeerInDatabase(beer);
        beerRepository.save(beer);
        return beer;
    }

    public List<Beer> findAll() {
        return beerRepository.findAll().stream().collect(Collectors.toList());
    }

    public void deleteBeerById(long id) throws BeerNotFoundException {
        checkIfExistsABeerById(id);
        beerRepository.deleteById(id);
    }

    public void checkIfExistsABeerInDatabase(Beer beer) throws BeerAlreadyInDatabaseException {
        Optional<Beer> findByName = beerRepository.findByName(beer.getName());
        if (!findByName.isEmpty()) {
            throw new BeerAlreadyInDatabaseException();
        }
    }

    public Optional<Beer> searchBeerFromName(String name) throws BeerNotFoundException {
        Optional<Beer> searchedBeer = beerRepository.findByName(name);
        if (searchedBeer.isEmpty()) {
            throw new BeerNotFoundException(name);
        }
        return searchedBeer;
    }

    public void checkIfExistsABeerById(long id) throws BeerNotFoundException{
        Optional<Beer> beer = beerRepository.findById(id);
            if(beer.isEmpty()){
                throw new BeerNotFoundException(id);
            }
    }
}
