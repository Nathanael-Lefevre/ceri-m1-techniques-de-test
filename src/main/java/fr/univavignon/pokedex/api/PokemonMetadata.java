package fr.univavignon.pokedex.api;

/**
 * Pokemon metadata POJO.
 *
 * @author fv
 */
public class PokemonMetadata {

	/** Pokemon index. **/
	private final int index;

	/** Pokemon name. **/
	private final String name;

	/** Pokemon attack level. **/
	private final int attack;

	/** Pokemon defense level. **/
	private final int defense;

	/** Pokemon stamina level. **/
	private final int stamina;

	/**
	 * Default constructor.
	 *
	 * @param pIndex Pokemon index.
	 * @param pName Pokemon name.
	 * @param pAttack Attack level.
	 * @param pDefense Defense level.
	 * @param pStamina Stamina level.
	 */
	public PokemonMetadata(final int pIndex,
						   final String pName,
						   final int pAttack,
						   final int pDefense,
						   final int pStamina) {
		this.index = pIndex;
		this.name = pName;
		this.attack = pAttack;
		this.defense = pDefense;
		this.stamina = pStamina;
	}

	/** Index getter.
	 *
	 * @return Pokemon index.
	 * **/
	public int getIndex() {
		return index;
	}

	/** Name getter.
	 *
	 * @return Pokemon name.
	 * **/
	public String getName() {
		return name;
	}

	/** Attack level getter.
	 *
	 * @return Pokemon attack.
	 * **/
	public int getAttack() {
		return attack;
	}

	/** Defense level getter.
	 *
	 * @return Pokemon defense.
	 * **/
	public int getDefense() {
		return defense;
	}

	/** Stamina level getter.
	 *
	 * @return Pokemon stamina.
	 * **/
	public int getStamina() {
		return stamina;
	}

}
