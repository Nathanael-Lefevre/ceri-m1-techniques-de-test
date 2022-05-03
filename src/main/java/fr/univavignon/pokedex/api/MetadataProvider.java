package fr.univavignon.pokedex.api;

import java.util.*;

public class MetadataProvider implements IPokemonMetadataProvider {
    private static Map<Integer, PokemonMetadata> pokemonMetadatas;

    static {
        pokemonMetadatas = new HashMap<>();
        pokemonMetadatas.put(0, new PokemonMetadata(0, "Bulbizarre", 126, 126, 90));
        pokemonMetadatas.put(133, new PokemonMetadata(133, "Aquali", 186, 168, 260));
    }

    @Override
    public PokemonMetadata getPokemonMetadata(int index) throws PokedexException {
        PokemonMetadata pm =  pokemonMetadatas.get(index);
        if (pm == null) {
            throw new PokedexException("Aucune Métadonée n'existe pour l'index spécifié");
        }

        return pm;
    }
}
