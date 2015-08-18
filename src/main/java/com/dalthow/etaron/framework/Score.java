package com.dalthow.etaron.framework;

/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios
 * @class Score.java
 * 
 **/
public class Score
{
	// Declaration of all the fields in the database.

	private int coins, level, userId;
	private float duration;
	private String date;


	// Constructor that fills in the declared variables.

	public Score(int coins, int level, int userId, float duration, String date)
	{
		this.coins = coins;
		this.level = level;
		this.userId = userId;
		this.duration = duration;
		this.date = date;
	}


	// Getters.

	public int getCoins()
	{
		return coins;
	}

	public float getDuration()
	{
		return duration;
	}

	public String getDate()
	{
		return date;
	}

	public int getLevel()
	{
		return level;
	}

	public int getUserId()
	{
		return userId;
	}


	// Setters.

	public void setCoins(int coins)
	{
		this.coins = coins;
	}

	public void setDuration(float duration)
	{
		this.duration = duration;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
}
