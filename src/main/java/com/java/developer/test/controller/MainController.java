package com.java.developer.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.developer.test.dto.UserDTO;
import com.java.developer.test.service.JobService;
import com.java.developer.test.service.JwtUserDtlService;
import com.java.developer.test.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class MainController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDtlService jwtUserDtlService;
  private final UserDetailsService userDetailsService;
  private final JobService jobService;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public MainController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDtlService jwtUserDtlService, UserDetailsService userDetailsService, JobService jobService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.jwtUserDtlService = jwtUserDtlService;
    this.userDetailsService = userDetailsService;
    this.jobService = jobService;
  }

  @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> login(@RequestBody UserDTO jwtReq) throws Exception {
    authenticate(jwtReq.getUsername(), jwtReq.getPassword());

    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(jwtReq.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(objectMapper.writeValueAsString(Collections.singletonMap("jwt", token)));
  }

  @GetMapping(value = "/jobs", produces = "application/json")
  public ResponseEntity<?> login() throws Exception {
    return ResponseEntity.ok(jobService.getJobs());
  }

  @GetMapping(value = "/job/{id}", produces = "application/json")
  public ResponseEntity<?> login(@PathVariable(value = "id") String id) throws Exception {
    return ResponseEntity.ok(jobService.getJobDtl(id));
  }

  @GetMapping("/register")
  public ResponseEntity<?> oke(){
    UserDTO user = new UserDTO();
    user.setUsername("admin");
    user.setPassword("admin123");
    jwtUserDtlService.save(user);
    return ResponseEntity.ok("oke");
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
