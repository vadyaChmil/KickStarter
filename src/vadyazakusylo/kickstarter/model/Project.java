package vadyazakusylo.kickstarter.model;

import java.util.Map;
import java.util.Set;

public class Project {
	private String name;
	private String shortDescription;
	private double needMoney;
	private double currentMoney;
	private int daysLeft;
	private String urlVideo;
	private Map<String, String> questionAnswer;

	public Project(String name, String shortDescription, double needMoney, double currentMoney,
			int daysLeft) {
		this.name = name;
		this.shortDescription = shortDescription;
		this.needMoney = needMoney;
		this.currentMoney = currentMoney;
		this.daysLeft = daysLeft;
	}

	public Project(String name, String shortDescription, double needMoney, double currentMoney,
			int daysLeft, String urlVideo, Map<String, String> questionAnswer) {
		this.name = name;
		this.shortDescription = shortDescription;
		this.needMoney = needMoney;
		this.currentMoney = currentMoney;
		this.daysLeft = daysLeft;
		this.urlVideo = urlVideo;
		this.questionAnswer = questionAnswer;
	}

	public String getName() {
		return name;
	}

	public String getShortContent() {
		String shortContent = shortDescription + "\n\tNeed money: " + needMoney
				+ "\tCurrent money: " + currentMoney + "\n\tDays left: " + daysLeft;
		return shortContent;
	}

	public String getFullContent() {
		String fullContent = getShortContent() + "\n\tLook video: " + urlVideo + getQuestions();
		return fullContent;
	}

	public String getQuestions() {
		String result = "\n\n\tQuestion\tAnswer";
		if (questionAnswer == null || questionAnswer.size() == 0) {
			return "";
		}
		Set<Map.Entry<String, String>> questions = questionAnswer.entrySet();
		for (Map.Entry<String, String> oneQuestion : questions) {
			String question = oneQuestion.getKey();
			String answer = questionAnswer.get(question);
			result += "\n--------------------------------------------------";
			result += "\n\t" + question + "\n\t\t\t" + answer;
		}
		return result;
	}
}