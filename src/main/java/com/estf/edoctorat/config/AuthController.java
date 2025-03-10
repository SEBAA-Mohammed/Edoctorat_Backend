package com.estf.edoctorat.config;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estf.edoctorat.dto.LoginRequest;
import com.estf.edoctorat.dto.PreRegisterRequest;
import com.estf.edoctorat.models.AuthGroupModel;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.TokenConfirmation;
import com.estf.edoctorat.models.UserGroupModel;
import com.estf.edoctorat.models.UserModel;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import com.estf.edoctorat.repositories.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication", description = "Authentication management APIs")
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
    private CandidatRepository candidatRepository;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private TokenConfirmationRepository tokenConfirmationRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        return googleTokenVerifier.verify(idTokenString);
    }

    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns JWT tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/token/")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        try {
            UserModel user = userRepository.findByEmail(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateToken(Map.of("tokenType", "refresh"), userDetails);

            // Set JWT cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            jwtCookie.setAttribute("SameSite", "Lax");
            response.addCookie(jwtCookie);
            response.setHeader("Authorization", "Bearer " + token);

            return ResponseEntity.ok()
                    .body(new AuthResponse(token, refreshToken, createUserInfo(user)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Operation(summary = "Logout user", description = "Invalidates the user's JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out"),
    })
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

        Optional<CandidatModel> candidatOpt = candidatRepository.findByUser(user);
        if (candidatOpt.isPresent()) {
            return createCandidatInfo(candidatOpt.get());
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
                getFullPhotoUrl(professor.getPathPhoto()),
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
                getFullPhotoUrl(candidat.getPathPhoto()),
                groups,
                misc);
    }

    private String getFullPhotoUrl(String pathPhoto) {
        if (pathPhoto == null || pathPhoto.isEmpty()) {
            return null;
        }
        return baseUrl + "/" + pathPhoto;
    }

    @Operation(summary = "Verify professor", description = "Verifies if the user is a professor using Google token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully verified professor"),
            @ApiResponse(responseCode = "401", description = "Not authorized or not a professor")
    })
    @PostMapping("/verify-is-prof/")
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

    @Operation(summary = "Register candidate", description = "Registers a new candidate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered candidate", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Registration failed")
    })
    @PostMapping("/register/candidat/")
    public ResponseEntity<?> registerCandidat(@RequestBody RegisterRequest request) {
        try {
            System.out.println(request);
            // Find or create user
            UserModel user = userRepository.findByEmail(request.getEmail())
                    .map(existingUser -> {
                        existingUser.setIs_active(true);
                        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                        existingUser.setFirst_name(request.getPrenom());
                        existingUser.setLast_name(request.getNom());
                        return existingUser;
                    })
                    .orElseGet(() -> {
                        UserModel newUser = new UserModel();
                        newUser.setEmail(request.getEmail());
                        newUser.setUsername(request.getEmail());
                        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                        newUser.setIs_active(true);
                        newUser.setDate_joined(new Date());
                        newUser.setFirst_name(request.getPrenom());
                        newUser.setLast_name(request.getNom());
                        return newUser;
                    });

            UserGroupModel userGroup = userGroupRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Default candidat group not found"));
            user.setUserGroup(userGroup);

            user = userRepository.save(user);

            PaysModel pays = paysRepository.findByNom(request.getPays())
                    .orElseGet(() -> {
                        PaysModel newPays = new PaysModel();
                        newPays.setNom(request.getPays());
                        return paysRepository.save(newPays);
                    });

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
            candidat.setPays(pays);
            candidat.setFonctionnaire(request.getFonctionnaire() != null ? request.getFonctionnaire() : false);
            candidat.setEtatDossier(0);

            candidat = candidatRepository.save(candidat);

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateToken(Map.of("tokenType", "refresh"), userDetails);

            return ResponseEntity.ok(new AuthResponse(token, refreshToken, createCandidatInfo(candidat)));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Access protected route", description = "Test endpoint for protected routes")
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully accessed protected route"),
            @ApiResponse(responseCode = "401", description = "Not authorized")
    })
    @GetMapping("/protected")
    public ResponseEntity<?> protectedRoute(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user info", description = "Retrieves information about the authenticated user")
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user info"),
            @ApiResponse(responseCode = "401", description = "Not authorized")
    })
    @GetMapping("/get-user-info/")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();
        return ResponseEntity.ok(createUserInfo(user));
    }

    @Operation(summary = "Confirm email", description = "Sends confirmation email to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmation email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to process request")
    })
    @PostMapping("/confirm-email/")
    public ResponseEntity<?> confirmEmail(@RequestBody PreRegisterRequest request) {
        try {

            Optional<UserModel> existingUser = userRepository.findByEmail(request.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Confirmation email sent successfully");
            if (existingUser.isPresent()) {
                UserModel user = existingUser.get();
                user.setFirst_name(request.getPrenom());
                user.setLast_name(request.getNom());
                userRepository.save(user);

                Optional<TokenConfirmation> existingToken = tokenConfirmationRepository.findByUser(user);
                TokenConfirmation tokenConfirmation;

                if (existingToken.isPresent()) {
                    tokenConfirmation = existingToken.get();
                    tokenConfirmation.setToken(UUID.randomUUID().toString());
                    tokenConfirmation.setExpiryDate(
                            Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()));
                } else {
                    tokenConfirmation = new TokenConfirmation();
                    tokenConfirmation.setToken(UUID.randomUUID().toString());
                    tokenConfirmation.setUser(user);
                    tokenConfirmation.setExpiryDate(
                            Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()));
                }
                tokenConfirmationRepository.save(tokenConfirmation);

                String confirmationUrl = request.getOrigin() + "?token=" + tokenConfirmation.getToken();
                sendConfirmationEmail(request.getEmail(), confirmationUrl);

                return ResponseEntity.ok(response);
            } else {
                UserModel user = new UserModel();
                user.setEmail(request.getEmail());
                user.setUsername(request.getEmail());
                user.setFirst_name(request.getPrenom());
                user.setLast_name(request.getNom());
                user.setIs_active(false);
                user.setDate_joined(new Date());
                user = userRepository.save(user);

                String token = UUID.randomUUID().toString();
                Date expiryDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

                TokenConfirmation confirmation = new TokenConfirmation();
                confirmation.setToken(token);
                confirmation.setUser(user);
                confirmation.setExpiryDate(expiryDate);
                confirmation.setConfirmed(false);
                tokenConfirmationRepository.save(confirmation);

                String confirmationUrl = request.getOrigin() + "?token=" + token;
                sendConfirmationEmail(user.getEmail(), confirmationUrl);

                return ResponseEntity.ok()
                        .body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Failed to process request: " + e.getMessage());
        }
    }

    private void sendConfirmationEmail(String email, String confirmationUrl) {
        String emailContent = """
                    <html>
                    <body>
                        <h2>Email Confirmation</h2>
                        <p>Please click the button below to confirm your email address:</p>
                        <a href="%s"
                           style="background-color: #4CAF50;
                                  color: white;
                                  padding: 14px 20px;
                                  text-decoration: none;
                                  border-radius: 4px;">
                            Confirm Email
                        </a>
                        <p>This link will expire in 30 minutes.</p>
                    </body>
                    </html>
                """.formatted(confirmationUrl);

        emailService.sendHtmlEmail(email, "Confirm your email", emailContent);
    }

    @Operation(summary = "Verify token", description = "Verifies email confirmation token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    @PostMapping("/verify-token/")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();

        String token = body.get("token");
        if (token == null) {
            response.put("message", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<TokenConfirmation> confirmationOpt = tokenConfirmationRepository.findValidToken(token);
        if (confirmationOpt.isEmpty()) {
            response.put("message", "Invalid or expired token");
            return ResponseEntity.badRequest().body(response);
        }

        UserModel user = confirmationOpt.get().getUser();
        response.put("nom", user.getLast_name());
        response.put("prenom", user.getFirst_name());
        response.put("email", user.getEmail());
        response.put("message", "Token verified successfully");

        return ResponseEntity.ok(response);
    }

}