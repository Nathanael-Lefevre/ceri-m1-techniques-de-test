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
    @Mock IPokedexFactory mPokedexFactory;
    @Mock IPokemonMetadataProvider mMetadataProvider;
    @Mock IPokemonFactory mPokemonFactory;
    @Mock IPokedex mPokedex;

    @Override
    @Before
    public void init() throws PokedexException {
        mPokedexFactory = Mockito.mock(IPokedexFactory.class);
        mMetadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        mPokemonFactory = Mockito.mock(IPokemonFactory.class);
        mPokedex = Mockito.mock(IPokedex.class);

        when(mPokedexFactory.createPokedex(mMetadataProvider, mPokemonFactory)).then(
                new Answer<IPokedex>() {
                    public IPokedex answer(InvocationOnMock invocation) {
                        return mPokedex;
                    }
                });
    }

    @Override
    @Test
    public void testShouldReturnPokedex() {
        Assert.assertTrue(mPokedexFactory.createPokedex(mMetadataProvider, mPokemonFactory) instanceof IPokedex);
    }
}
