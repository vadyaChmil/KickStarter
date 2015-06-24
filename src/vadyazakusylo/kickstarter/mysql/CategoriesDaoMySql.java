package vadyazakusylo.kickstarter.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vadyazakusylo.kickstarter.model.Category;
import vadyazakusylo.kickstarter.model.dao.CategoriesDao;
import vadyazakusylo.kickstarter.model.exception.GettingDateException;

public class CategoriesDaoMySql implements CategoriesDao {
	private Connection connection;
	private List<Category> categories;

	public CategoriesDaoMySql(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Category> getCategoriesList() throws GettingDateException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectCategories());
			ResultSet resultSet = preparedStatement.executeQuery();
			categories = new ArrayList<Category>();
			while (resultSet.next()) {
				categories.add(new Category(resultSet.getString("category")));
			}
			return categories;
		} catch (SQLException e) {
			System.out.println("Can't connect to table \"Categories\"");
			throw new GettingDateException();
		}
	}

	private String selectCategories() {
		StringBuilder sql = new StringBuilder();
		sql.append("select category ");
		sql.append("from category;");
		return sql.toString();
	}
}
