//package com.EChowk.EChowk.Config;
//
//import com.EChowk.EChowk.Config.JwtAuthFilter;
//import com.EChowk.EChowk.Service.CustomUserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//    private final JwtAuthFilter jwtAuthFilter;
//    private final CustomUserDetailsService userDetailsService;
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable()) // ✅ Spring Security 6.1+ syntax
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/auth/**",
////                                "/users/register",
////                                "/offers/**",
////                                "/requests/**").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .sessionManagement(session -> session
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                )
////                .authenticationProvider(authenticationProvider())
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http
////                .csrf(AbstractHttpConfigurer::disable)
////                .cors(cors -> cors.configurationSource(request -> {
////                    CorsConfiguration config = new CorsConfiguration();
////                    config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173")); // Next.js and React frontend URLs
////                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////                    config.setAllowedHeaders(List.of("*"));
////                    config.setAllowCredentials(true);
////                    return config;
////                }))
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/auth/**").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authenticationProvider(authenticationProvider())
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
////                .build();
////    }
////@Bean
////public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////    return http
////            .csrf(AbstractHttpConfigurer::disable)
////            .cors(cors -> cors.configurationSource(request -> {
////                CorsConfiguration config = new CorsConfiguration();
////                config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
////                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////                config.setAllowedHeaders(List.of("*"));
////                config.setAllowCredentials(true);
////                return config;
////            }))
////            .authorizeHttpRequests(auth -> auth
////                    .requestMatchers(
////                            "/auth/**",
////                            "/users/register",
////                            "/health",
////                            "/skills/**",
////                            "/skilloffers/**",
////                            "/requests/**"
////                    ).permitAll()
////                    .anyRequest().authenticated()
////            )
////            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////            .authenticationProvider(authenticationProvider())
////            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
////            .build();
////}
//    @Autowired FirebaseAuthFilter firebaseAuthFilter;
//    @Value("${auth.use-firebase:false}")
//    private boolean usefirebase;
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//            .csrf(AbstractHttpConfigurer::disable)
//            .cors(cors -> cors.configurationSource(request -> {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
//                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                config.setAllowedHeaders(List.of("*"));
//                config.setAllowCredentials(true);
//                return config;
//            }))
//            .authorizeHttpRequests(auth -> auth
//                    // Publicly accessible endpoints
//                    .requestMatchers(
//                            "/auth/**",
//                            "/users/register",
//                            "/health/**",
//                            "/swagger-ui/**",
//                            "/v3/api-docs/"
//                    ).permitAll()
//
//                    // Endpoints accessible to USER role
//                    .requestMatchers(
//                            "/skills/**",
//                            "/offers/**",
//                            "/requests/**",
//                            "/reviews/**",
//                            "/users/upload-profile-picture"
//                    ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")  // ✅ Allow USER & ADMIN
//
//                    // Admin-specific endpoints
//                    .requestMatchers("/admin/").hasAuthority("ROLE_ADMIN")
//
//                    // All other endpoints need authentication
//                    .anyRequest().authenticated()
//            )
//            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authenticationProvider(authenticationProvider())
//            //firebase --> JWT
//            .addFilterBefore(firebaseAuthFilter,UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//            .build();
//}
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
//            throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
package com.EChowk.EChowk.Config;

import com.EChowk.EChowk.Config.JwtAuthFilter;
import com.EChowk.EChowk.Config.FirebaseAuthFilter;
import com.EChowk.EChowk.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final FirebaseAuthFilter firebaseAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Value("${auth.use-firebase:false}")
    private boolean useFirebase;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        // Publicly accessible endpoints
                        .requestMatchers(
                                "/auth/**",
                                "/users/register",
                                "/health/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Endpoints accessible to USER role
                        .requestMatchers(
                                "/skills/**",
                                "/offers/**",
                                "/requests/**",
                                "/reviews/**",
                                "/users/upload-profile-picture"
                        ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // Admin-specific endpoints
                        .requestMatchers("/admin/").hasAuthority("ROLE_ADMIN")

                        // All other endpoints need authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider());

        // 🔁 Conditionally add Firebase filter if enabled
        if (useFirebase) {
            httpSecurity.addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class);
        }

        // Always add JWT filter
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}