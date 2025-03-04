package com.ch11.vo;

public class Person {
	
	private String name;
    private Exam[] exam;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Exam[] getExam() {
		return exam;
	}

	public void setExam(Exam[] exam) {
		this.exam = exam;
	}

	@Override
    public String toString() {
        return "Person{" + "name=" + name + ", exam=" + exam + '}';
    }
}
