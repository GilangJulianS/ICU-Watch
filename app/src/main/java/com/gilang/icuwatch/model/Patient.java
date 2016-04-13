package com.gilang.icuwatch.model;

/**
 * Created by Gilang on 13/04/2016.
 */
public class Patient {

	public String name, gender, indication;
	public int heartbeat, temperature, age, room;
	public int status;

	public Patient(String name, String gender, String indication, int age, int room) {
		this.name = name;
		this.gender = gender;
		this.indication = indication;
		this.heartbeat = 0;
		this.temperature = 0;
		this.age = age;
		this.room = room;
		this.status = 0;
	}
}
