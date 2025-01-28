package com.estf.edoctorat.config;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estf.edoctorat.dto.LoginRequest;
import com.estf.edoctorat.models.AuthGroupModel;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.CandidatRepository;
import com.estf.edoctorat.repositories.PaysRepository;
import com.estf.edoctorat.repositories.ProfesseurRepository;
import com.estf.edoctorat.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ProfesseurRepository professeurRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PaysRepository paysRepository;
    @Autowired
    private CandidatRepository candidatRepository;

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        return googleTokenVerifier.verify(idTokenString);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        try {
            UserModel user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateToken(Map.of("tokenType", "refresh"), userDetails);

            // Set JWT cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(jwtCookie);

            // Add Authorization header
            response.setHeader("Authorization", "Bearer " + token);

            return ResponseEntity.ok()
                .header("Access-Control-Expose-Headers", "Authorization")
                .body(new AuthResponse(token, refreshToken, createUserInfo(user)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
        
        return ResponseEntity.ok().body("Logged out successfully");
    }

    private AuthResponse.UserInfo createUserInfo(UserModel user) {
        List<String> groups = user.getUserGroup() != null ? user.getUserGroup().getGroups().stream()
                .map(AuthGroupModel::getNom)
                .collect(Collectors.toList()) : Collections.emptyList();

        Map<String, Object> misc = new HashMap<>();

        // Check if user is professor
        Optional<ProfesseurModel> profOpt = professeurRepository.findByUser(user);
        if (profOpt.isPresent()) {
            return createProfessorInfo(profOpt.get());
        }

        return new AuthResponse.UserInfo(
                user.getEmail(),
                user.getLast_name(),
                user.getFirst_name(),
                null,
                groups,
                misc);
    }

    private AuthResponse.UserInfo createProfessorInfo(ProfesseurModel professor) {
        UserModel user = professor.getUser();
        List<String> groups = user.getUserGroup() != null ? user.getUserGroup().getGroups().stream()
                .map(AuthGroupModel::getNom)
                .collect(Collectors.toList()) : Collections.emptyList();

        Map<String, Object> misc = new HashMap<>();
        misc.put("grade", professor.getGrade());
        misc.put("nombreProposer", professor.getNombreProposer());
        misc.put("nombreEncadre", professor.getNombreEncadre());
        misc.put("etablissement", professor.getEtablissement().getIdEtablissement());
        misc.put("labo", professor.getLabo_id());

        return new AuthResponse.UserInfo(
                user.getEmail(),
                user.getLast_name(),
                user.getFirst_name(),
                professor.getPathPhoto(),
                groups,
                misc);
    }

    private AuthResponse.UserInfo createCandidatInfo(CandidatModel candidat) {
        UserModel user = candidat.getUser();
        List<String> groups = user.getUserGroup() != null ? user.getUserGroup().getGroups().stream()
                .map(AuthGroupModel::getNom)
                .collect(Collectors.toList()) : Collections.emptyList();

        Map<String, Object> misc = new HashMap<>();
        misc.put("cne", candidat.getCne());
        misc.put("cin", candidat.getCni());
        misc.put("ville", candidat.getVille());
        misc.put("pays", candidat.getPays() != null ? candidat.getPays().getId() : null);

        return new AuthResponse.UserInfo(
                user.getEmail(),
                user.getLast_name(),
                user.getFirst_name(),
                candidat.getPathPhoto(),
                groups,
                misc);
    }

    @PostMapping("/verify-is-prof")
    public ResponseEntity<?> verifyProfessor(@RequestBody Map<String, String> request) {
        try {
            String idTokenString = request.get("idToken");
            GoogleIdToken.Payload payload = verifyGoogleToken(idTokenString);
            String email = payload.getEmail();

            ProfesseurModel professor = professeurRepository.findByUserEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Not a professor"));

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateToken(Map.of("tokenType", "refresh"), userDetails);

            return ResponseEntity.ok(new AuthResponse(token, refreshToken, createProfessorInfo(professor)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Check if user exists
            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // Create user
            UserModel user = new UserModel();
            user.setEmail(request.getEmail());
            user.setUsername(request.getEmail()); // Use email as username
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIs_active(true);
            user.setDate_joined(new Date());
            user.setFirst_name(request.getPrenom());
            user.setLast_name(request.getNom());
            
            // Save user first
            user = userRepository.save(user);
            System.out.println("User registered with email: " + user.getEmail());

            // Create candidat
            CandidatModel candidat = new CandidatModel();
            candidat.setUser(user);
            candidat.setCne(request.getCne());
            candidat.setCni(request.getCin());
            candidat.setNomCandidatAr(request.getNomCandidatAr());
            candidat.setPrenomCandidatAr(request.getPrenomCandidatAr());
            candidat.setAdresse(request.getAdresse());
            candidat.setSexe(request.getSexe());
            candidat.setVilleDeNaissance(request.getVilleDeNaissance());
            candidat.setVilleDeNaissanceAr(request.getVilleDeNaissanceAr());
            candidat.setVille(request.getVille());
            candidat.setDateDeNaissance(request.getDateDeNaissance());
            candidat.setTypeDeHandicape(request.getTypeDeHandiCape());
            candidat.setSituation_familiale(request.getSituationfamiliale());

            if (request.getPays() != null) {
                PaysModel pays = paysRepository.findById(request.getPays())
                        .orElseThrow(() -> new RuntimeException("Pays not found"));
                candidat.setPays(pays);
            }

            candidat = candidatRepository.save(candidat);

            // Generate tokens
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateToken(Map.of("tokenType", "refresh"), userDetails);

            return ResponseEntity.ok(new AuthResponse(token, refreshToken, createCandidatInfo(candidat)));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedRoute(HttpServletRequest request) {
        // Get authenticated user from request attribute
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        // Use user object
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/get-user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();
        return ResponseEntity.ok(createUserInfo(user));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}