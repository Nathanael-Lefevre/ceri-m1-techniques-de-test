package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokemonTrainerFactoryTest {
    @Before
    public void init() throws PokedexException;

    @Test
    public void testShouldReturnTrainer();

    @Test
    public void testShouldReturnTrainerWithCorrectName();
}
