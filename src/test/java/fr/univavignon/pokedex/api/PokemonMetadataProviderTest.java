package fr.univavignon.pokedex.api;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PokemonMetadataProviderTest implements IPokemonMetadataProviderTest {

    PokemonMetadata metadata0;
    PokemonMetadata metadata1;

    @Mock
    private IPokemonMetadataProvider mPokemonMetadataProvider;

    @Before
    public void init() throws PokedexException {
        metadata0 = new PokemonMetadata(0, "Bulbizarre", 126, 126, 90);
        metadata1 = new PokemonMetadata(133, "Aquali", 186, 168, 260);

        this.mPokemonMetadataProvider = Mockito.mock(IPokemonMetadataProvider.class);

        when(mPokemonMetadataProvider.getPokemonMetadata(any(Integer.class))).then(
                new Answer<>() {
                    public PokemonMetadata answer(InvocationOnMock invocation) throws PokedexException {
                        Integer index = invocation.getArgument(0);

                        if (index == 0) {
                            return metadata0;
                        } else if (index == 133) {
                            return metadata1;
                        }
                        throw new PokedexException("Aucune Métadonée n'existe pour l'index spécifié");
                    }
                });
    }

    @Override
    @Test
    public void testShouldReturnPokemonMetadataCorrespondingToRequestedIndex() throws PokedexException {
        Assert.assertEquals(0, this.mPokemonMetadataProvider.getPokemonMetadata(0).getIndex());
        Assert.assertEquals(133, this.mPokemonMetadataProvider.getPokemonMetadata(133).getIndex());
    }

    @Override
    @Test
    public void testShouldThrowPokedexExceptionWhenIncorrectIndexInRequest() throws PokedexException {
        Assert.assertThrows(PokedexException.class, () -> mPokemonMetadataProvider.getPokemonMetadata(-1));
        Assert.assertThrows(PokedexException.class, () -> mPokemonMetadataProvider.getPokemonMetadata(2));
    }
}
