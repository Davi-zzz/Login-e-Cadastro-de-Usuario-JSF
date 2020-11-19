package controller;



import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import model.User;
import util.Session;
import util.Util;

@Named
@ViewScoped
public class WelcomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8394143630266495351L;

	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("login.xhtml");
	}

	public User getUsuarioLogado() {
		Object obj = Session.getInstance().getAttribute("usuarioLogado");
		if (obj == null)
			return null;
		return (User) obj;
	}
}
