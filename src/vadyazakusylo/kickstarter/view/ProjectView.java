package vadyazakusylo.kickstarter.view;

import vadyazakusylo.kickstarter.controller.Controller;
import vadyazakusylo.kickstarter.model.Model;
import vadyazakusylo.kickstarter.model.Project;
import vadyazakusylo.kickstarter.model.exception.GettingDateException;
import vadyazakusylo.kickstarter.view.factory.State;
import vadyazakusylo.kickstarter.view.input.Input;
import vadyazakusylo.kickstarter.view.output.Output;

public class ProjectView extends ViewAbstract {
	Project project;

	public ProjectView(Model model, Controller controller, Input input, Output output) {
		super(model, controller, input, output);
	}

	@Override
	public void printContent() {
		output.write("---------------------Project----------------------");
		try {
			output.write("\n");
			printProject();
			output.write("\nInput:\n1 - to donate\n2 - to ask question\n0 - to return");
		} catch (GettingDateException e) {
			output.write("\n");
			output.write(e);
			output.write("\nInput:\n0 - to return");
		}
	}

	private void printProject() {
		project = model.getProject(model.getWorkingProject().getName());
		controller.setWorkingProject(project);
		output.write("You have chosen " + project.getName());
		output.write(project.getFullContent());
	}

	@Override
	public State chooseDirection() {
		int inputNumber = input.readInt();
		if (project != null && inputNumber == 1) {
			state = State.DONATE;
		} else if (project != null && inputNumber == 2) {
			state = State.QUESTION;
		} else if (inputNumber == 0) {
			state = State.PROJECTS;
		} else {
			state = State.ERROR;
		}
		return state;
	}
}