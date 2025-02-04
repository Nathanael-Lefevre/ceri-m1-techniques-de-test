package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PokemonMetadataProviderTest implements IPokemonMetadataProviderTest {

    private PokemonMetadata metadata0;
    private PokemonMetadata metadata1;

    @Mock
    private IPokemonMetadataProvider mPokemonMetadataProvider;

    @Before
    public void initNonMock() {
        mPokemonMetadataProvider = new PokemonMetadataProvider();
    }

    //@Before
    public void init() throws PokedexException {
        metadata0 = new PokemonMetadata(0, "Bulbizarre", 126, 126, 90);
        metadata1 = new PokemonMetadata(133, "Aquali", 186, 168, 260);

        this.mPokemonMetadataProvider = Mockito.mock(IPokemonMetadataProvider.class);

        when(mPokemonMetadataProvider.getPokemonMetadata(any(Integer.class))).then(
                invocation -> {
                    Integer index = invocation.getArgument(0);

                    if (index == 0) {
                        return metadata0;
                    } else if (index == 133) {
                        return metadata1;
                    } else if (index > 0 && index < 151) {
                        return metadata1;  // Sinon on retourne une métadata valide
                    }
                    throw new PokedexException("Aucune Métadonée n'existe pour l'index spécifié");
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
    public void testShouldThrowPokedexExceptionWhenIncorrectIndexInRequest() {
        Assert.assertThrows(PokedexException.class, () -> mPokemonMetadataProvider.getPokemonMetadata(-1));
        Assert.assertThrows(PokedexException.class, () -> mPokemonMetadataProvider.getPokemonMetadata(151));
    }
}
