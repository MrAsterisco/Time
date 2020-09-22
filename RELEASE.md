# Release
The project is already configured to publish on a public repository on Bintray. You may want to change the repo, by looking into the `gradle.properties` and changing the `bintrayOrg` and `bintrayRepository` properties.

To release a new version of Time, follow these steps:

- Update the package version according to Semantic Versioning in `gradle.properties`
- Create a `local.properties` file, if you don't have on already, and insert your Bintray username and API key as follows:

```groovy
bintrayUser=yourUsername
bintrayApiKey=yourKey
```

- Run `bintrayUpload`.

## Known Issues
- Publication will only work on macOS, as this library builds for Android and iOS at the same time.
- Publication will **not** publish the JS version of the library.