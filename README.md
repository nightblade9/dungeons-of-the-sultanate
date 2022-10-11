# Dungeons of the Sultanate

[![JEE CI](https://github.com/nightblade9/dungeons-of-the-sultanate/actions/workflows/ci.yml/badge.svg)](https://github.com/nightblade9/dungeons-of-the-sultanate/actions/workflows/ci.yml)

Browser-based roguelike built in JEE and friends.

# Technical Design

- Designed as a cluster of independent microservices, with a web application that provides a UI.
- Each service has its own separate code-base and data
- DTOs provide (with partial duplication) abstraction for inter-service communication
- Services authenticate each other via a shared secret

# Development Environment Setup

- Install MongoDB and PostgresQL (latest versions)
- Open IDEA > New project > repository root
- Click "project" (top-left of IDE) and choose Project Files; then, for each project, find the `build.gradle` under the root of each project, right-click, and pick `Link Gradle project`. Wait for IDEA to process the prjects ("Gradle" window on the right side, the two-way arrow circle icon should be white, not grey)
- Add all the build.gradle files one by one

## Configurating OAuth2

You need to configure OAuth2. First, create a new `src/main/java/resources/application-dev.yml` file with the required OAuth2 configuration:

```
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: GitHub client ID goes here
            clientSecret: GitHub client secret goes here
```

You can create a new client secret [here](https://github.com/settings/developers) - there doesn't seem to be any way to view existing client secrets.

Run the app, and verify that browsing to `http://localhost:8080` presents you with a link to log in, and you can log in via GitHub.

### Troubleshooting OAuth2

If you get this exception at runtime, check that you have specified the `clientId` and `clientSecret` values correctly in your `application-dev.yml` file:

```json
No qualifying bean of type 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository' available
```

If you get a generic user/password page, check the health-check end-point at [http://localhost:8080/health](http://localhost:8080/health). Look for:

```json
{
  "oauth2_client_id":"e3d19545c58e172e61b1",
  "oauth2_client_secret_set":"true"
}
```

If these are not set to these values, check that `profile` is set to `dev`.  You can also verify this by checking the console logs. Immediately after the Spring ASCII logo, look for something like this (incorrect):

```
c.d.d.DungeonsOfTheSultanateApplication  : No active profile set, falling back to 1 default profile: "default"
```

It should show one of these instead (correct):

```
c.d.d.DungeonsOfTheSultanateApplication  : No active profile set, falling back to 1 default profile "dev"
c.d.d.DungeonsOfTheSultanateApplication  : The following 1 profile is active: "dev"
```

This project uses `System.setProperty` in `main` to set the default spring profile to `dev`. If this isn't working, make sure your `application-dev.yml` has the correct OAuth2 configuration.

If you want to override this for some reason, you can manually specify a run configuration in IntelliJ IDEA: select the Run menu, then Run, edit the existing configuration, and under Environment Variables, specify `spring.profiles.active=dev`.

## Configuring MongoDB

- Install and start mongodb 
  - If you're on Linux, you can grab the binaries from AUR.
  - Start the service with `systemctl start mongodb`. Enable it running at startup via `systemctl enable mongodb`.
- Note that you'll use the default/root user.
- Update `application-dev.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://127.0.0.1/dots_web
      database: dots_web
```

Log in and authenticate via an OAuth2 provider; if you don't get any exceptions, MongoDB is working as expected.

## Configuring PostgresQL

I hope you installed PostgresSQL 14. Anyway, open up each microservice's `application.yml`. If they have PostgresQL/JPA config such as:

```
spring:
  datasource:
    #username: ...
    #password: ...
```

Copy/paste the settings to `application-dev.yml`, and specify the PostgresQL user name and password.

## Run the microservices

- In the `Gradle` window on the right, for both projects, run `application` > `bootRun`.  Both should run.
- In the bottom-right of the IDE, click Services. You should see both services running.

## Inter-Service Communication

Make sure all services have a `dots.serviceToService.secret` value specified in `application-dev.yml`. For development:

```yaml
dots:
  serviceToService:
    secret: Super Sekrit Dev Value!
```

# Architecture

## Technologies

Everything is done on the server-side; we use:

- MongoDB
- PostgresQL via JPA
- Spring MVC
- Spring Boot
- ThymeLeaf (UI)

## Design

We try to follow Jeffery Palermo's onion architecture. You can read about it [here](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/) (part one of four).
