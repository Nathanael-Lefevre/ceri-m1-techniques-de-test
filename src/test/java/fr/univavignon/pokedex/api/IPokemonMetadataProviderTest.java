package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public interface IPokemonMetadataProviderTest {

    @Before
    public void init() throws PokedexException;

    @Test
    public void testShouldReturnPokemonMetadataCorrespondingToRequestedIndex() throws PokedexException;

    @Test
    public void testShouldThrowPokedexExceptionWhenIncorrectIndexInRequest() throws PokedexException;
}
