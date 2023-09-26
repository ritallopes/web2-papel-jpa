package br.ufrn.imd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.domain.Papel;
import br.ufrn.imd.repositories.PapelRepository;

@RestController
public class PapelService {

	@Autowired
	private PapelRepository repository;
	
	@RequestMapping("/papeis")
	public List<Papel> getUsers() {
		return (List<Papel>) repository.findAll();
	}

}
