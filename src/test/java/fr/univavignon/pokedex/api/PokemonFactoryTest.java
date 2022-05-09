package fr.univavignon.pokedex.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

public class PokemonFactoryTest implements IPokemonFactoryTest{
    @Mock
    private IPokemonFactory mPokemonFactory;

    private PokemonMetadata metadata0;
    private Pokemon pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);

    @Before
    public void initNonMock() {
        mPokemonFactory = new PokemonFactory(new PokemonMetadataProvider());
    }

    @Override
    //@Before
    public void init() throws PokedexException {
        mPokemonFactory = Mockito.mock(IPokemonFactory.class);

        metadata0 = new PokemonMetadata(0, "Bulbizarre", 126, 126, 90);
        //pokemon0 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, .56);

        when(mPokemonFactory.createPokemon(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class))).then(
                (Answer<Pokemon>) invocation -> {
                    Integer index = invocation.getArgument(0);
                    if (index == -1) {
                        return null;
                    }
                    String name = metadata0.getName();
                    int attack = metadata0.getAttack();
                    int defense = metadata0.getDefense();
                    int stamina = metadata0.getStamina();
                    return new Pokemon(0, name, attack, defense, stamina, 613, 64, 4000, 4, .56);
                }).then((Answer<Pokemon>) invocation -> {
                    Integer index = invocation.getArgument(0);
                    if (index == -1) {
                        return null;
                    }
                    String name = metadata0.getName();
                    int attack = metadata0.getAttack() + 15;
                    int defense = metadata0.getDefense() + 15;
                    int stamina = metadata0.getStamina() + 15;
                    return new Pokemon(0, name, attack, defense, stamina, 613, 64, 4000, 4, 1);
                });
    }

    @Override
    @Test
    public void testShouldReturnPokemon() {
        Assert.assertNotNull(mPokemonFactory.createPokemon(0, 613, 64, 4000, 4));
    }

    @Override
    @Test
    public void testShouldReturnPokemonWithCorrectStats() {
        Pokemon actualPokemon;
        for (int i = 0; i < 3; i++) {
            actualPokemon = mPokemonFactory.createPokemon(0, 613, 64, 4000, 4);

            Assert.assertEquals(pokemon0.getIndex(), actualPokemon.getIndex());
            Assert.assertEquals(pokemon0.getName(), actualPokemon.getName());
            Assert.assertTrue(actualPokemon.getAttack() - pokemon0.getAttack() <= 15);
            Assert.assertTrue(actualPokemon.getDefense() - pokemon0.getDefense() <= 15);
            Assert.assertTrue(actualPokemon.getStamina() - pokemon0.getStamina() <= 15);
            Assert.assertEquals(pokemon0.getCp(), actualPokemon.getCp());
            Assert.assertEquals(pokemon0.getHp(), actualPokemon.getHp());
            Assert.assertEquals(pokemon0.getDust(), actualPokemon.getDust());
            Assert.assertEquals(pokemon0.getCandy(), actualPokemon.getCandy());
            Assert.assertTrue(actualPokemon.getIv() >= 0 && actualPokemon.getIv() <= 1);
        }
    }

    @Override
    @Test
    public void testShouldReturnNullWhenPokedexException() {
        Assert.assertNull(mPokemonFactory.createPokemon(-1, 613, 64, 4000, 4));
    }
}
