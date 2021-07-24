# spring-security-memory
Spring security against Authentication Manager in memeory



## Handling Security Exception

![image](https://user-images.githubusercontent.com/17804600/126819181-0368e849-8ad4-43d5-be6a-23ed91b22dce.png)


## Authentication

If the application does not throw an AccessDeniedException or an AuthenticationException, then ExceptionTranslationFilter does not do anything.

Architecture Components

This section describes the main architectural components of Spring Security’s used in Servlet authentication. If you need concrete flows that explain how these pieces fit together, look at the Authentication Mechanism specific sections.

* SecurityContextHolder - The SecurityContextHolder is where Spring Security stores the details of who is authenticated.

* SecurityContext - is obtained from the SecurityContextHolder and contains the Authentication of the currently authenticated user.

* Authentication - Can be the input to AuthenticationManager to provide the credentials a user has provided to authenticate or the current user from the SecurityContext.

* GrantedAuthority - An authority that is granted to the principal on the Authentication (i.e. roles, scopes, etc.)

* **AuthenticationManager - the API that defines how Spring Security’s Filters perform authentication**.

* ProviderManager - the most common implementation of AuthenticationManager.

* AuthenticationProvider - used by ProviderManager to perform a specific type of authentication.

* Request Credentials with AuthenticationEntryPoint - used for requesting credentials from a client (i.e. redirecting to a log in page, sending a WWW-Authenticate response, etc.)

AbstractAuthenticationProcessingFilter - a base Filter used for authentication. This also gives a good idea of the high level flow of authentication and how pieces work together.

### SecurityContextHolder

![image](https://user-images.githubusercontent.com/17804600/126822292-dfec4f4a-1972-4244-a76a-91e1f09702bd.png)

he SecurityContextHolder is where Spring Security stores the details of who is authenticated. Spring Security does not care how the SecurityContextHolder is populated. If it contains a value, then it is used as the currently authenticated user.

The simplest way to indicate a user is authenticated is to set the SecurityContextHolder directly.

````
SecurityContext context = SecurityContextHolder.createEmptyContext(); 
Authentication authentication =
    new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
context.setAuthentication(authentication);

SecurityContextHolder.setContext(context); 
````
> We start by creating an empty SecurityContext.
It is important to create a new SecurityContext instance instead of using SecurityContextHolder.getContext().setAuthentication(authentication) to avoid race conditions across multiple threads.
Next we create a new Authentication object.
Spring Security does not care what type of Authentication implementation is set on the SecurityContext.
Here we use TestingAuthenticationToken because it is very simple.
A more common production scenario is UsernamePasswordAuthenticationToken(userDetails, password, authorities).
Finally, we set the SecurityContext on the SecurityContextHolder.
Spring Security will use this information for authorization.

By default the SecurityContextHolder uses a ThreadLocal to store these details, which means that the SecurityContext is always available to methods in the same thread, even if the SecurityContext is not explicitly passed around as an argument to those methods. Using a ThreadLocal in this way is quite safe if care is taken to clear the thread after the present principal’s request is processed. Spring Security’s FilterChainProxy ensures that the SecurityContext is always cleared.

Some applications aren’t entirely suitable for using a ThreadLocal, because of the specific way they work with threads. For example, a Swing client might want all threads in a Java Virtual Machine to use the same security context. SecurityContextHolder can be configured with a strategy on startup to specify how you would like the context to be stored. For a standalone application you would use the SecurityContextHolder.MODE_GLOBAL strategy. Other applications might want to have threads spawned by the secure thread also assume the same security identity. This is achieved by using SecurityContextHolder.MODE_INHERITABLETHREADLOCAL. You can change the mode from the default SecurityContextHolder.MODE_THREADLOCAL in two ways. The first is to set a system property, the second is to call a static method on SecurityContextHolder. Most applications won’t need to change from the default, but if you do, take a look at the JavaDoc for SecurityContextHolder to learn more.

### AuthenticationManager
ProviderManager is the most commonly used implementation of AuthenticationManager. ProviderManager delegates to a List of AuthenticationProviders. Each AuthenticationProvider has an opportunity to indicate that authentication should be successful, fail, or indicate it cannot make a decision and allow a downstream AuthenticationProvider to decide. If none of the configured AuthenticationProviders can authenticate, then authentication will fail with a ProviderNotFoundException which is a special AuthenticationException that indicates the ProviderManager was not configured to support the type of Authentication that was passed into it.

AuthenticationManager is an interface, which is supposed to process an Authentication request. 

#### ProviderManager
the most imple of AuthenticationManager is ProviderManager, which manages a list of AuthenticationProviders, each of them may fails or sucesses the authentication. 
![image](https://user-images.githubusercontent.com/17804600/126830820-10333d23-7764-47b6-93c8-eb3d3dcc36a2.png)

