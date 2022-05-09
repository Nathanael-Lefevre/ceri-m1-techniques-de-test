package fr.univavignon.pokedex.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pokedex implements IPokedex {
    /** Pokemon List of the Pokedex. **/
    private List<Pokemon> pokemonList;

    /** MetadataProvider allowing Pokemon metadata. **/
    private IPokemonMetadataProvider metadataProvider;

    /** PokemonFactory allowing to create Pokemons. **/
    private IPokemonFactory pokemonFactory;

    /**
     * @param pMetadataProvider MetadataProvider
     * @param pPokemonFactory PokemonFactory
     */
    public Pokedex(final IPokemonMetadataProvider pMetadataProvider,
                   final IPokemonFactory pPokemonFactory) {
        this.pokemonList = new ArrayList<>();
        this.metadataProvider = pMetadataProvider;
        this.pokemonFactory = pPokemonFactory;
    }

    /**
     * @return number of Pokemons in the Pokedex.
     */
    @Override
    public int size() {
        return pokemonList.size();
    }

    /**
     * @param pokemon Pokemon to add to this pokedex.
     * @return index of the Pokemon in the Pokedex.
     */
    @Override
    public int addPokemon(final Pokemon pokemon) {
        pokemonList.add(pokemon);
        return pokemonList.indexOf(pokemon);
    }

    /**
     * @param id Unique pokedex relative identifier.
     * @return Pokemon at the given id.
     * @throws PokedexException when index incorrect
     */
    @Override
    public Pokemon getPokemon(final int id) throws PokedexException {
        if (id < 0) {
            throw new PokedexException(
                    "L'index du Pokemon dans le pokedex "
                            + "doit être supérieur à -1");
        } else if (id >= size()) {
            throw new PokedexException(
                    "Le Pokedex ne contient pas autant de pokemon !");
        }
        return pokemonList.get(id);
    }

    /**
     * @return an unmodifiable copy of the Pokemon List in the Pokedex.
     */
    @Override
    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemonList);
    }

    /**
     * @param order Comparator instance used for sorting the created view.
     * @return an unmodifiable sorted List of Pokemon.
     */
    @Override
    public List<Pokemon> getPokemons(final Comparator<Pokemon> order) {
        List<Pokemon> sortedCPList = new ArrayList<>(pokemonList);
        sortedCPList.sort(order);
        return Collections.unmodifiableList(sortedCPList);
    }

    /**
     * @param index Pokemon index.
     * @param cp    Pokemon CP.
     * @param hp    Pokemon HP.
     * @param dust  Required dust for upgrading pokemon.
     * @param candy Required candy for upgrading pokemon.
     * @return a Pokemon
     */
    @Override
    public Pokemon createPokemon(final int index,
                                 final int cp,
                                 final int hp,
                                 final int dust,
                                 final int candy) {
        return pokemonFactory.createPokemon(index, cp, hp, dust, candy);
    }

    /**
     * @param index Index of the pokemon to retrieve metadata for.
     * @return metadata for the Pokemon at the given index.
     * @throws PokedexException when index incorrect
     */
    @Override
    public PokemonMetadata getPokemonMetadata(final int index)
            throws PokedexException {
        if (index < 0) {
            throw new PokedexException(
                    "L'index du Pokemon dans le pokedex"
                            + "doit être supérieur à -1");
        } else if (index >= size()) {
            throw new PokedexException(
                    "Le Pokedex ne contient pas autant de pokemon !");
        }
        int pokemonIndex = pokemonList.get(index).getIndex();
        return metadataProvider.getPokemonMetadata(pokemonIndex);
    }
}
