package vadyazakusylo.kickstarter.controller;

import vadyazakusylo.kickstarter.view.View;
import vadyazakusylo.kickstarter.view.factory.State;
import vadyazakusylo.kickstarter.view.factory.ViewFactory;

public class KickStarter {
	private ViewFactory viewFactory;

	public KickStarter(Controller controller, ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public void go() {
		State previousState = null;
		State activeState = State.START;
		while (true) {
			View view = viewFactory.getView(activeState);
			view.printContent();
			if (activeState == State.EXIT) {
				break;
			}
			if (activeState != State.ERROR) {
				previousState = activeState;
			}
			activeState = view.chooseDirection();
			if (activeState == State.OUT_FROM_ERROR) {
				activeState = previousState;
			}
		}
	}
}
