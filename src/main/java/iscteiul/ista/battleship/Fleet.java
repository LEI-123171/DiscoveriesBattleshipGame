package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the fleet of a player in the Discoveries Battleship Game.
 * <p>
 * A fleet holds a list of ships and ensures that every ship added lies inside
 * the board and does not collide with (or touch) the ships already placed.
 * It also provides queries by category and by position, and methods to print
 * the state of the fleet.
 *
 * @see IFleet
 * @see IShip
 */
public class Fleet implements IFleet {

    /**
     * Prints all the given ships.
     *
     * @param ships the list of ships to print
     */
    static void printShips(List<IShip> ships) {
        for (IShip ship : ships)
            System.out.println(ship);
    }

    // -----------------------------------------------------

    /** The ships that belong to this fleet. */
    private List<IShip> ships;

    /**
     * Creates an empty fleet.
     */
    public Fleet() {
        ships = new ArrayList<>();
    }

    /**
     * Returns the ships currently in the fleet.
     *
     * @return the list of ships of this fleet
     */
    @Override
    public List<IShip> getShips() {
        return ships;
    }

    /**
     * Adds a ship to the fleet, if the fleet is not full, the ship lies
     * entirely inside the board and does not collide with any other ship.
     *
     * @param s the ship to add
     * @return {@code true} if the ship was added, {@code false} otherwise
     */

    /*Note: ships.size() <= FLEET_SIZE allows one more ship than the limit?*/
    @Override
    public boolean addShip(IShip s) {
        boolean result = false;
        if ((ships.size() <= FLEET_SIZE) && (isInsideBoard(s)) && (!colisionRisk(s))) {
            ships.add(s);
            result = true;
        }
        return result;
    }

    /**
     * Returns the ships of the fleet that belong to a given category.
     *
     * @param category the category of interest (e.g. "Nau", "Caravela")
     * @return the list of ships of that category (empty if there are none)
     */
    @Override
    public List<IShip> getShipsLike(String category) {
        List<IShip> shipsLike = new ArrayList<>();
        for (IShip s : ships)
            if (s.getCategory().equals(category))
                shipsLike.add(s);

        return shipsLike;
    }

    /**
     * Returns the ships of the fleet that are still floating, that is, that
     * have not yet been completely hit.
     *
     * @return the list of ships still floating
     */
    @Override
    public List<IShip> getFloatingShips() {
        List<IShip> floatingShips = new ArrayList<>();
        for (IShip s : ships)
            if (s.stillFloating())
                floatingShips.add(s);

        return floatingShips;
    }

    /**
     * Returns the ship that occupies a given position of the board.
     *
     * @param pos the position to check
     * @return the ship occupying {@code pos}, or {@code null} if there is none
     */
    @Override
    public IShip shipAt(IPosition pos) {
        for (int i = 0; i < ships.size(); i++)
            if (ships.get(i).occupies(pos))
                return ships.get(i);
        return null;
    }

    /**
     * Checks whether a ship lies entirely inside the board.
     *
     * @param s the ship to check
     * @return {@code true} if all positions of the ship are inside the board
     */
    private boolean isInsideBoard(IShip s) {
        return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= BOARD_SIZE - 1 && s.getTopMostPos() >= 0
                && s.getBottomMostPos() <= BOARD_SIZE - 1);
    }

    /**
     * Checks whether a ship is too close to (or overlaps) any ship already in
     * the fleet.
     *
     * @param s the ship to check
     * @return {@code true} if there is a collision risk, {@code false} otherwise
     */
    private boolean colisionRisk(IShip s) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).tooCloseTo(s))
                return true;
        }
        return false;
    }

    /**
     * Shows the state of the fleet: all ships, the ships still floating and
     * the ships of each category.
     */
    public void printStatus() {
        printAllShips();
        printFloatingShips();
        printShipsByCategory("Galeao");
        printShipsByCategory("Fragata");
        printShipsByCategory("Nau");
        printShipsByCategory("Caravela");
        printShipsByCategory("Barca");
    }

    /**
     * Prints all the ships of the fleet belonging to a particular category.
     *
     * @param category the category of ships of interest, must not be {@code null}
     */
    public void printhipsByCategory(String category) {
        assert category != null;

        printShips(getShipsLike(category));
    }

    /**
     * Prints all the ships of the fleet that are still floating (not yet sunk).
     */
    public void printFloatingShips() {
        printShips(getFloatingShips());
    }

    /**
     * Prints all the ships of the fleet.
     */
    void printAllShips() {
        printShips(ships);
    }

}