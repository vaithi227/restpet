package com.gcp.demo.kmm.rest;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gcp.demo.kmm.model.Projection;
import com.gcp.demo.kmm.service.KmmProjectionService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/projections")
public class ProjectionRestController {

	@Autowired
	private KmmProjectionService kmmProjectionService;

	@RequestMapping(value = "/*/projectionName/{projectionName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Projection>> getProjectionsList(@PathVariable("projectionName") String projectionName) {
		if (projectionName == null) {
			projectionName = "";
		}
		Collection<Projection> projections = this.kmmProjectionService.findProjectionByProjectionName(projectionName);
		if (projections.isEmpty()) {
			return new ResponseEntity<Collection<Projection>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Projection>>(projections, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Projection>> getProjections() {
		Collection<Projection> projections = this.kmmProjectionService.findAllProjections();
		if (projections.isEmpty()) {
			return new ResponseEntity<Collection<Projection>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Projection>>(projections, HttpStatus.OK);
	}

	@RequestMapping(value = "/{projectionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Projection> getProjection(@PathVariable("projectionId") int projectionId) {
		Projection projection = null;
		projection = this.kmmProjectionService.findProjectionById(projectionId);
		if (projection == null) {
			return new ResponseEntity<Projection>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Projection>(projection, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Projection> addProjection(@RequestBody @Valid Projection projection, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (projection == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Projection>(headers, HttpStatus.BAD_REQUEST);
		}
		this.kmmProjectionService.saveProjection(projection);
		headers.setLocation(ucBuilder.path("/api/projections/{id}").buildAndExpand(projection.getId()).toUri());
		return new ResponseEntity<Projection>(projection, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{projectionId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Projection> updateProjection(@PathVariable("projectionId") int projectionId, @RequestBody @Valid Projection projection,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (projection == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Projection>(headers, HttpStatus.BAD_REQUEST);
		}
		Projection currentProjection = this.kmmProjectionService.findProjectionById(projectionId);
		if (currentProjection == null) {
			return new ResponseEntity<Projection>(HttpStatus.NOT_FOUND);
		}
		currentProjection.setProgramName(projection.getProgramName());
		currentProjection.setProjectionName(projection.getProjectionName());
		
		
		
		this.kmmProjectionService.saveProjection(currentProjection);
		return new ResponseEntity<Projection>(currentProjection, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{projectionId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteProjection(@PathVariable("projectionId") int projectionId) {
		Projection projection = this.kmmProjectionService.findProjectionById(projectionId);
		if (projection == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.kmmProjectionService.deleteProjection(projection);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
