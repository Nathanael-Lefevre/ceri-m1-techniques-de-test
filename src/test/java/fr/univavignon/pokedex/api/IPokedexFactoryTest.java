package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokedexFactoryTest {
    @Before
    public void init() throws PokedexException;

    @Test
    public void testShouldReturnPokedex();
}
