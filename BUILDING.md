# Building

First read [BUILDING.md in Open Grind repository](https://git.opengrind.org/open-grind/open-grind/src/branch/main/BUILDING.md) for basics.

The APK has no native libraries, so one universal APK runs on every device.

## Prerequisites

Clone repository:

```bash
git clone https://github.com/fdev8974/free-grind--recaptcha-helper.git
```

## Build with Docker (easiest, Linux x86_64 only)

```bash
docker compose build
docker compose run --rm build
# -> app/build/outputs/apk/release/app-release-unsigned.apk
```

### Clean up Docker

```bash
docker compose down -v
```

## Build with Nix (builds everywhere)

```bash
nix run .#build-android
# -> app/build/outputs/apk/release/app-release-unsigned.apk
```

## Build manually (advanced)

Prerequisites:

- JDK 17
- Android SDK: platform 36 and build-tools 36.0.0

`./gradlew` pins Gradle 9.4.1, so you don't need Gradle installed.

```bash
./gradlew :app:assembleRelease
# -> app/build/outputs/apk/release/app-release-unsigned.apk
```

Unit tests:

```bash
./gradlew :app:testDebugUnitTest
```

## Signing

Free Grind and this helper trust each other only when both are signed with the same key:

- The helper answers only Free Grind builds whose certificate SHA-256 is listed in `SIGNING_CERTS_SHA256` in [FreeGrindTrust.kt](./app/src/main/java/dev/estopia/free_grind/recaptcha/FreeGrindTrust.kt): the GitHub Actions release key and the local `src-tauri/certs/free-grind-dev.jks`.
- Free Grind uses only a `dev.estopia.free_grind.recaptcha` install whose signature matches its own.

So sign the helper with the keystore that signed the Free Grind build you use. Copy [contrib/keystore.properties.example](./contrib/keystore.properties.example) and point it at that keystore:

```bash
RECAPTCHA_HELPER_KEYSTORE_PROPERTIES=/home/you/.config/free-grind/keystore.properties \
  nix run .#build-android
```

## Publishing a release

Every push to `main` runs [release-main.yml](./.github/workflows/release-main.yml): it runs the unit tests, builds and signs the APK, checks the certificate with `contrib/sign-release.sh` and publishes a release tagged `main-<sha>`. The repository needs these secrets:

- `ANDROID_KEYSTORE_BASE64`: the same value as in the free-grind repository
- `ANDROID_KEYSTORE_PASSWORD` (optional): only needed if the keystore password is not `opengrind`, the one Free Grind's `app/build.gradle.kts` uses

## Verifying a release

```bash
apksigner verify --print-certs free-grind-recaptcha-helper-<tag>-android.apk
```

The certificate SHA-256 must be `7ce9e5cdac620ec798bea6749aed4c6d59bf12ab5e1dc8cb59c117bb929fc9d0`.

## Reproducibility

Follow [Open Grind's REPRODUCIBILITY.md](https://git.opengrind.org/open-grind/open-grind/src/branch/main/REPRODUCIBILITY.md).

| Component                                              | Pinned in                                  |
| ------------------------------------------------------ | ------------------------------------------ |
| Gradle distribution (+ SHA-256)                        | `gradle/wrapper/gradle-wrapper.properties` |
| Android Gradle Plugin, reCAPTCHA Enterprise SDK, JUnit | `gradle/libs.versions.toml`                |
| compileSdk / minSdk / targetSdk, build-tools           | `app/build.gradle.kts`                     |
| JDK + Android SDK (Nix build)                          | `flake.nix`                                |
| nixpkgs revision                                       | `flake.lock`                               |
