package controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import dao.UserDAO;
import model.User;
import util.Session;
import util.Util;
@Named
@RequestScoped
public class LoginController {

	private User user;

	public void logar() {
		
		UserDAO dao = new UserDAO();
		try {
			User usuarioLogado = 
					dao.obterUser(getUser().getEmail(), 
							Util.hash(getUser().getEmail()+getUser().getSenha()));
			if (usuarioLogado == null)
				Util.addErrorMessage("Usuário ou senha inválido.");
			else {
				// Usuario existe com as credenciais
				Session.getInstance().setAttribute("usuarioLogado", usuarioLogado);
				Util.redirect("welcome.xhtml");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problema ao verificar o Login. Entre em contato pelo email: contato@email.com.br");
		}
	}

	public User getUser() {
		if (user == null)
			user = new User();
		return user;
	}

	public void setUsuario(User usuario) {
		this.user = usuario;
	}
	public void redirectButton() {
		Util.redirect("cadastro.xhtml");
	}
}
