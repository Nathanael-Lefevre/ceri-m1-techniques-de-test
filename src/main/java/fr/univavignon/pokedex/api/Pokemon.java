package fr.univavignon.pokedex.api;

/**
 * Pokemon POJO.
 *
 * @author fv
 */
public final class Pokemon extends PokemonMetadata {

	/** Combat Point of the pokémon. **/
	private final int cp;

	/** HP of the pokemon. **/
	private final int hp;

	/** Required dust for upgrading this pokemon. **/
	private final int dust;

	/** Required candy for upgrading this pokemon. **/
	private final int candy;

	/** IV perfection percentage. **/
	private final double iv;

	/**
	 * Default constructor.
	 *
	 * @param index Pokemon index.
	 * @param name Pokemon name.
	 * @param attack Attack level.
	 * @param defense Defense level.
	 * @param stamina Stamina level.
	 * @param pCp Pokemon cp.
	 * @param pHp Pokemon hp.
	 * @param pDust Required dust for upgrading this pokemon.
	 * @param pCandy Required candy for upgrading this pokemon.
	 * @param pIv IV perfection percentage.
	 */
	public Pokemon(
			final int index,
			final String name,
			final int attack,
			final int defense,
			final int stamina,
			final int pCp,
			final int pHp,
			final int pDust,
			final int pCandy,
			final double pIv) {
		super(index, name, attack, defense, stamina);
		this.cp = pCp;
		this.hp = pHp;
		this.dust = pDust;
		this.candy = pCandy;
		this.iv = pIv;
	}

	/** Combat Point getter getter.
	 *
	 * @return Pokemon cp.
	 * **/
	public int getCp() {
		return cp;
	}

	/** HP getter.
	 *
	 * @return Pokemon hp.
	 * **/
	public int getHp() {
		return hp;
	}

	/** Dust getter.
	 *
	 * @return Pokemon dust.
	 * **/
	public int getDust() {
		return dust;
	}

	/** Candy getter.
	 *
	 * @return Pokemon candy.
	 * **/
	public int getCandy() {
		return candy;
	}

	/** IV getter.
	 *
	 * @return Pokemon iv.
	 * **/
	public double getIv() {
		return iv;
	}

}
