package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Sexo;
import model.User;
import util.Util;

public class UserDAO implements DAO<User> {

	@Override
	public void inserir(User obj) throws Exception {
		// TODO Auto-generated method stub
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("users ");
		sql.append("  (nome, cpf, data_nascimento, email, sexo, cidade, senha) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			// convertendo um obj LocalDate para sql.Date
			if (obj.getDataNascimento() != null)
				stat.setDate(3, Date.valueOf(obj.getDataNascimento()));
			else
				stat.setDate(3, null);
			stat.setString(4, obj.getEmail());
			// ternario java
			stat.setObject(5, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			stat.setString(6, obj.getCidade());
			stat.setString(7, (Util.hash(obj.getEmail()+obj.getSenha())));

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;


		
	}

	@Override
	public void alterar(User obj) throws Exception {
		// TODO Auto-generated method stub
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE users SET ");
		sql.append("  nome = ?, ");
		sql.append("  cpf = ?, ");
		sql.append("  email = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  cidade = ?, ");
		sql.append("  sexo = ?, ");
		sql.append("  data_nascimento = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getEmail());
			stat.setString(4, (Util.hash(obj.getEmail()+obj.getSenha())));
			stat.setString(5, obj.getCidade());
			// ternario java
			stat.setObject(6, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			// convertendo um obj LocalDate para sql.Date
			stat.setDate(7, obj.getDataNascimento() == null ? null : Date.valueOf(obj.getDataNascimento()));
			stat.setInt(8, obj.getId());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public void excluir(User obj) throws Exception {
		// TODO Auto-generated method stub
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM users WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public List<User> obterTodos() throws Exception {
		// TODO Auto-generated method stub
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<User> listaUsuario = new ArrayList<User>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");

		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.cidade, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  users u ");
		sql.append("ORDER BY u.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				user.setDataNascimento(data == null ? null : data.toLocalDate());
				user.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				user.setNome(rs.getString("nome"));
				user.setCpf(rs.getString("cpf"));
				user.setEmail(rs.getString("email"));
				user.setCidade(rs.getString("cidade"));
				user.setSenha(rs.getString("senha"));

				listaUsuario.add(user);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UserDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaUsuario;
	}

	@Override
	public User obterUm(User obj) throws Exception {
		// TODO Auto-generated method stub
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		User user = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.cidade, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  users u ");
		sql.append("WHERE u.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				user.setDataNascimento(data == null ? null : data.toLocalDate());
				user.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				user.setNome(rs.getString("nome"));
				user.setCpf(rs.getString("cpf"));
				user.setEmail(rs.getString("email"));
				user.setCidade(rs.getString("cidade"));
				user.setSenha(rs.getString("senha"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UserDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return user;
	}
	public User obterUser(String email, String senha) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		User usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.cidade, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  users u ");
		sql.append("WHERE ");
		sql.append("  u.email = ? ");
		sql.append("  AND u.senha = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				usuario = new User();
				usuario.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCidade(rs.getString("cidade"));
				usuario.setSenha(rs.getString("senha"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UsuarioDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return usuario;
	}
}
