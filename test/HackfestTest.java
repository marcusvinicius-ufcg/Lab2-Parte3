import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import models.Evento;
import models.Participante;
import models.Tema;


public class HackfestTest {

	Evento evento;
	//admim de um hackfest cadastrar evento
	@Before
	public void setup(){
		evento = new Evento();
		evento.setNome("nome");
		evento.setDescricao("descricao");
		evento.setData(new Date());
	}
	
	@Test
	public void cadastrarParticipante() {
		Participante participante = new Participante();
		
		participante.setNome("nome");
		participante.setEmail("teste@teste.com");
		
		assertTrue(evento.cadastrarParticipante(participante));
		
		//nao permitir cadastrar participantes iguais no mesmo evento
		assertFalse(evento.cadastrarParticipante(participante));
		
		//checando quantidade
		assertTrue(evento.getParticipantes().size() == 1);
	}
	
	@Test
	public void removerParticipante() {
		Participante participante = new Participante();
		
		participante.setNome("nome");
		participante.setEmail("teste@teste.com");
		
		assertTrue(evento.cadastrarParticipante(participante));
		
		//nao permitir cadastrar participantes iguais no mesmo evento
		assertTrue(evento.removerParticipante(participante.getId()));
		
		//checando quantidade
		assertFalse(evento.getParticipantes().contains(participante));
	}
	
	@Test
	public void cadastrarTema() {
		Tema tema = new Tema();
		
		tema.setNome("Teste Play");
		
		assertTrue(evento.cadastrarTema(tema));
		
		//nao permitir cadastrar temas iguais no mesmo evento
		assertFalse(evento.cadastrarTema(tema));
		
		//checando quantidade
		assertTrue(evento.getTemas().size() == 1);
	}
	
	@Test
	public void removerTema() {
		Tema tema = new Tema();
		
		tema.setNome("Teste Play");
		
		assertTrue(evento.cadastrarTema(tema));
		
		//nao permitir cadastrar participantes iguais no mesmo evento
		assertTrue(evento.removerTema(tema.getId()));
		
		//checando quantidade
		assertFalse(evento.getTemas().contains(tema));
	}
	
	@Test
	public void ordenarPorInscritos(){
		Evento novoEvento = new Evento();
		
		novoEvento.setNome("nome");
		novoEvento.setDescricao("descricao");
		novoEvento.setData(new Date());
		
		Participante participante1 = new Participante();
		
		participante1.setNome("nome1");
		participante1.setEmail("email-1@email.com");
		
		Participante participante2 = new Participante();
		participante2.setNome("nome2");
		participante2.setEmail("email-2@email.com");
		
		Participante participante3 = new Participante();
		
		participante3.setNome("nome3");
		participante3.setEmail("email-3@email.com");
		
		//adicionando participantes
		assertTrue(novoEvento.cadastrarParticipante(participante1));
		assertTrue(novoEvento.cadastrarParticipante(participante2));
		assertTrue(evento.cadastrarParticipante(participante3));
		
		//checando quantidade
		assertTrue(evento.getParticipantes().size() == 1);
		assertTrue(novoEvento.getParticipantes().size() == 2);
		
		List<Evento> eventos = new ArrayList<>();
		
		eventos.add(evento);
		eventos.add(novoEvento);
		
		//checando primeiro elemento da lista desordenada
		assertTrue(eventos.get(0).getParticipantes().size() == 1);
		
		Collections.sort(eventos);
		
		assertTrue(eventos.get(0).getParticipantes().size() == 2);
	}
}
