# Free Grind reCAPTCHA helper

An Android helper add-on for [Free Grind](https://github.com/fdev8974/free-grind) that mints reCAPTCHA Enterprise tokens on demand for Grindr's API. Based on [Open Grind reCAPTCHA helper](https://git.opengrind.org/open-grind/recaptcha-helper).

Free Grind asks it for a token when Grindr wants a captcha, for example when signing in to an account that has first-party reCAPTCHA enabled, or when reporting a profile.

Requires Android >= 9, Google Play services and the Grindr app installed. Tokens minted on Android emulators are usually rejected.

The add-on depends on Google's proprietary [reCAPTCHA Enterprise SDK](https://cloud.google.com/recaptcha/docs/instrument-android-apps) (`com.google.android.recaptcha:recaptcha`).

[**Download from Releases**](https://github.com/fdev8974/free-grind--recaptcha-helper/releases)

Free Grind and this helper only talk to each other when both are signed with the same key (Free Grind's release keystore, see [BUILDING.md](./BUILDING.md#signing)).

## Building

See [BUILDING.md](./BUILDING.md).

## License

[MIT](./LICENSE)
