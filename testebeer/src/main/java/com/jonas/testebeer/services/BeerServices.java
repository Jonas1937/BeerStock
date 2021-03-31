package com.jonas.testebeer.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.exceptions.BeerAlreadyInDatabaseException;
import com.jonas.testebeer.exceptions.BeerMaxQuantityException;
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

    public void checkIfExistsABeerById(long id) throws BeerNotFoundException {
        Optional<Beer> beer = beerRepository.findById(id);
        if (beer.isEmpty()) {
            throw new BeerNotFoundException(id);
        }
    }

    public Beer increment(long id, int quantityForIncrement) throws BeerNotFoundException, BeerMaxQuantityException {
        checkIfExistsABeerById(id);
        Optional<Beer> beerFound = beerRepository.findById(id);
        Beer beerToBeIncremented = beerFound.get();
        int beerQuantity = beerToBeIncremented.getQuantity() + quantityForIncrement;
        if (beerQuantity > beerToBeIncremented.getMax()) {
            throw new BeerMaxQuantityException(beerToBeIncremented.getName(), beerToBeIncremented.getMax());
        }
        beerToBeIncremented.setQuantity(beerQuantity);
        return beerRepository.save(beerToBeIncremented);
    }

    public Beer decrement(long id, int quantityForDecrement) throws BeerNotFoundException, BeerMaxQuantityException {
        checkIfExistsABeerById(id);
        Optional<Beer> beerFound = beerRepository.findById(id);
        Beer beerToBeDecremented = beerFound.get();
        int beerQuantity = beerToBeDecremented.getQuantity() - quantityForDecrement;
        if (beerQuantity > beerToBeDecremented.getMax()) {
            throw new BeerMaxQuantityException(beerToBeDecremented.getName(), beerToBeDecremented.getMax());
        }
        beerToBeDecremented.setQuantity(beerQuantity);
        return beerRepository.save(beerToBeDecremented);
    }
}
