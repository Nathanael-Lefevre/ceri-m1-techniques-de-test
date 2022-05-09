package fr.univavignon.pokedex.api;

public class PokedexFactory implements IPokedexFactory {
    /**
     * @param metadataProvider Metadata provider the created pokedex
     *                         will use.
     * @param pokemonFactory   Pokemon factory the created pokedex will use.
     * @return a Pokedex
     */
    @Override
    public IPokedex createPokedex(
            final IPokemonMetadataProvider metadataProvider,
            final IPokemonFactory pokemonFactory) {
        return new Pokedex(metadataProvider, pokemonFactory);
    }
}
