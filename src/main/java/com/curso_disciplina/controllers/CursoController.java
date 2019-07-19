package com.curso_disciplina.controllers;

import java.util.List;

import javax.smartcardio.ATR;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso_disciplina.models.Curso;
import com.curso_disciplina.models.Disciplina;
import com.curso_disciplina.repository.CursoRepository;
import com.curso_disciplina.repository.DisciplinaRepository;


@Controller
public class CursoController {
	
	@Autowired
	private CursoRepository cr;
	
	@Autowired
	private DisciplinaRepository dr;
	
	//Carregar o formulario
	@RequestMapping(value="/cadastrarCurso", method=RequestMethod.GET)
	public String form(){
		return "Curso/formCurso";
	}
	
	//Cadastrar curso
	@RequestMapping(value="/cadastrarCurso", method=RequestMethod.POST)
	public String salvar(@Valid Curso curso, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarCurso";
		}
		cr.save(curso);
		attributes.addFlashAttribute("mensagem", "Curso Cadastrado com sucesso");
		return "redirect:/cadastrarCurso";
	}
	
	//Lista curso
	@RequestMapping("/curso")
	public ModelAndView listaEventos(){
		ModelAndView mv = new ModelAndView("index");
		Iterable<Curso> curso = cr.findAll();
		mv.addObject("cursos", curso);
		return mv;
	}
	//listar disciplinas no detalhe do curso
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesCurso(@PathVariable("id") int id){
		Curso curso = cr.findById(id);
		
		ModelAndView mv = new ModelAndView("Curso/detalhesCurso");
		mv.addObject("cursos", curso);
		
		Iterable<Disciplina> disc = dr.findByCurso(curso);
		mv.addObject("disciplinas", disc);
		
		return mv;
	}
	//deletar curso
	@DeleteMapping("/deletarCurso/{id}")
	public String deletarCurso(int id) {
		Curso curso = cr.findById(id);
		
		cr.delete(curso);
		
		return "redirect:/curso";
	}
	
	//Adicionar disciplina ao curso
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesCursoPost(@PathVariable("id") int id, @Valid Disciplina disciplina, BindingResult result, RedirectAttributes attributes){
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{id}";
		}
		Curso curso = cr.findById(id);
		disciplina.setCurso(curso);
		dr.save(disciplina);
		
		attributes.addFlashAttribute("mensagem", "Disciplina adicionada com sucesso");
		
		return "redirect:/{id}";
	}
	//deletar disciplina
	@RequestMapping(value="deletarDisciplina/{id}", method=RequestMethod.DELETE)
	public String deletarDiscplina(@PathVariable("id") int id){
		Disciplina disc = dr.findById(id);
		dr.delete(disc);
		
		Curso curso = disc.getCurso();
		long codigoLong = curso.getId();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
	
}
