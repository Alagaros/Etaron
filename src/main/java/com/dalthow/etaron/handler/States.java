/**
 * Etaron
 *
 * 
 * @Author Dalthow Game Studios 
 * @Class States.java
 * 
 **/

package com.dalthow.etaron.handler;

public enum States
{
	// A list of all the states available in the game
	
	SPLASH_STATE(0),
	MENU_STATE(1),
	GAME_STATE(2),
	EDITOR_STATE(3);

	
	// Declaration
	
	private int id;

	
	// Constructor
	
	States(int id)
	{
		this.setId(id);
	}

	
	// Getter
	
	public int getId()
	{
		return id;
	}

	
	// Setter
	
	private void setId(int id)
	{
		this.id = id;
	}
}