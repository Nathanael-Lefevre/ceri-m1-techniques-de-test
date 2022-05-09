package fr.univavignon.pokedex.api;

public class PokemonTrainerFactory implements IPokemonTrainerFactory {
    @Override
    public PokemonTrainer createTrainer(String name, Team team, IPokedexFactory pokedexFactory) {
        IPokemonMetadataProvider metadataProvider = new PokemonMetadataProvider();
        IPokemonFactory pokemonFactory = new PokemonFactory(metadataProvider);
        IPokedex pokedex = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);
        return new PokemonTrainer(name, team, pokedex);
    }
}
