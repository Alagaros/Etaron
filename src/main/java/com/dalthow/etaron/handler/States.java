package com.dalthow.etaron.handler;

/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios 
 * @class States.java
 * 
 **/

public enum States
{
	// A list of all the states available in the game.
	
	SPLASH_STATE(0),
	MENU_STATE(1),
	GAME_STATE(2),
	EDITOR_STATE(3);

	
	// Declaration of the state id.
	
	private int id;

	
	// Constructor that sets the declared variables.
	
	States(int id)
	{
		this.setId(id);
	}

	
	// Getter.
	
	public int getId()
	{
		return id;
	}

	
	// Setter.
	
	private void setId(int id)
	{
		this.id = id;
	}
}