package fr.univavignon.pokedex.api;

import java.util.HashMap;
import java.util.Map;


public class PokemonMetadataProvider implements IPokemonMetadataProvider {
    /**
     * Map of Int -> PokemonMetadata.
     */
    private static Map<Integer, PokemonMetadata> pokemonMetadatas;

    /** 0. */
    public static final int INT_0 = 0;

    /** 90. */
    public static final int INT_90 = 90;

    /** 126. */
    public static final int INT_126 = 126;

    /** 133. */
    public static final int INT_133 = 133;

    /** 168. */
    public static final int INT_168 = 168;

    /** 186. */
    public static final int INT_186 = 186;

    /** 260. */
    public static final int INT_260 = 260;


    static {
        pokemonMetadatas = new HashMap<>();
        pokemonMetadatas.put(INT_0,
                new PokemonMetadata(INT_0,
                            "Bulbizarre",
                                    INT_126,
                                    INT_126,
                                    INT_90));
        pokemonMetadatas.put(INT_133,
                new PokemonMetadata(INT_133,
                            "Aquali",
                                    INT_186,
                                    INT_168,
                                    INT_260));
    }

    /**
     * @param index Index of the pokemon to retrieve metadata for.
     * @return PokemonMetadata
     * @throws PokedexException when index incorrect
     */
    @Override
    public PokemonMetadata getPokemonMetadata(final int index)
            throws PokedexException {
        PokemonMetadata pm =  pokemonMetadatas.get(index);
        if (pm == null) {
            throw new PokedexException(
                    "Aucune Métadonée n'existe pour l'index spécifié");
        }

        return pm;
    }
}
