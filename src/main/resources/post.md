# Custom Basic Authentication with Spring Security

In this article, we will:
* Create a simple web application
* Secure with Custom Basic Authentication (username/password)
* Will use in-memory user store to store users

## 1. Add Spring Web and Spring Security starter dependencies

Let's start by adding Spring Boot starter dependencies for Spring Web and Spring Security
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.3.1.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>2.3.1.RELEASE</version>
</dependency>
```
## 2. Bootstrap a Simple Web Application

```
@SpringBootApplication
public class CustomBasicAuthApp {

  public static void main(String[] args) {
      SpringApplication.run(CustomBasicAuthApp.class, args);
  }
}
```

## 3. Provide custom ``WebSecurityConfig``

To kick in custom authentication behaviour, Spring Security expects one bean (@Bean or @Component) that extends ``WebSecurityConfigurerAdapter`` class.

Therefore, we will create ``WebSecurityConfig`` class that extends ``WebSecurityConfigurerAdapter``.

Here we describe two things for Spring Securities' consideration:
1. Override ``configure(HttpSecurity http)`` method:
    * Allows us to assert that we want to use custom basic authentication
    * Also, presents opportunity to secure all or any number of specific endpoints/pattern with designated roles, if any.
2. Override ``configure(AuthenticationManagerBuilder auth)`` method:
    * Here we provide custom ``AuthenticationProvider`` that is responsible for performing actual authentication
    * ``CustomAuthenticationProvider`` class provides minimalistic implementation for custom authentication

```
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/app").hasRole("USER")
                .antMatchers("/root").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

}
```
    
## 4. Custom ``AuthenticationProvider`` for performing authentication

``CustomAuthenticationProvider`` provides basic implementation of ``AuthenticationProvider``.

It observes two responsibilities:

* ``authenticate(Authentication authentication)`` method provides in-memory implementation of username/password authentication
    * Firstly, it delegates username/password verification to ``UserRepository`` class that serves as in memory store to user 
    database and returns ``User`` object, if verification is successful
    * Secondly, creates instance of ``UsernamePasswordAuthenticationToken`` by passing username/password and user's roles for further filter chain.
    This is how Spring Security will run matchers (``antMatchers(..)``) against assigned roles.

```
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    public CustomAuthenticationProvider() {
        this.userRepository = new InMemoryUserRepository();
    }

    /**
     * First, authenticate user w/ username/password
     * Secondly, check roles
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> user = userRepository.get(authentication);
        if(!user.isPresent()) throw new BadCredentialsException("Invalid credentials");
        return new UsernamePasswordAuthenticationToken(
            authentication.getName(), 
            authentication.getCredentials().toString(), 
            user.get().getAuthorities());
    }

    /**
     * Tells what type of authentication is supported
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
```
    
UserRepository - Provides in-memory store for user objects

```
public interface UserRepository {
    Optional<User> get(Authentication authentication);
}

public class InMemoryUserRepository implements UserRepository {

    private Map<String, User> userStore = new ConcurrentHashMap<>();

    public InMemoryUserRepository() {
        createDefaultUsers();
    }

    @Override
    public Optional<User> get(Authentication authentication) {
        if(userStore.containsKey(authentication.getName()) 
            && userStore.get(authentication.getName()).getPassword().equals(authentication.getCredentials().toString())) {
            return Optional.of(userStore.get(authentication.getName()));
        }
        return Optional.empty();
    }

    private void createDefaultUsers() {
        List<GrantedAuthority> grantedRoles = new ArrayList<>();
        grantedRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
        userStore.put("user", new User("user", "user-secret", grantedRoles));
        grantedRoles.clear();
        grantedRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedRoles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userStore.put("root", new User("root", "root-secret", grantedRoles));
    }
}
```
    
## 5. Testing

### 5.1 Testing /* endpoints

```
curl -i "http://localhost:8080"

HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 27
Date: Fri, 10 Jul 2020 16:22:16 GMT

Let's do custom basic auth.
```
    
### 5.2 Testing /app endpoints - Unauthorized

```
curl -i -u user:forgot "http://localhost:8080/app"

HTTP/1.1 401
WWW-Authenticate: Basic realm="Realm"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 10 Jul 2020 16:24:25 GMT

{"timestamp":"2020-07-10T16:24:25.259+00:00","status":401,"error":"Unauthorized","message":"","path":"/app"}
```

### 5.3 Testing /app endpoints - Access allowed (user: user)

```
curl -i -u user:user-secret "http://localhost:8080/app"

HTTP/1.1 200
Set-Cookie: JSESSIONID=1207332FB11CE683DFA1D650868000C7; Path=/; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 30
Date: Fri, 10 Jul 2020 16:25:36 GMT

You invoked /app successfully.
```

### 5.4 Testing /app endpoints - Access allowed (user: root)

```
curl -i -u root:root-secret "http://localhost:8080/app"

HTTP/1.1 200
Set-Cookie: JSESSIONID=26D5A1A0D73A28984E19E70C53113DB6; Path=/; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 30
Date: Fri, 10 Jul 2020 16:26:53 GMT

You invoked /app successfully.
```

### 5.5 Testing /root endpoints - Unauthorized (user: user)

```
curl -i -u user:user-secret "http://localhost:8080/root"

HTTP/1.1 403
Set-Cookie: JSESSIONID=BFB8EFCED4C67BB359900A683658BADC; Path=/; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 10 Jul 2020 16:28:31 GMT

{"timestamp":"2020-07-10T16:28:31.464+00:00","status":403,"error":"Forbidden","message":"","path":"/root"}
```

### 5.6 Testing /root endpoints - Access allowed (user: root)

```
curl -i -u root:root-secret "http://localhost:8080/root"

HTTP/1.1 200
Set-Cookie: JSESSIONID=215BD707B2DFCF6CC4D183E6C73C0630; Path=/; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 31
Date: Fri, 10 Jul 2020 16:29:26 GMT

You invoked /root successfully.
```