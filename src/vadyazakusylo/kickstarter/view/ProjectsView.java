package vadyazakusylo.kickstarter.view;

import java.util.List;

import vadyazakusylo.kickstarter.controller.Controller;
import vadyazakusylo.kickstarter.model.Category;
import vadyazakusylo.kickstarter.model.Model;
import vadyazakusylo.kickstarter.model.Project;
import vadyazakusylo.kickstarter.model.exception.GettingDateException;
import vadyazakusylo.kickstarter.view.factory.State;
import vadyazakusylo.kickstarter.view.input.Input;
import vadyazakusylo.kickstarter.view.output.Output;

public class ProjectsView extends ViewAbstract {
	List<Project> projectsList;

	public ProjectsView(Model model, Controller controller, Input input, Output output) {
		super(model, controller, input, output);
	}

	@Override
	public void printContent() {
		output.write("------------Choose one of the project-------------");
		try {
			output.write("\n");
			printProjectsList();
		} catch (GettingDateException e) {
			output.write("\n");
			output.write(e);
		}
		output.write("\n0. Return");
	}

	private void printProjectsList() {
		Category category = model.getWorkingCategory();
		projectsList = model.getProjectsList(category);
		for (int oneProject = 1; oneProject <= projectsList.size(); oneProject++) {
			output.write(oneProject + ". " + projectsList.get(oneProject - 1).getName());
			output.write(projectsList.get(oneProject - 1).getShortContent());
		}
	}

	@Override
	public State chooseDirection() {
		int inputNumber = input.readInt();
		if (inputNumber == 0) {
			state = State.CATEGORIES;
		} else if (inputNumber > 0 && inputNumber <= projectsList.size()) {
			state = State.PROJECT;
			setWorkingProject(inputNumber - 1);
		} else {
			state = State.ERROR;
		}
		return state;
	}

	private void setWorkingProject(int inputNumber) {
		controller.setWorkingProject(projectsList.get(inputNumber));
	}
}