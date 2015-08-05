package vadyazakusylo.kickstarter.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import vadyazakusylo.kickstarter.model.Project;
import vadyazakusylo.kickstarter.model.dao.ProjectDao;
import vadyazakusylo.kickstarter.model.exception.GettingDateException;

public class ProjectDaoMySql implements ProjectDao {
	private Connection connection;

	public ProjectDaoMySql(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Project getProject(String projectName) {
		Project project = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectProject());
			preparedStatement.setString(1, projectName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("project.name");
				String shortDescription = resultSet.getString("description.description");
				double needMoney = resultSet.getDouble("project.need_money");
				double currentMoney = resultSet.getDouble("project.current_money");
				int daysLeft = resultSet.getInt("project.days_left");
				String urlVideo = resultSet.getString("description.url_video");
				Map<String, String> questionAnswer = getQuestionAnswerMap(projectName);
				project = new Project(name, shortDescription, needMoney, currentMoney, daysLeft,
						urlVideo, questionAnswer);
			}
		} catch (SQLException e) {
			System.out.println("Can't connect to table \"Projects\"");
			throw new GettingDateException();
		}
		return project;
	}

	private String selectProject() {
		StringBuilder sql = new StringBuilder();
		sql.append("select project.name, ");
		sql.append("description.description, ");
		sql.append("project.need_money, project.current_money, ");
		sql.append("project.days_left, description.url_video ");
		sql.append("from project inner join description ");
		sql.append("on project.id = description.id_project ");
		sql.append("and project.name = ?;");
		return sql.toString();
	}

	private Map<String, String> getQuestionAnswerMap(String name) {
		Map<String, String> questionAnswer = new TreeMap<String, String>();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(selectQuestionAnswer());
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Question = resultSet.getString("question");
				String Answer = resultSet.getString("answer");
				questionAnswer.put(Question, Answer);
			}
		} catch (SQLException e) {
			System.out.println("Can't connect to table \"Questions\"");
			System.out.println(e);
			throw new GettingDateException();
		}
		return questionAnswer;
	}

	private String selectQuestionAnswer() {
		StringBuilder sql = new StringBuilder();
		sql.append("select question, answer ");
		sql.append("from question ");
		sql.append("where id_project = ");
		sql.append("(select id ");
		sql.append("from project ");
		sql.append("where name = ?);");
		return sql.toString();
	}

	@Override
	public double getCurrenMoney(String nameProject) throws GettingDateException {
		double currentMoney = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectCurrentMoney());
			preparedStatement.setString(1, nameProject);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				currentMoney = resultSet.getDouble("project.current_money");
			}
		} catch (SQLException e) {
			System.out.println("Can't connect to table \"Projects\"");
			throw new GettingDateException();
		}
		return currentMoney;
	}

	private String selectCurrentMoney() {
		StringBuilder sql = new StringBuilder();
		sql.append("select current_money ");
		sql.append("from project ");
		sql.append("where name = ?;");
		return sql.toString();
	}

	@Override
	public void setCurrentMoney(double money, String nameProject) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateCurrentMoney());
			preparedStatement.setDouble(1, money);
			preparedStatement.setString(2, nameProject);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Transaction isn't successful");
		}
	}

	private String updateCurrentMoney() {
		StringBuilder sql = new StringBuilder();
		sql.append("update project ");
		sql.append("set current_money = ? ");
		sql.append("where name = ?;");
		return sql.toString();
	}

	@Override
	public void setQuestion(String question, String name) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuestion());
			preparedStatement.setString(1, question);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Problems with connect. Try again.");
		}
	}

	private String updateQuestion() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into question ");
		sql.append("(question, id_project) ");
		sql.append("values (?, ");
		sql.append("(select id ");
		sql.append("from project ");
		sql.append("where name = ?));");
		return sql.toString();
	}
}
