package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import models.Evento;
import models.Tema;
import models.Participante;
import models.DAO.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;

public class Application extends Controller {

	public static GenericDAO dao = new GenericDAO();

	static {
		String[] temas = { "Java", "Banco de Dados", "Cloud Computing", "Web",
				"Android" };

		String[] eventos = { "Over the Air", "Música hack Day",
				"Cloud Computing", "Análise e Relatórios", "Web Development",
				"Framework's", "Plataforma Java", "DBOO", "Scrum", "Segurança" };

		String[] descricoes = { "Desenvolvimento de aplicações Mobile",
				"Maratona hacker para software relacionado a Música",
				"Plataforma e Software como serviço",
				"O que dizem os dados e como publicar tais informações?",
				"Desenvolvimento de aplicações Web",
				"Provendo uma funcionalidade mais genérica.",
				"Explorando Recursos", "Banco de Dados Orientado a Objetos",
				"Desenvolvimento Ágil", "Protocolos Criptografados." };

		try {
			Random random = new Random();
			for (int i = 0; i < eventos.length; i++) {
				Calendar data = new GregorianCalendar();
				data.set(Calendar.WEEK_OF_YEAR, Calendar.WEEK_OF_YEAR + random.nextInt(6)+1);
				Evento evento = new Evento();
				evento.setDescricao(descricoes[i]);
				evento.setNome(eventos[i]);
				evento.setData(data.getTime());
				Tema tema1 = new Tema();
				Tema tema2 = new Tema();
				
				Participante p1 = new Participante();
				p1.setNome("Jack Wilson");
				p1.setEmail("jack@hotmail.com");
				
				Participante p2 = new Participante();
				p2.setNome("Roberto");
				p2.setEmail("roberto@db4o.com");
				
				Participante p3 = new Participante();
				p3.setNome("Camila");
				p3.setEmail("camila@amazon.com");
				
				Participante p4 = new Participante();
				p4.setNome("Maria");
				p4.setEmail("maria@dell.com");
				
				Participante p5 = new Participante();
				p5.setNome("Thiago");
				p5.setEmail("thiago@google.br");
				
				Participante p6 = new Participante();
				p6.setNome("João");
				p6.setEmail("joao@facebook.com");
				
				Participante p7 = new Participante();
				p7.setNome("Priscila");
				p7.setEmail("priscila@microsoft.com");
				
				Participante p8 = new Participante();
				p8.setNome("Vinicius");
				p8.setEmail("vinicius@hotmail.com");
				
				

				switch (i) {
				case 0:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[4]);
					evento.cadastrarParticipante(p1);
					evento.cadastrarParticipante(p2);
					evento.cadastrarParticipante(p3);
					evento.cadastrarParticipante(p4);
					evento.cadastrarParticipante(p5);
					evento.cadastrarParticipante(p6);
					evento.cadastrarParticipante(p7);
					evento.cadastrarParticipante(p8);
					break;
				case 1:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[4]);
					break;
				case 2:
					tema1.setNome(temas[2]);
					tema2.setNome(temas[3]);
					break;
				case 3:
					tema1.setNome(temas[1]);
					tema2.setNome(temas[3]);
					break;
				case 4:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[3]);
					evento.cadastrarParticipante(p1);
					evento.cadastrarParticipante(p2);
					evento.cadastrarParticipante(p3);
					evento.cadastrarParticipante(p4);
					evento.cadastrarParticipante(p5);
					evento.cadastrarParticipante(p6);
					evento.cadastrarParticipante(p7);
					break;
				case 5:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[1]);
					break;
				case 6:
					tema1.setNome(temas[3]);
					tema2.setNome(temas[0]);
					break;
				case 7:
					tema1.setNome(temas[1]);
					tema2.setNome(temas[0]);
					evento.cadastrarParticipante(p1);
					evento.cadastrarParticipante(p2);
					evento.cadastrarParticipante(p3);
					evento.cadastrarParticipante(p4);
					evento.cadastrarParticipante(p5);
					break;
				case 8:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[4]);
					break;
				case 9:
					tema1.setNome(temas[0]);
					tema2.setNome(temas[2]);
					
					break;
				default:
					break;
				}

				evento.cadastrarTema(tema1);
				evento.cadastrarTema(tema2);
				
				

				dao.persist(evento);
				dao.flush();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Transactional
	public static Result index() {
		
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);

		return ok(index.render("Hackfests", eventos, getTemas()));
	}

	@Transactional
	public static Result inscritos() {
		
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);
		
		return ok(listaInscritos.render(eventos));
	}

	@Transactional
	public static Result filtrar() {
		
		DynamicForm requestData = Form.form().bindFromRequest();

		String temaFiltro = requestData.get("filtro");

		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);
		if (temaFiltro.equals("Todos"))
			return ok(index.render("Hackfests", eventos, getTemas()));

		List<Evento> listaFiltrada = new ArrayList<>();
		Tema tema1 = new Tema();
		tema1.setNome(temaFiltro);
		for (Evento evento : eventos)
			if (evento.getTemas().contains(tema1))
				listaFiltrada.add(evento);

		return ok(index.render("Hackfests", listaFiltrada, getTemas()));
	}

	public static Result abriCadastroEvento() {
		Form<Evento> evento = form(Evento.class);
		return ok(cadastrarEvento.render(evento));
	}

	@Transactional
	public static Result cadastrarEvento() {
		Form<Evento> form = form(Evento.class).bindFromRequest();

		if (form.hasErrors())

			return badRequest(cadastrarEvento.render(form));

		Evento evento = form.get();

		dao.persist(evento);
		dao.flush();

		return redirect(routes.Application.index());
	}

	@Transactional
	public static Result inscricao() {
		DynamicForm requestData = Form.form().bindFromRequest();

		long id = Long.parseLong(requestData.get("ID"));

		Form<Participante> participante = form(Participante.class);

		return ok(inscricao.render(id, participante));
	}

	@Transactional
	public static Result inscrever(Long id) {

		Form<Participante> alterarForm = Form.form(Participante.class)
				.bindFromRequest();
		if (alterarForm.hasErrors()) {
			return badRequest(inscricao.render(id, alterarForm));
		}
		Participante participante = alterarForm.get();
		Evento evento = dao.findByEntityId(Evento.class, id);

		evento.cadastrarParticipante(participante);

		dao.merge(evento);
		dao.flush();
		return redirect(routes.Application.index());
	}

	@Transactional
	public static Result salvarAlteracaoEvento(long id) {
		Form<Evento> alterarForm = form(Evento.class).bindFromRequest();

		if (alterarForm.hasErrors()) {
			Evento evento = dao.findByEntityId(Evento.class, id);
			return badRequest(editarEvento.render(alterarForm, evento));
		}
		Evento evento = alterarForm.get();
		Evento eventoAnterior = dao.findByEntityId(Evento.class, id);
		evento.setParticipantes(eventoAnterior.getParticipantes());
		evento.setTemas(eventoAnterior.getTemas());
		
		dao.merge(evento);
		dao.flush();
		
		return redirect(routes.Application.index());
	}

	@Transactional
	public static Result abriEdicaoEvento() {
		DynamicForm requestData = Form.form().bindFromRequest();
		long id = Long.parseLong(requestData.get("opcoes"));

		Form<Evento> metaForm = form(Evento.class).fill(
				dao.findByEntityId(Evento.class, id));

		Evento evento = dao.findByEntityId(Evento.class, id);

		return ok(editarEvento.render(metaForm, evento));
	}

	@Transactional
	public static Result selecionarEvento() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);

		return ok(selecionarEvento.render(eventos));
	}

	@Transactional
	public static Result removerEvento() {
		DynamicForm requestData = Form.form().bindFromRequest();
		long id = Long.parseLong(requestData.get("opcoes"));

		dao.removeById(Evento.class, id);
		dao.flush();
		return redirect(routes.Application.index());
	}

	@Transactional
	public static Result removerTemaEvento(long id) {
		DynamicForm requestData = Form.form().bindFromRequest();
		long idTema = Long.parseLong(requestData.get("ID_TEMA"));

		Evento evento = dao.findByEntityId(Evento.class, id);

		evento.removerTema(idTema);
		dao.merge(evento);
		dao.flush();

		Form<Evento> metaForm = form(Evento.class).fill(
				dao.findByEntityId(Evento.class, id));

		Evento eventoAtualizado = dao.findByEntityId(Evento.class, id);

		return ok(editarEvento.render(metaForm, eventoAtualizado));
	}

	@Transactional
	public static Result abrirRemoverEvento() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);

		return ok(excluirEvento.render(eventos));
	}

	@Transactional
	public static Result listarTemas() {
		
		return ok(listarTemas.render(getTemas()));
	}

	@Transactional
	public static Result abrirCadastroTemas() {
		Form<Tema> tema = form(Tema.class);
		return ok(cadastrarTema.render(tema));
	}

	@Transactional
	public static Result cadastrarTema() {
		Form<Tema> form = form(Tema.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(cadastrarTema.render(form));
		}

		Tema tema = form.get();

		dao.persist(tema);
		dao.flush();

		return redirect(routes.Application.listarTemas());
	}

	@Transactional
	public static Result abrirSelecaoTema() {
		

		return ok(selecionarTema.render(getTemas()));
	}

	@Transactional
	public static Result abrirEdicaoTema() {
		DynamicForm requestData = Form.form().bindFromRequest();
		long id = Long.parseLong(requestData.get("opcoes"));

		Form<Tema> temaForm = form(Tema.class).fill(
				dao.findByEntityId(Tema.class, id));

		return ok(editarTema.render(id, temaForm));
	}

	@Transactional
	public static Result atualizarTema(long id) {

		Form<Tema> alterarForm = form(Tema.class).bindFromRequest();

		if (alterarForm.hasErrors()) {

			return badRequest(editarTema.render(id, alterarForm));
		}
		Tema tema = alterarForm.get();

		dao.merge(tema);
		dao.flush();
		return redirect(routes.Application.listarTemas());
	}

	@Transactional
	public static Result abrirRemoverTema() {
		return ok(excluirTema.render(getTemas()));

	}

	@Transactional
	public static Result removerTema() {
		DynamicForm requestData = Form.form().bindFromRequest();
		long id = Long.parseLong(requestData.get("opcoes"));

		dao.removeById(Tema.class, id);
		dao.flush();

		return redirect(routes.Application.listarTemas());
	}

	@Transactional
	public static Result selecionarAdicionarTema() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);

		return ok(selecionarAdicionarTema.render(eventos));
	}

	@Transactional
	public static Result abrirEventoAdicionarTema() {
		DynamicForm requestData = Form.form().bindFromRequest();
		long id = Long.parseLong(requestData.get("opcoes"));

		Form<Evento> metaForm = form(Evento.class).fill(
				dao.findByEntityId(Evento.class, id));

		Evento evento = dao.findByEntityId(Evento.class, id);


		return ok(adicionarTemas.render(metaForm, evento, getTemas()));
	}

	@Transactional
	public static Result addTemaEvento(long id) {

		DynamicForm requestData = Form.form().bindFromRequest();
		long idTema = Long.parseLong(requestData.get("ID_TEMA"));

		Evento evento = dao.findByEntityId(Evento.class, id);

		Tema tema = dao.findByEntityId(Tema.class, idTema);

		evento.cadastrarTema(tema);

		dao.merge(evento);
		dao.flush();

		Form<Evento> metaForm = form(Evento.class).fill(
				dao.findByEntityId(Evento.class, id));

		Evento eventoAtualizado = dao.findByEntityId(Evento.class, id);

		return ok(adicionarTemas.render(metaForm, eventoAtualizado, getTemas()));

	}
	
	private static List<Tema> getTemas(){
		List<Tema> todosTemas = dao.findAllByClassName("Tema");
		List<Tema> temas = new ArrayList<>();
		boolean aux = true;
		for (Tema allTema: todosTemas) {
			for (Tema tema : temas) {
				if(tema.getNome().equals(allTema.getNome())){
					aux = false;
					break;
				}
			}
			if(aux){
				temas.add(allTema);
			}
			aux=true;
		}
		return temas;
	}

}
