package com.jonas.testebeer.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.enums.BeerType;
import com.jonas.testebeer.exceptions.BeerAlreadyInDatabaseException;
import com.jonas.testebeer.exceptions.BeerNotFoundException;
import com.jonas.testebeer.repository.BeerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TestBeerServices {

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerServices beerServices;

    @Test
    public void testBeerSave() throws BeerAlreadyInDatabaseException {
        // when
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(defaultModelBeer)).thenReturn(defaultModelBeer);

        // then
        Beer savedBeer = beerServices.saveBeerOnDatabase(defaultModelBeer);
        assertEquals(savedBeer.getName(), defaultModelBeer.getName());
        assertThat(savedBeer.getId(), is(equalTo(defaultModelBeer.getId())));

    }

    @Test
    public void testBeerSaveFailed() {
        // when
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);
        when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.of(defaultModelBeer));
        when(beerRepository.save(defaultModelBeer)).thenReturn(defaultModelBeer);

        // then
        assertThrows(BeerAlreadyInDatabaseException.class, () -> beerServices.saveBeerOnDatabase(defaultModelBeer));

    }

    @Test
    public void testSearchedBeerInDatabase() throws BeerNotFoundException {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        // when
        Mockito.when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.of(defaultModelBeer));

        // then
        Optional<Beer> searchedBeer = beerServices.searchBeerFromName(defaultModelBeer.getName());
        assertThat(searchedBeer.get(), is(equalTo(defaultModelBeer)));
    }

    @Test
    public void testSearchedBeerInDatabaseFailed() throws BeerNotFoundException {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        // when
        Mockito.when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(BeerNotFoundException.class,
             () -> beerServices.searchBeerFromName(defaultModelBeer.getName()));
    }

    @Test
    public void testFindAllOnDatabase() {
            // given
            Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

            //when
            Mockito.when(beerRepository.findAll())
                    .thenReturn(Collections.singletonList(defaultModelBeer));

            //then
            List<Beer> findAll = beerServices.findAll();
            assertThat(findAll.get(0), is(defaultModelBeer));
    }

}
