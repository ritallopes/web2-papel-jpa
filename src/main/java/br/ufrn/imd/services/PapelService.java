package br.ufrn.imd.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.domain.Papel;
import br.ufrn.imd.repositories.PapelRepository;

@RestController
public class PapelService {

	@Autowired
	private PapelRepository repository;

	@GetMapping("/papeis")
	public ResponseEntity<List<Papel>> getAllPapeis() {
		System.out.println("getAllPapeis");

		try {
			List<Papel> papeis = new ArrayList<Papel>();
			repository.findAll().forEach(papeis::add);
			if (papeis.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(papeis, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/papeis/{id}")
	public ResponseEntity<Papel> getPapelById(@PathVariable("id") long id) {
		System.out.println("getPapelById");
		Optional<Papel> papelDB = repository.findById(id);
		if (papelDB.isPresent()) {
			return new ResponseEntity<>(papelDB.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/papeis")
	public ResponseEntity<Papel> createPapel(@RequestBody Papel papel) {
		System.out.println("addPapel");
		try {
			Papel papelDB = repository.save(papel);
			return new ResponseEntity<>(papelDB, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/papeis/{id}")
	public ResponseEntity<Papel> updatePapel(@PathVariable("id") long id, @RequestBody Papel papel) {
		System.out.println("updatePapel");

		Optional<Papel> papelInDB = repository.findById(id);
		if (papelInDB.isPresent()) {
			Papel papel2 = papelInDB.get();
			papel2.setId(papel.getId());
			papel2.setDescricao(papel.getDescricao());
			return new ResponseEntity<>(repository.save(papel2), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/papeis/{id}")
	public ResponseEntity<HttpStatus> deletePapel(@PathVariable("id") long id) {
		System.out.println("deletePapeis");

		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/papeis")
	public ResponseEntity<HttpStatus> deleteAllPapeis() {
		System.out.println("deleteAllPapeis");
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
