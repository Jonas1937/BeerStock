package com.jonas.testebeer.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
        when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.of(defaultModelBeer));

        // then
        Optional<Beer> searchedBeer = beerServices.searchBeerFromName(defaultModelBeer.getName());
        assertThat(searchedBeer.get(), is(equalTo(defaultModelBeer)));
    }

    @Test
    public void testSearchedBeerInDatabaseFailed() throws BeerNotFoundException {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        // when
        when(beerRepository.findByName(defaultModelBeer.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(BeerNotFoundException.class, () -> beerServices.searchBeerFromName(defaultModelBeer.getName()));
    }

    @Test
    public void testFindAllOnDatabase() {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        // when
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(defaultModelBeer));

        // then
        List<Beer> findAll = beerServices.findAll();
        assertThat(findAll.get(0), is(defaultModelBeer));
    }

    @Test
    public void testFindAllButNotFoundOnDatabase() {

        // when
        when(beerRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<Beer> findAll = beerServices.findAll();
        assertThat(findAll, is(empty()));
    }

    @Test
    public void testDeleteBeerByIdWithSucess() throws BeerNotFoundException {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        // when
        when(beerRepository.findById(defaultModelBeer.getId())).thenReturn(Optional.of(defaultModelBeer));
        doNothing().when(beerRepository).deleteById(defaultModelBeer.getId());

        // then
        beerServices.deleteBeerById(defaultModelBeer.getId());

        verify(beerRepository, times(1)).findById(defaultModelBeer.getId());
        verify(beerRepository, times(1)).deleteById(defaultModelBeer.getId());

    }

    @Test
    public void testDeleteBeerByIdFailed() throws BeerNotFoundException {
        // given
        Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

        //when
        when(beerRepository.findById(defaultModelBeer.getId())).thenReturn(Optional.empty());
        
        assertThrows(BeerNotFoundException.class , () -> beerServices.deleteBeerById(defaultModelBeer.getId()));
    }
}
