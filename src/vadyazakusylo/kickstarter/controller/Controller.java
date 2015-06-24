package vadyazakusylo.kickstarter.controller;

import vadyazakusylo.kickstarter.model.Category;
import vadyazakusylo.kickstarter.model.Model;
import vadyazakusylo.kickstarter.model.Project;

public class Controller {
	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void setWorkingCategory(Category category) {
		model.setWorkingCategory(category);
	}

	public void setWorkingProject(Project project) {
		model.setWorkingProject(project);
	}

	public void setCurrentMoney(double money) {
		model.setCurrentMoney(money);
	}

	public void setQuestion(String question) {
		model.setQuestion(question);
	}
}
