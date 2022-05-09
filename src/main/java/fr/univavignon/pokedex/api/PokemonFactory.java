package fr.univavignon.pokedex.api;

public class PokemonFactory implements IPokemonFactory {
    /**
     * Valeur maximum pouvant être ajoutée
     * au calcul du level d'une statistique.
     */
    public static final int MAX_ADD_LEVEL = 15;
    /**
     * IPokemonMetadataProvider allowing to retreive metadata.
     */
    private IPokemonMetadataProvider pokemonMetadataProvider;

    /**
     * @param pPokemonMetadataProvider for metadatas provision
     */
    public PokemonFactory(
            final IPokemonMetadataProvider pPokemonMetadataProvider) {
        this.pokemonMetadataProvider = pPokemonMetadataProvider;
    }

    private Integer genIndividualRandomLevel(final Integer specieLevel) {
        return specieLevel + (int) (Math.random() * MAX_ADD_LEVEL);
    }

    private Double genRandomIV() {
        return Math.random();
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
        try {
            PokemonMetadata pm = pokemonMetadataProvider.
                    getPokemonMetadata(index);
            Integer attack = genIndividualRandomLevel(pm.getAttack());
            Integer defense = genIndividualRandomLevel(pm.getDefense());
            Integer stamina = genIndividualRandomLevel(pm.getStamina());
            Double iv = genRandomIV();
            return new Pokemon(
                    index,
                    pm.getName(),
                    attack,
                    defense,
                    stamina,
                    cp,
                    hp,
                    dust,
                    candy,
                    iv);
        } catch (PokedexException e) {
            System.out.println("IMPOSSIBLE DE CRÉER LE POKEMON");
            e.printStackTrace();
            return null;
        }
    }
}
