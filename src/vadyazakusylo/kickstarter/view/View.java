package vadyazakusylo.kickstarter.view;

import vadyazakusylo.kickstarter.view.factory.State;

public interface View {

	void printContent();

	State chooseDirection();

}
