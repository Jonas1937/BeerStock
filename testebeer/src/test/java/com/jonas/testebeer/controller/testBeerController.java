package com.jonas.testebeer.controller;

import com.jonas.testebeer.entity.Beer;
import com.jonas.testebeer.enums.BeerType;
import com.jonas.testebeer.exceptions.BeerNotFoundException;
import com.jonas.testebeer.repository.BeerRepository;
import com.jonas.testebeer.services.BeerServices;
import com.jonas.testebeer.utils.ToJsonConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class testBeerController {

        private MockMvc mockMvc;

        @Mock
        private BeerServices beerServices;

        @InjectMocks
        private BeerController beerController;

        @BeforeEach
        public void setup() {
                this.mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
        }

        @Test
        public void testAddBeerOnPostRequest() throws Exception {

                // given
                Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

                // when
                Mockito.when(beerServices.saveBeerOnDatabase(defaultModelBeer)).thenReturn(defaultModelBeer);

                this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
                                .content(ToJsonConverter.asJsonString(defaultModelBeer)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name", is(defaultModelBeer.getName())));

        }

        @Test
        public void testAddBeerFailed() throws Exception {
                // given
                Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);
                defaultModelBeer.setName(null);

                // when
                this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
                                .content(ToJsonConverter.asJsonString(defaultModelBeer)))
                                .andExpect(status().isBadRequest());

        }

        @Test
        public void testSendingForSearchAValidBeerName() throws Exception {
                // given
                Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

                // when
                Mockito.when(beerServices.searchBeerFromName(defaultModelBeer.getName()))
                                .thenReturn(Optional.of(defaultModelBeer));

                // then
                mockMvc.perform(get("/search/Brahma").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name", is(defaultModelBeer.getName())))
                                .andExpect(jsonPath("$.brand", is(defaultModelBeer.getBrand())));
        }

        @Test
        public void testSendingForSearchAInvalidBeerName() throws Exception {
                // given
                Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

                // when
                Mockito.when(beerServices.searchBeerFromName(defaultModelBeer.getName()))
                                .thenThrow(BeerNotFoundException.class);

                // then
                mockMvc.perform(get("/search/Brahma").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testFindAllOnDatabase() {
                // given
                Beer defaultModelBeer = new Beer(1L, "Brahma", "Ambev", 50, 100, BeerType.LAGER);

                //when
                Mockito.when(beerServices.findAll())
                        .thenReturn(Collections.singletonList(defaultModelBeer));

                //then
                
        }

}
