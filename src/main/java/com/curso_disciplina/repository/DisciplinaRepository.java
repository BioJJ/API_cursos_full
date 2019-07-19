package com.curso_disciplina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso_disciplina.models.Curso;
import com.curso_disciplina.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	
	Iterable<Disciplina> findByCurso(Curso curso);
	Disciplina findById(Integer id);
}
