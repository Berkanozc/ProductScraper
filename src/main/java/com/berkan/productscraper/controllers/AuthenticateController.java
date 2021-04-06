package com.berkan.productscraper.controllers;

import com.berkan.productscraper.models.User;
import com.berkan.productscraper.repositories.BrandRepository;
import com.berkan.productscraper.repositories.UserRepository;
import com.berkan.productscraper.utility.JWTToken;
import com.berkan.productscraper.utility.WebConfig;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private WebConfig webConfig;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @PostMapping()
    public ResponseEntity<User> authenticate(@RequestBody ObjectNode credentialsJSON) {

        String email = credentialsJSON.get("email").asText();
        String password = credentialsJSON.get("password").asText();

        // Search user by given email
        Query q = em.createNamedQuery("User.find_user_by_email");
        q.setParameter(1, email);

        User user;

        try {
            user = (User) q.getSingleResult();
        } catch (NoResultException e) {
            // When no user is found
            user = registerNewUser(credentialsJSON);

            if (user == null) {
                return ResponseEntity.badRequest().header("YOO").build();
            }
        }

        if (user.getHashedPassword().equals(password)) {
            return ResponseEntity
                    .accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJWTToken(user))
                    .body(user);
        }

        return ResponseEntity.badRequest().build();
    }

    private User registerNewUser(ObjectNode credentialsJSON) {
        // register new user
        String email = credentialsJSON.get("email").asText();
        String password = credentialsJSON.get("password").asText();
        String firstName = credentialsJSON.get("firstName").asText();
        String lastName = credentialsJSON.get("lastName").asText();
        String companyName = credentialsJSON.get("companyName").asText();

        if (email == null || password == null || firstName == null || lastName == null) {
            return null;
        }

        return userRepository.save(new User(firstName, lastName, email, password, companyName));
    }

    private String generateJWTToken(User user) {
        JWTToken jwToken = new JWTToken(user.getId(), user.getEmail());
        return jwToken.encode(this.webConfig.issuer, this.webConfig.passPhrase, this.webConfig.tokenDurationOfValidity);
    }
}
