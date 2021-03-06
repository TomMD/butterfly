# Release steps

1. Working from a <temp branch> (out of develop) in your fork:
   1. Rev up all 12 pom files to the release version
   1. Build and test it
   1. javadoc
      1. generate javadocs for utilities and extensions-api projects: `mvn javadoc:javadoc`
      1. place javadocs files under `docs/javadocs/<version>`
      1. update **THREE** links in `Extension-development-guide.md`, pointing to latest javadoc
      1. commit only javadocs `Adding javadocs for version x`
   1. doc
      1. update zip link (two places in the URL) in `Installing-Butterfly.md`
   1. sample extension
      1. make sure `butterfly.version` in `tests/sample-extension/pom.xml` is updated
      1. place `sample-extension` jar under `docs/jar`
      1. update link in `QUICK_START.md` if jar file name changed
   1. sample app (only if changed)
      1. zip `sample-app` folder
      1. place updated zip under `docs/zip`
      1. update link in `QUICK_START.md` if zip file name changed
   1. Update release notes
   1. Commit `Releasing x`
   1. Push from <temp branch> to origin <temp branch> (`git push origin <temp branch>`)
1. Send and merge PR from origin <temp branch> to upstream develop
1. Send and merge PR from upstream develop to upstream master
1. Tag new release from master
   1. Release title should be the version
   1. Add sections `New Features and enhancements` and `Bug fixes` from release notes to Release description
1. Close milestone
   1. Set is due date to today
1. Deploy artifacts to Maven Central
   1. Go to TravisCI (click on its badge on butterfly repo README)
   1. Click on `More Options -> Trigger Build`
   1. Set `master` as branch
   1. Set `Releasing <version number>` in `CUSTOM COMMIT MESSAGE` field
   1. Copy and paste the content of [.travis_release.yml](.travis_release.yml) in `CUSTOM CONFIG` field
1. Manual sonatype release
   1. Staging Repositories
   1. Close (the one with sources and everything)
   1. Release
1. Update [Homebrew formula](https://github.com/paypal/homebrew-butterfly/blob/master/Formula/butterfly.rb)
   1. Update zip link
   1. Update sha256
   1. Verify brew can update or install new version
1. Working from a <temp branch> (out of develop) in your fork:
   1. Rev up all 12 pom files to the next SNAPSHOT version
   1. Make sure the new Butterfly SNAPSHOT version is set in `sample-extension` pom file
   1. Build `butterlfy-parent` and make sure it builds fine
   1. Add new version empty section in release notes
   1. Commit `Preparing for version x`
   1. Push from <temp branch> to origin <temp branch> (`git push origin <temp branch>`)
   1. Send and merge PR from origin <temp branch> to upstream develop
1. Create new milestone
1. Add issues to new milestone (if any)