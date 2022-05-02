package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PokemonTrainerFactoryTest implements IPokemonTrainerFactoryTest{
    @Mock
    Team mTeam;

    @Mock
    IPokedexFactory mPokedexFactory;

    @Mock
    IPokemonTrainerFactory mPokemonTrainerFactory;

    @Mock
    PokemonTrainer mPokemonTrainer;

    @Override
    @Before
    public void init() throws PokedexException {
        mTeam = Mockito.mock(Team.class);
        mPokedexFactory = Mockito.mock(IPokedexFactory.class);
        mPokemonTrainerFactory = Mockito.mock(IPokemonTrainerFactory.class);
        mPokemonTrainer = Mockito.mock(PokemonTrainer.class);

        when(mPokemonTrainerFactory.createTrainer(any(String.class), mTeam, mPokedexFactory)).then(
                new Answer<PokemonTrainer>() {
                    public PokemonTrainer answer(InvocationOnMock invocation) {
                        return mPokemonTrainer;
                    }
                });


        //String name, Team team, IPokedexFactory pokedexFactory)
    }

    @Override
    //@Test
    public void testShouldReturnTrainer() {
        Assert.assertTrue(mPokemonTrainerFactory.createTrainer("name", mTeam, mPokedexFactory) instanceof PokemonTrainer);
    }
}
