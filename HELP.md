# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web.security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Udeerstand the new Gradle

#### gradle structure

- gradlew
- settings.gradle
    - include multi modules
    - define the root project
- gradlew.bat
- gradle/ folder
    - holds the gradle configuration or wrapper settings

#### gradle changes

- compile and test compile changes to implmentation and test implementation
- to make an implementation available through out the application or global scope change it to api and change plugin
  java to 'java-library'
- it is advised to use kotlin rather than groovy as best practice
- settings.gradle should have multi module defines using include('module1','module2')

### Reading the JWT token
one would need below packages to read the JWT token
```
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
implementation 'io.jsonwebtoken:jjwt-iml:0.11.5'
implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
```

Jwt token contains header.payload.signature
payload is where we have the claims. There are three types of claims:
- registered claims (set of predeifned claims , not mandatory but should be there, issuer, exp  time etc)
- public (defined in the auth service)
- private ( custom claims used for shring information between parties)

#### extracting the claims from token (Set<Claim>)
```
Jwts.parserBuilder().setSigningKey("")
     .build().parseClaimsJws(token)
     .getBody();
```

we can get the encrytion key from EncryptionKeyGenerators online
https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx

#### genratung token
```

private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis()
                        + properties.getAccessTokenValidity().toMillis())
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
```


#### Adding Configuration properties 

As per Spring boot 3.0 We only need to annotate the class with ConfigurationProperties(prefix="")


