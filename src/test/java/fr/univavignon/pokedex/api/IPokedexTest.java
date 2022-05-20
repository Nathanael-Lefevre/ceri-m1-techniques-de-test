package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

public interface IPokedexTest extends IPokemonMetadataProviderTest, IPokemonFactoryTest{
    @Before
    void init() throws PokedexException;

    /**
     * Initialisation de la preuve par récurrence du fonctionnement de size()
     */
    @Test
    void testShouldReturn0AsNumberOfPokemonInPokedex();

    @Test
    void testShouldAddPokemonToPokedex();

    @Test
    void testShouldReturnIndexOfNewlyAddedPokemon();

    /**
     * Preuve par récurrence du fonctionnement de size()
     */
    @Test
    void testShouldReturnIncrementedSizeAfterPokemonAdd();

    @Test
    void testShouldReturnPokemonAtCorrectIndex() throws PokedexException;

    @Test
    void testShouldThrowPokedexExceptionWhenIndexIsInvalid() throws PokedexException;

    @Test
    void testShouldReturnPockemonList();

    @Test
    void testPokemonListShouldNotBeModifiable();

    @Test
    void testShouldReturnPokemonListSortedByCP();

    @Test
    void testShouldReturnPokemonListSortedByINDEX();

    @Test
    void testShouldReturnPokemonListSortedByNAME();

    @Test
    void testSortedPokemonListShouldNotBeModifiable();

    @Test
    void testShouldReturnPokemonWithCorrectStats() throws PokedexException;
}
