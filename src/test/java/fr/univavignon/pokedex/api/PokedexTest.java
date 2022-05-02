package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PokedexTest implements IPokedexTest {
    IPokedex mPokedex;
    List<Pokemon> mPokemonList;
    Pokemon pokemon0;
    Pokemon pokemon1;
    Pokemon pokemon2;

    @Override
    @Before
    public void init() throws PokedexException {
        mPokemonList = new ArrayList<Pokemon>();
        pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);
        pokemon1 = new Pokemon(133, "Aquali", 186, 168, 260, 2729, 202, 5000, 4, 1.);
        pokemon2 = new Pokemon(6, "Carapuce", 44, 65, 0, 3310, 202, 5000, 4, 1.);

        mPokedex = Mockito.mock(IPokedex.class);

        when(mPokedex.size()).thenAnswer(
                new Answer<Integer>() {
                    public Integer answer(InvocationOnMock invocation) {
                        return mPokemonList.size();
                    }
                });

        when(mPokedex.addPokemon(any(Pokemon.class))).then(
                new Answer<>() {
            public Object answer(InvocationOnMock invocation) {
                Pokemon p = invocation.getArgument(0);
                mPokemonList.add(p);
                return mPokemonList.indexOf(p);
            }
        });

        when(mPokedex.getPokemon(any(Integer.class))).then(
                new Answer<>() {
                    public Pokemon answer(InvocationOnMock invocation) throws PokedexException {
                        Integer index = invocation.getArgument(0);
                        if (index < 0) {
                            throw new PokedexException("L'index du Pokemon dans le pokedex doit être supérieur à -1");
                        } else if (index > (mPokemonList.size() - 1)) {
                            throw new PokedexException("Le Pokedex ne contient pas autant de pokemon !");
                        }
                        return mPokemonList.get(index);
                    }
                });

        when(mPokedex.getPokemons()).then(
                new Answer<List<Pokemon>>() {
                    public List<Pokemon> answer(InvocationOnMock invocation) {
                        return Collections.unmodifiableList(mPokemonList);
                    }
                });

        when(mPokedex.getPokemons(PokemonComparators.CP)).then(
                new Answer<List<Pokemon>>() {
                    public List<Pokemon> answer(InvocationOnMock invocation) {
                        List<Pokemon> sortedCPList = new ArrayList<>(Arrays.asList(pokemon0, pokemon1, pokemon2));
                        return Collections.unmodifiableList(sortedCPList);
                    }
                });

        when(mPokedex.getPokemons(PokemonComparators.INDEX)).then(
                new Answer<List<Pokemon>>() {
                    public List<Pokemon> answer(InvocationOnMock invocation) {
                        List<Pokemon> sortedIndexList = new ArrayList<>(Arrays.asList(pokemon0, pokemon2, pokemon1));
                        return Collections.unmodifiableList(sortedIndexList);
                    }
                });

        when(mPokedex.getPokemons(PokemonComparators.NAME)).then(
                new Answer<List<Pokemon>>() {
                    public List<Pokemon> answer(InvocationOnMock invocation) {
                        List<Pokemon> sortedNameList = new ArrayList<>(Arrays.asList(pokemon1, pokemon0, pokemon2));
                        return Collections.unmodifiableList(sortedNameList);
                    }
                });
    }

    @Override
    @Test
    public void testShouldReturn0AsNumberOfPokemonInPokedex() {
        Assert.assertEquals(this.mPokedex.size(), 0);
    }

    @Override
    @Test
    public void testShouldAddPokemonToPokedex() {
        mPokedex.addPokemon(pokemon0);
        Assert.assertTrue(mPokemonList.contains(pokemon0));
    }

    @Override
    @Test
    public void testShouldReturnIndexOfNewlyAddedPokemon() {
        Assert.assertEquals(0, mPokedex.addPokemon(pokemon0));
        Assert.assertEquals(1, mPokedex.addPokemon(pokemon1));
    }

    @Override
    @Test
    public void testShouldReturnIncrementedSizeAfterPokemonAdd() {
        int sizeBefore = mPokedex.size();
        mPokedex.addPokemon(pokemon0);
        int sizeAfter = mPokedex.size();

        Assert.assertEquals(sizeAfter, sizeBefore + 1);
    }

    @Override
    @Test
    public void testShouldReturnPokemonAtCorrectIndex() throws PokedexException {
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        Assert.assertEquals(mPokedex.getPokemon(0), pokemon0);
        Assert.assertEquals(mPokedex.getPokemon(1), pokemon1);
        Assert.assertNotEquals(mPokedex.getPokemon(0), pokemon1);
    }

    @Override
    @Test
    public void testShouldThrowPokedexExceptionWhenIndexIsInvalid() throws PokedexException {
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(-2));  // not just -1
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(-1));
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(0));
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(1));
        mPokedex.addPokemon(pokemon0);
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(1));
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemon(33));  // not just 1
    }

    @Override
    @Test
    public void testShouldReturnPockemonList() {
        List<Pokemon> truePokemonList = new ArrayList<>(Arrays.asList(pokemon0, pokemon1));
        for (Pokemon p : truePokemonList) {
            mPokedex.addPokemon(p);
        }

        List<Pokemon> actualPokemonList = mPokedex.getPokemons();
        // Nothing less
        for (Pokemon pokemon : truePokemonList) {
            Assert.assertTrue(actualPokemonList.contains(pokemon));
        }

        // Nothing more
        for (Pokemon pokemon : actualPokemonList) {
            Assert.assertTrue(truePokemonList.contains(pokemon));
        }
    }

    @Override
    @Test
    public void testPokemonListShouldNotBeModifiable() {
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        List<Pokemon> res = mPokedex.getPokemons();
        Assert.assertThrows(UnsupportedOperationException.class, () -> res.remove(pokemon0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> res.add(pokemon2));
    }

    @Override
    @Test
    public void testShouldReturnPokemonListSortedByCP() {
        List<Pokemon> trueSortedCPList = new ArrayList<>(Arrays.asList(pokemon0, pokemon1, pokemon2));
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        mPokedex.addPokemon(pokemon2);

        List<Pokemon> resSortedCPList = mPokedex.getPokemons(PokemonComparators.CP);
        Assert.assertArrayEquals(new List[]{trueSortedCPList}, new List[]{resSortedCPList});
    }

    @Override
    @Test
    public void testShouldReturnPokemonListSortedByINDEX() {
        List<Pokemon> trueSortedIndexList = new ArrayList<>(Arrays.asList(pokemon0, pokemon2, pokemon1));
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        mPokedex.addPokemon(pokemon2);

        List<Pokemon> resSortedCPList = mPokedex.getPokemons(PokemonComparators.INDEX);
        Assert.assertArrayEquals(new List[]{trueSortedIndexList}, new List[]{resSortedCPList});
    }

    @Override
    @Test
    public void testShouldReturnPokemonListSortedByNAME() {
        List<Pokemon> trueSortedNameList = new ArrayList<>(Arrays.asList(pokemon1, pokemon0, pokemon2));
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        mPokedex.addPokemon(pokemon2);

        List<Pokemon> resSortedCPList = mPokedex.getPokemons(PokemonComparators.NAME);
        Assert.assertArrayEquals(new List[]{trueSortedNameList}, new List[]{resSortedCPList});
    }

    @Override
    @Test
    public void testSortedPokemonListShouldNotBeModifiable() {
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);

        List<Pokemon> resSortedCPList = mPokedex.getPokemons(PokemonComparators.CP);
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedCPList.remove(pokemon0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedCPList.add(pokemon2));

        List<Pokemon> resSortedIndexList = mPokedex.getPokemons(PokemonComparators.INDEX);
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedIndexList.remove(pokemon0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedIndexList.add(pokemon2));

        List<Pokemon> resSortedNameList = mPokedex.getPokemons(PokemonComparators.NAME);
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedNameList.remove(pokemon0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> resSortedNameList.add(pokemon2));
    }
}
