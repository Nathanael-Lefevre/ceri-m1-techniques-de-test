package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PokemonTrainerFactoryTest implements IPokemonTrainerFactoryTest{
    @Mock private IPokedexFactory mPokedexFactory;
    @Mock private IPokemonTrainerFactory mPokemonTrainerFactory;
    @Mock private IPokedex mPokedex;

    private Team mTeam;

    @Before
    public void initNonMock() {
        mPokemonTrainerFactory = new PokemonTrainerFactory();
        mPokedexFactory = new PokedexFactory();

        mTeam = Team.INSTINCT;
    }

    @Override
    //@Before
    public void init() throws PokedexException {
        mPokedexFactory = Mockito.mock(IPokedexFactory.class);
        mPokemonTrainerFactory = Mockito.mock(IPokemonTrainerFactory.class);
        mPokedex = Mockito.mock(IPokedex.class);

        mTeam = Team.INSTINCT;

        when(mPokemonTrainerFactory.createTrainer(anyString(), eq(mTeam), eq(mPokedexFactory))).then(
                (Answer<PokemonTrainer>) invocation -> {
                    String name = invocation.getArgument(0);
                    return new PokemonTrainer(name, mTeam, mPokedex);
                });
    }

    @Override
    @Test
    public void testShouldReturnTrainer() {
        Assert.assertNotNull(mPokemonTrainerFactory.createTrainer("name", mTeam, mPokedexFactory));
    }

    @Override
    @Test
    public void testShouldReturnTrainerWithCorrectName() {
        String nameTest = "name";
        PokemonTrainer pokemonTrainer = mPokemonTrainerFactory.createTrainer(nameTest, mTeam, mPokedexFactory);
        Assert.assertEquals(nameTest, pokemonTrainer.getName());
    }

    @Override
    @Test
    public void testShouldReturnTrainerWithCorrectTeam() {
        PokemonTrainer pokemonTrainer = mPokemonTrainerFactory.createTrainer("name", mTeam, mPokedexFactory);
        Assert.assertEquals(mTeam, pokemonTrainer.getTeam());
    }

    @Override
    @Test
    public void testShouldReturnPokedex() {
        PokemonTrainer pokemonTrainer = mPokemonTrainerFactory.createTrainer("name", mTeam, mPokedexFactory);
        Assert.assertNotNull(pokemonTrainer.getPokedex());
    }


}
