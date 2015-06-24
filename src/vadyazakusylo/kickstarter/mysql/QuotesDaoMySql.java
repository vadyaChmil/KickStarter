package vadyazakusylo.kickstarter.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vadyazakusylo.kickstarter.model.Quote;
import vadyazakusylo.kickstarter.model.dao.QuotesDao;
import vadyazakusylo.kickstarter.model.exception.GettingDateException;

public class QuotesDaoMySql implements QuotesDao {
	private Connection connection;

	public QuotesDaoMySql(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Quote> getQuotesList() throws GettingDateException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuotes());
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Quote> quotes = new ArrayList<Quote>();
			while (resultSet.next()) {
				quotes.add(new Quote(resultSet.getString("quotes_q.quote"), resultSet
						.getString("quotes_a.autor")));
			}
			return quotes;
		} catch (SQLException e) {
			System.out.println("Can't connect to table \"Quotes\"");
			throw new GettingDateException();
		}
	}

	private String selectQuotes() {
		StringBuilder sql = new StringBuilder();
		sql.append("select quotes_q.quote,  quotes_a.autor ");
		sql.append("from quotes_q natural join quotes_a;");
		return sql.toString();
	}
}
