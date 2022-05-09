package fr.univavignon.pokedex.api;

/**
 * Trainer POJO.
 *
 * @author fv
 */
public class PokemonTrainer {

	/** Trainer name. **/
	private final String name;

	/** Trainer team. **/
	private final Team team;

	/** Trainer pokedex. **/
	private final IPokedex pokedex;

	/**
	 * Default constructor.
	 *
	 * @param pName Trainer name.
	 * @param pTeam Trainer team.
	 * @param pPokedex Trainer pokedex.
	 */
	public PokemonTrainer(final String pName,
						  final Team pTeam,
						  final IPokedex pPokedex) {
		this.name = pName;
		this.team = pTeam;
		this.pokedex = pPokedex;
	}

	/** Name getter.
	 *
	 * @return PokemonTrainer name.
	 * **/
	public String getName() {
		return name;
	}

	/** Team getter.
	 *
	 * @return PokemonTrainer team.
	 * **/
	public Team getTeam() {
		return team;
	}

	/** Pokedex getter.
	 *
	 * @return PokemonTrainer Pokedex.
	 * **/
	public IPokedex getPokedex() {
		return pokedex;
	}

}
