package fr.univavignon.pokedex.api;

public class PokemonFactory implements IPokemonFactory {
    IPokemonMetadataProvider pokemonMetadataProvider;

    public PokemonFactory(IPokemonMetadataProvider pokemonMetadataProvider) {
        this.pokemonMetadataProvider = pokemonMetadataProvider;
    }

    private Integer genIndividualRandomLevel(Integer specieLevel) {
        return specieLevel + (int) (Math.random() * 15);
    }

    private Double genRandomIV() {
        return Math.random();
    }

    @Override
    public Pokemon createPokemon(int index, int cp, int hp, int dust, int candy) {
        try {
            PokemonMetadata pm = pokemonMetadataProvider.getPokemonMetadata(index);
            Integer attack = genIndividualRandomLevel(pm.getAttack());
            Integer defense = genIndividualRandomLevel(pm.getDefense());
            Integer stamina = genIndividualRandomLevel(pm.getStamina());
            Double iv = genRandomIV();
            return new Pokemon(index, pm.getName(), attack, defense, stamina, cp, hp, dust, candy, iv);
        } catch (PokedexException e) {
            System.out.println("IMPOSSIBLE DE CRÉER LE POKEMON");
            System.out.println(e);
            return null;
        }
    }
}
