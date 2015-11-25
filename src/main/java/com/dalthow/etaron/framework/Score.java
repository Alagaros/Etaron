package com.dalthow.etaron.framework;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Score
{
	// Declaration of all the fields in the database.
	private int coins, level;
	private double duration;


	// Constructor that fills in the declared variables.
	public Score(int coins, int level, double duration)
	{
		this.coins = coins;
		this.level = level;
		this.duration = duration;
	}


	// Getters.
	public int getCoins()
	{
		return coins;
	}
	public double getDuration()
	{
		return duration;
	}
	public int getLevel()
	{
		return level;
	}

	// Setters.
	public void setCoins(int coins)
	{
		this.coins = coins;
	}
	public void setDuration(double duration)
	{
		this.duration = duration;
	}
}
