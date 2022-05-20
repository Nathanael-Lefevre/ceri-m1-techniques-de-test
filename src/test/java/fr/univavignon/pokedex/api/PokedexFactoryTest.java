package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;

public class PokedexFactoryTest implements IPokedexFactoryTest {
    @Mock private IPokedexFactory mPokedexFactory;
    @Mock private IPokemonMetadataProvider mMetadataProvider;
    @Mock private IPokemonFactory mPokemonFactory;
    @Mock private IPokedex mPokedex;

    @Before
    public void initNonMock() {
        mPokedexFactory = new PokedexFactory();
        mMetadataProvider = new PokemonMetadataProvider();
        //mPokemonFactory = new PokemonFactory(mMetadataProvider);
        mPokemonFactory = new RocketPokemonFactory();
    }

    @Override
    //@Before
    public void init() throws PokedexException {
        mPokedexFactory = Mockito.mock(IPokedexFactory.class);
        mMetadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        mPokemonFactory = Mockito.mock(IPokemonFactory.class);
        mPokedex = Mockito.mock(IPokedex.class);

        when(mPokedexFactory.createPokedex(mMetadataProvider, mPokemonFactory)).then(
                (Answer<IPokedex>) invocation -> mPokedex);
    }

    @Override
    @Test
    public void testShouldReturnPokedex() {
        Assert.assertNotNull(mPokedexFactory.createPokedex(mMetadataProvider, mPokemonFactory));
    }
}
