package com.ch11.vo;

public class Exam {
	
	private String subject;
    private int score;
    
    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return "Exam {" + "subject=" + subject + ", score=" + score + '}';
    }
}
