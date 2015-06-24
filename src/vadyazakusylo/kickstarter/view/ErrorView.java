package vadyazakusylo.kickstarter.view;

import vadyazakusylo.kickstarter.controller.Controller;
import vadyazakusylo.kickstarter.model.Model;
import vadyazakusylo.kickstarter.view.factory.State;
import vadyazakusylo.kickstarter.view.input.Input;
import vadyazakusylo.kickstarter.view.output.Output;

public class ErrorView extends ViewAbstract {

	public ErrorView(Model model, Controller controller, Input input, Output output) {
		super(model, controller, input, output);
	}

	@Override
	public void printContent() {
		output.write("----------------------ERROR-----------------------");
		output.write();
		output.write("--You have inputted incorrect number or symbols---");
		output.write("----Input 0 for return to the previous window-----");
	}

	@Override
	public State chooseDirection() {
		int inputNumber = input.readInt();
		if (inputNumber == 0) {
			state = State.OUT_FROM_ERROR;
		} else {
			state = State.ERROR;
		}
		return state;
	}
}