package de.tu_darmstadt.gdi1.pacman.exceptions;

public class NoItemsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoItemsException()
	{
		super("Es gibt keine sammelbaren Items!");
	}
}
