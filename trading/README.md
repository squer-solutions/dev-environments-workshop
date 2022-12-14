# Dev Setups

In the greater context of this repository, this is the "target application" that we're trying to optimise the dev-setup for.
It has various dependencies it needs to function properly and it is our job to supply them with various tools.

Additionally, the application goes to showcase how one could use Micronaut with Kotlin Coroutines to arrive at an async/await
style programming model.

## Developing

This repository uses KtLint to support uniform code-styling. It is suggested to use the pre-commit hook delivered with it.
```sh
./gradlew addKtlintFormatGitPreCommitHook
```