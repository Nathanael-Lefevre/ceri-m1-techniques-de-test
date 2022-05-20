package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PokedexTest implements IPokedexTest {
    @Mock private IPokedex mPokedex;
    @Mock private List<Pokemon> mPokemonList;
    @Mock private IPokemonMetadataProvider mMetadataProvider;
    private Pokemon pokemon0;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private PokemonMetadata metadata0;
    private PokemonMetadata metadata1;

    @Before
    public void initNonMock() {
        mPokemonList = new ArrayList<>();
        mMetadataProvider = new PokemonMetadataProvider();
        pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);
        pokemon1 = new Pokemon(133, "Aquali", 186, 168, 260, 2729, 202, 5000, 4, 1.);
        pokemon2 = new Pokemon(6, "Carapuce", 44, 65, 0, 3310, 202, 5000, 4, 1.);

        //mPokedex = new Pokedex(new PokemonMetadataProvider(), new PokemonFactory(new PokemonMetadataProvider()));
        mPokedex = new Pokedex(new PokemonMetadataProvider(), new RocketPokemonFactory());
    }


    @Override
    //@Before
    public void init() throws PokedexException {
        mMetadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        mPokemonList = new ArrayList<>();
        pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);
        pokemon1 = new Pokemon(133, "Aquali", 186, 168, 260, 2729, 202, 5000, 4, 1.);
        pokemon2 = new Pokemon(6, "Carapuce", 44, 65, 0, 3310, 202, 5000, 4, 1.);

        metadata0 = new PokemonMetadata(0, "Bulbizarre", 126, 126, 90);
        metadata1 = new PokemonMetadata(133, "Aquali", 186, 168, 260);

        mPokedex = Mockito.mock(IPokedex.class);

        when(mMetadataProvider.getPokemonMetadata(0)).thenReturn(metadata0);

        when(mPokedex.getPokemonMetadata(any(Integer.class))).then(
                invocation -> {
                    Integer index = invocation.getArgument(0);

                    if (index == 0) {
                        return metadata0;
                    } else if (index == 1) {
                        return metadata1;
                    } else if (index > 0 && index < 151) {
                        return metadata1;  // Sinon on retourne une métadata valide
                    }
                    throw new PokedexException("Aucune Métadonée n'existe pour l'index spécifié");
                });

        when(mPokedex.createPokemon(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class))).then(
                (Answer<Pokemon>) invocation -> {
                    Integer index = invocation.getArgument(0);
                    if (index == -1) {
                        return null;
                    }
                    String name = metadata0.getName();
                    int attack = metadata0.getAttack();
                    int defense = metadata0.getDefense();
                    int stamina = metadata0.getStamina();
                    return new Pokemon(0, name, attack, defense, stamina, 613, 64, 4000, 4, .56);
                }).then((Answer<Pokemon>) invocation -> {
                    Integer index = invocation.getArgument(0);
                    if (index == -1) {
                        return null;
                    }
                    String name = metadata0.getName();
                    int attack = metadata0.getAttack() + 15;
                    int defense = metadata0.getDefense() + 15;
                    int stamina = metadata0.getStamina() + 15;
                    return new Pokemon(0, name, attack, defense, stamina, 613, 64, 4000, 4, 1);
                });

        when(mPokedex.size()).thenAnswer(
                (Answer<Integer>) invocation -> mPokemonList.size());

        when(mPokedex.addPokemon(any(Pokemon.class))).then(
                invocation -> {
                    Pokemon p = invocation.getArgument(0);
                    mPokemonList.add(p);
                    return mPokemonList.indexOf(p);
                });

        when(mPokedex.getPokemon(any(Integer.class))).then(
                invocation -> {
                    Integer index = invocation.getArgument(0);
                    if (index < 0) {
                        throw new PokedexException("L'index du Pokemon dans le pokedex doit être supérieur à -1");
                    } else if (index > (mPokemonList.size() - 1)) {
                        throw new PokedexException("Le Pokedex ne contient pas autant de pokemon !");
                    }
                    return mPokemonList.get(index);
                });

        when(mPokedex.getPokemons()).then(
                (Answer<List<Pokemon>>) invocation -> Collections.unmodifiableList(mPokemonList));

        when(mPokedex.getPokemons(PokemonComparators.CP)).then(
                (Answer<List<Pokemon>>) invocation -> {
                    List<Pokemon> sortedCPList = new ArrayList<>(Arrays.asList(pokemon1, pokemon2, pokemon0));
                    sortedCPList.sort(PokemonComparators.CP);
                    return Collections.unmodifiableList(sortedCPList);
                });

        when(mPokedex.getPokemons(PokemonComparators.INDEX)).then(
                (Answer<List<Pokemon>>) invocation -> {
                    List<Pokemon> sortedIndexList = new ArrayList<>(Arrays.asList(pokemon2, pokemon0, pokemon1));
                    sortedIndexList.sort(PokemonComparators.INDEX);
                    return Collections.unmodifiableList(sortedIndexList);
                });

        when(mPokedex.getPokemons(PokemonComparators.NAME)).then(
                (Answer<List<Pokemon>>) invocation -> {
                    List<Pokemon> sortedNameList = new ArrayList<>(Arrays.asList(pokemon2, pokemon0, pokemon1));
                    sortedNameList.sort(PokemonComparators.NAME);
                    return Collections.unmodifiableList(sortedNameList);
                });
    }

    @Override
    @Test
    public void testShouldReturnPokemonMetadataCorrespondingToRequestedIndex() throws PokedexException {
        mPokedex.addPokemon(pokemon0);
        mPokedex.addPokemon(pokemon1);
        Assert.assertEquals(0, mPokedex.getPokemonMetadata(0).getIndex());
        Assert.assertEquals(133, mPokedex.getPokemonMetadata(1).getIndex());
    }

    @Override
    @Test
    public void testShouldThrowPokedexExceptionWhenIncorrectIndexInRequest() {
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemonMetadata(-1));
        Assert.assertThrows(PokedexException.class, () -> mPokedex.getPokemonMetadata(151));
    }

    @Override
    @Test
    public void testShouldReturnPokemon() {
        Assert.assertNotNull(mPokedex.createPokemon(0, 613, 64, 4000, 4));
    }

    @Override
    @Test
    public void testShouldReturnPokemonWithCorrectStats() throws PokedexException {
        Pokemon actualPokemon;
        for (int i = 0; i < 3; i++) {
            actualPokemon = mPokedex.createPokemon(
                    pokemon0.getIndex(),
                    pokemon0.getCp(),
                    pokemon0.getHp(),
                    pokemon0.getDust(),
                    pokemon0.getCandy());
            PokemonMetadata metadata = mMetadataProvider.getPokemonMetadata(0);

            Assert.assertEquals(0, actualPokemon.getIndex());
            Assert.assertEquals(metadata.getName(), actualPokemon.getName());
            Integer attackDiff = actualPokemon.getAttack() - metadata.getAttack();
            Assert.assertTrue( attackDiff >= 0 && attackDiff <= 15);

            Integer defenseDiff = actualPokemon.getDefense() - metadata.getDefense();
            Assert.assertTrue( defenseDiff >= 0 && defenseDiff <= 15);

            Integer staminaDiff = actualPokemon.getStamina() - metadata.getStamina();
            Assert.assertTrue( staminaDiff >= 0 && staminaDiff <= 15);

            Assert.assertEquals(pokemon0.getCp(), actualPokemon.getCp());
            Assert.assertEquals(pokemon0.getHp(), actualPokemon.getHp());
            Assert.assertEquals(pokemon0.getDust(), actualPokemon.getDust());
            Assert.assertEquals(pokemon0.getCandy(), actualPokemon.getCandy());
            Assert.assertTrue(actualPokemon.getIv() >= 0 && actualPokemon.getIv() <= 1);
        }
    }

    @Override
    @Test
    public void testShouldReturnNullWhenPokedexException() {
        Assert.assertNull(mPokedex.createPokemon(-1, 613, 64, 4000, 4));
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
        List<Pokemon> actualPokemonList = mPokedex.getPokemons();
        Assert.assertTrue(actualPokemonList.contains(pokemon0));
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
    public void testShouldThrowPokedexExceptionWhenIndexIsInvalid() {
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
