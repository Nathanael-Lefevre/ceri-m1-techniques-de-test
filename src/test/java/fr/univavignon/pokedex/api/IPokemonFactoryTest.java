package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokemonFactoryTest {
    @Before
    void init() throws PokedexException;

    @Test
    void testShouldReturnPokemon();

    @Test
    void testShouldReturnPokemonWithCorrectStats() throws PokedexException;

    @Test
    void testShouldReturnNullWhenPokedexException();
}
