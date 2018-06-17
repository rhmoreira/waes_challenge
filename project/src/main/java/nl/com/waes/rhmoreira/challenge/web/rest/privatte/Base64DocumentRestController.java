package nl.com.waes.rhmoreira.challenge.web.rest.privatte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.db.nosql.entity.Orientation;
import nl.com.waes.rhmoreira.challenge.service.B64DocumentService;
import nl.com.waes.rhmoreira.challenge.service.vo.DiffResult;
import nl.com.waes.rhmoreira.challenge.web.rest.vo.Base64Data;
import nl.com.waes.rhmoreira.challenge.web.rest.vo.Message;
import nl.com.waes.rhmoreira.challenge.web.rest.vo.ResponseObject;

/**
 * Rest Controller class to store and process the base64 data posted.
 * 
 * @author renato.moreira
 *
 */
@RestController
@RequestMapping(value="/v1/diff/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Base64DocumentRestController {
	
	@Autowired
	private B64DocumentService b64DocService;

	/**
	 * Post the left side of the data
	 * @param id
	 * @param data
	 * @return {@link Message}
	 */
	@PostMapping("/left")
//	@PreAuthorize("hasAuthority('CHALLENGE_AUTH')")
	public ResponseEntity<Void> saveLeft(@PathVariable("id") String id, @RequestBody Base64Data data){
		saveData(id, data, Orientation.LEFT);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Post the right side of the data
	 * @param id
	 * @param data
	 * @return {@link Message}
	 */
	@PostMapping("/right")
	//@PreAuthorize("hasAuthority('CHALLENGE_AUTH')")
	public ResponseEntity<Void> saveRight(@PathVariable("id") String id, @RequestBody Base64Data data){
		saveData(id, data, Orientation.RIGHT);
		return ResponseEntity.ok().build();
	}

	/**
	 * Get the diff comparison of both sides of the previously stored data.
	 * @param id
	 * @return {@link ResponseObject} Returns a json representation of <code>ResponseObject</code> 
	 * containing the result of the comparion.
	 * 
	 */
	@GetMapping
	//@PreAuthorize("hasAuthority('CHALLENGE_AUTH')")
	public ResponseEntity<ResponseObject> compare(@PathVariable("id") String id){
		DiffResult evaluation = b64DocService.evaluateJsonDocument(id);
		
		return ResponseEntity.ok(new ResponseObject(evaluation));
	}
	
	private void saveData(String id, Base64Data data, Orientation orientation) {
		if (data == null)
			throw new ChallengeException("No data provided");
		
		b64DocService.save(id, data.getValue(), orientation);
	}
}
