package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


import dao.UserDAO;
import model.Sexo;
import model.User;
import util.Util;
@Named
@RequestScoped
public class CadastroController extends Controller<User> implements Serializable{

	public CadastroController() {
		super(new UserDAO());
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475305238982335867L;
	
	
	

	public void cadastrar() {
		//inserir verificação posteriormente para saber se já há um usuario cadastrado com o mesmo cpf/email
		UserDAO dao = new UserDAO();
		try {
			
			dao.inserir(getEntity());
			 
				
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("exception no cadastro controller");
		}
		Util.redirect("welcome.xhtml?redirect-faces=true");
	}
	public void redirectButton() {
		Util.redirect("cadastro.xhtml");
	}
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}

	@Override
	public User getEntity() {
		if (entity == null)
			entity = new User();
		return entity;
	}


}
