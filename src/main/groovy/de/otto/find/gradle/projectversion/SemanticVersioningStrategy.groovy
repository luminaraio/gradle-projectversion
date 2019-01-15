package de.otto.find.gradle.projectversion

import org.gradle.internal.Cast

class SemanticVersioningStrategy implements VersioningStrategy<SemanticVersion> {

    final String branch
    final boolean dirty

    SemanticVersioningStrategy(String branch, boolean dirty) {
        this.branch = branch
        this.dirty = dirty
    }

    static VersioningStrategy<SemanticVersion> semanticVersioningStrategy(GitCommit gitCommit) {
        new SemanticVersioningStrategy(
                gitCommit.branch,
                gitCommit.dirty)
    }

    @Override
    SemanticVersion nextVersion(SemanticVersion oldVersion, VersioningStrategyOptions options) {
        def semanticVersioningOptions = Cast.<SemanticVersioningOptions> uncheckedCast(options)
        return branch == null || branch.isEmpty() || semanticVersioningOptions.defaultBranch == branch ?
                oldVersion
                        .nextMinor()
                        .withMinimumMajor(semanticVersioningOptions.minimumMajorVersion)
                        .withReleased(!dirty) :
                oldVersion
                        .nextPatch()
                        .withReleased(!dirty)
    }
}
