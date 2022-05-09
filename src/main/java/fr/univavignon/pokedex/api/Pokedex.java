package fr.univavignon.pokedex.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pokedex implements IPokedex {
    List<Pokemon> pokemonList = new ArrayList<>();
    IPokemonMetadataProvider metadataProvider;
    IPokemonFactory pokemonFactory;

    public Pokedex(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory) {
        this.metadataProvider = metadataProvider;
        this.pokemonFactory = pokemonFactory;
    }

    @Override
    public int size() {
        return pokemonList.size();
    }

    @Override
    public int addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
        return pokemonList.indexOf(pokemon);
    }

    @Override
    public Pokemon getPokemon(int id) throws PokedexException {
        if (id < 0) {
            throw new PokedexException("L'index du Pokemon dans le pokedex doit être supérieur à -1");
        } else if (id >= size()) {
            throw new PokedexException("Le Pokedex ne contient pas autant de pokemon !");
        }
        return pokemonList.get(id);
    }

    @Override
    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemonList);
    }

    @Override
    public List<Pokemon> getPokemons(Comparator<Pokemon> order) {
        List<Pokemon> sortedCPList = new ArrayList<>(pokemonList);
        sortedCPList.sort(order);
        return Collections.unmodifiableList(sortedCPList);
    }

    @Override
    public Pokemon createPokemon(int index, int cp, int hp, int dust, int candy) {
        return pokemonFactory.createPokemon(index, cp, hp, dust, candy);
    }

    @Override
    public PokemonMetadata getPokemonMetadata(int index) throws PokedexException {
        if (index < 0) {
            throw new PokedexException("L'index du Pokemon dans le pokedex doit être supérieur à -1");
        } else if (index >= size()) {
            throw new PokedexException("Le Pokedex ne contient pas autant de pokemon !");
        }
        return metadataProvider.getPokemonMetadata(pokemonList.get(index).getIndex());
    }
}
