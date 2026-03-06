package com.ski.academic_system.model;

public enum Role {
	STUDENT,
	TEACHER,
	ADMIN;
	
	public String authority() {
		return "ROLE_" + this.name().toUpperCase();
	}
}
