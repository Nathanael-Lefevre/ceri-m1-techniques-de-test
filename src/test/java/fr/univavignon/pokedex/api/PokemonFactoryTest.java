package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;

public class PokemonFactoryTest implements IPokemonFactoryTest{
    @Mock
    IPokemonFactory mPokemonFactory;

    PokemonMetadata metadata0;
    Pokemon pokemon0;

    @Override
    @Before
    public void init() throws PokedexException {
        mPokemonFactory = Mockito.mock(IPokemonFactory.class);

        metadata0 = new PokemonMetadata(0, "Bulbizarre", 126, 126, 90);
        pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);

        when(mPokemonFactory.createPokemon(0, 613, 64, 4000, 4)).then(
                new Answer<Pokemon>() {
                    public Pokemon answer(InvocationOnMock invocation) {
                        String name = metadata0.getName();
                        int attack = metadata0.getAttack();
                        int defense = metadata0.getDefense();
                        int stamina = metadata0.getStamina();
                        return new Pokemon(0, name, attack, defense, stamina, 613, 64, 4000, 4, .56);
                    }
                });
    }

    @Override
    @Test
    public void testShouldReturnPokemon() {
        Assert.assertTrue(mPokemonFactory.createPokemon(0, 613, 64, 4000, 4) instanceof Pokemon);
    }

    @Override
    @Test
    public void testShouldReturnPokemonWithCorrectStats() {
        pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);

        Pokemon actualPokemon = mPokemonFactory.createPokemon(0, 613, 64, 4000, 4);
        Assert.assertEquals(pokemon0.getIndex(), actualPokemon.getIndex());
        Assert.assertEquals(pokemon0.getName(), actualPokemon.getName());
        Assert.assertEquals(pokemon0.getAttack(), actualPokemon.getAttack());
        Assert.assertEquals(pokemon0.getDefense(), actualPokemon.getDefense());
        Assert.assertEquals(pokemon0.getStamina(), actualPokemon.getStamina());
        Assert.assertEquals(pokemon0.getCp(), actualPokemon.getCp());
        Assert.assertEquals(pokemon0.getHp(), actualPokemon.getHp());
        Assert.assertEquals(pokemon0.getDust(), actualPokemon.getDust());
        Assert.assertEquals(pokemon0.getCandy(), actualPokemon.getCandy());
        Assert.assertEquals(pokemon0.getIv(), actualPokemon.getIv(), .0);
    }
}
