package fr.univavignon.pokedex.api;

public class PokemonTrainerFactory implements IPokemonTrainerFactory {
    /**
     * @param name           Name of the created trainer.
     * @param team           Team of the created trainer.
     * @param pokedexFactory Factory to use for
     *                       creating associated pokedex instance.
     * @return a Pokemon Trainer
     */
    @Override
    public PokemonTrainer createTrainer(final String name,
                                        final Team team,
                                        final IPokedexFactory pokedexFactory) {
        IPokemonMetadataProvider metadataProvider =
                new PokemonMetadataProvider();
        IPokemonFactory pokemonFactory = new PokemonFactory(metadataProvider);
        IPokedex pokedex = pokedexFactory.
                createPokedex(metadataProvider, pokemonFactory);
        return new PokemonTrainer(name, team, pokedex);
    }
}
