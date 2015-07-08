package vadyazakusylo.kickstarter.view.output;

public class ConsoleOutput implements Output {

	@Override
	public void write(Object object) {
		System.out.println(object);
	}
}
