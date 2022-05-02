package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokedexTest {
    @Before
    public void init() throws PokedexException;

    /**
     * Initialisation de la preuve par récurrence du fonctionnement de size()
     */
    @Test
    public void testShouldReturn0AsNumberOfPokemonInPokedex();

    @Test
    public void testShouldAddPokemonToPokedex();

    @Test
    public void testShouldReturnIndexOfNewlyAddedPokemon();

    /**
     * Preuve par récurrence du fonctionnement de size()
     */
    @Test
    public void testShouldReturnIncrementedSizeAfterPokemonAdd();

    @Test
    public void testShouldReturnPokemonAtCorrectIndex() throws PokedexException;

    @Test
    public void testShouldThrowPokedexExceptionWhenIndexIsInvalid() throws PokedexException;

    @Test
    public void testShouldReturnPockemonList();

    @Test
    public void testPokemonListShouldNotBeModifiable();

    @Test
    public void testShouldReturnPokemonListSortedByCP();

    @Test
    public void testShouldReturnPokemonListSortedByINDEX();

    @Test
    public void testShouldReturnPokemonListSortedByNAME();

    @Test
    public void testSortedPokemonListShouldNotBeModifiable();
}
