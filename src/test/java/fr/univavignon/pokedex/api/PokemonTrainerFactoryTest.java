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
    @Mock
    IPokedexFactory mPokedexFactory;

    @Mock
    IPokemonTrainerFactory mPokemonTrainerFactory;

    @Mock
    IPokedex mPokedex;

    Team mTeam;

    @Override
    @Before
    public void init() throws PokedexException {
        mPokedexFactory = Mockito.mock(IPokedexFactory.class);
        mPokemonTrainerFactory = Mockito.mock(IPokemonTrainerFactory.class);
        mPokedex = Mockito.mock(IPokedex.class);

        mTeam = Team.INSTINCT;

        when(mPokemonTrainerFactory.createTrainer(anyString(), eq(mTeam), eq(mPokedexFactory))).then(
                new Answer<PokemonTrainer>() {
                    public PokemonTrainer answer(InvocationOnMock invocation) {
                        String name = invocation.getArgument(0);
                        return new PokemonTrainer(name, mTeam, mPokedex);
                    }
                });


        //String name, Team team, IPokedexFactory pokedexFactory)
    }

    @Override
    @Test
    public void testShouldReturnTrainer() {
        Assert.assertTrue(mPokemonTrainerFactory.createTrainer("name", mTeam, mPokedexFactory) instanceof PokemonTrainer);
    }

    @Override
    @Test
    public void testShouldReturnTrainerWithCorrectName() {
        String nameTest = "name";
        PokemonTrainer pokemonTrainer = mPokemonTrainerFactory.createTrainer(nameTest, mTeam, mPokedexFactory);
        Assert.assertEquals(nameTest, pokemonTrainer.getName());
    }


}
