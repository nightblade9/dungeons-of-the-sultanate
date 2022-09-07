# Dungeons of the Sultanate

[![JEE CI](https://github.com/nightblade9/dungeons-of-the-sultanate/actions/workflows/ci.yml/badge.svg)](https://github.com/nightblade9/dungeons-of-the-sultanate/actions/workflows/ci.yml)

Browser-based roguelike built in JEE and friends.

# Development Environment Setup

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

Then, configure your run task in IDEA by picking Run > Run, edit the existing configuration, and under Environment Variables, specify `spring.profiles.active=dev`.

Run the app, and verify that browsing to `http://localhost:8080` presents you with a link to log in, and you can log in via GitHub.

## Troubleshooting OAuth2

If you get a generic user/password page, check the console logs. Immediately after the Spring ASCII logo, oook for something like this (incorrect):

```
c.d.d.DungeonsOfTheSultanateApplication  : No active profile set, falling back to 1 default profile: "default"
```

It should show something like this (correct) if you've set the environment variable correctly in the run configuration:

```
c.d.d.DungeonsOfTheSultanateApplication  : The following 1 profile is active: "dev"
```