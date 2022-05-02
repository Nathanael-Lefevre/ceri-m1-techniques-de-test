package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokemonFactoryTest {
    @Before
    public void init() throws PokedexException;

    @Test
    public void testShouldReturnPokemon();

    @Test
    public void testShouldReturnPokemonWithCorrectStats();
}
