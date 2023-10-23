package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

    private Position root;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
	public Optional<Position> hire(Name person, String title) {
		return hirePosition(root, person, title);
	}

	/**
	 * Recursively searches for the position with the given title and is not filled.
	 * If it finds such a position, it creates a new employee and assigns them to
	 * that position
	 * 
	 * @param currentPosition
	 * @param person
	 * @param title
	 * 
	 * @return An Optional containing the newly filled position, or empty if no
	 *         position has that title.
	 */
	private Optional<Position> hirePosition(Position currentPosition, Name person, String title) {
		if (currentPosition.getTitle().equals(title) && !currentPosition.isFilled()) {
			Employee employee = new Employee(generateEmployeeId(), person);
			currentPosition.setEmployee(Optional.of(employee));
			return Optional.of(currentPosition);
		} else {
			for (Position position : currentPosition.getDirectReports()) {
				Optional<Position> hiredPosition = hirePosition(position, person, title);
				if (hiredPosition.isPresent()) {
					return hiredPosition;
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Generates a random employee ID
	 * 
	 * @return A randomly generated employee ID.
	 */
	private int generateEmployeeId() {
		return (int) (Math.random() * 1000);
	}

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
