package com.curso_disciplina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso_disciplina.models.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
	Curso findById(int id);
}
