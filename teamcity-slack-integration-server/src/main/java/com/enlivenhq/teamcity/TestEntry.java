package com.enlivenhq.teamcity;

import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.tests.TestName;

import javax.annotation.Nullable;

public class TestEntry {
    private TestName name;
    private Long id = null;
    public TestEntry(TestNameResponsibilityEntry testEntry) {
        this.name = testEntry.getTestName();
        this.id = testEntry.getTestNameId();
    }

    public TestEntry(TestName testName) {
        this.name = testName;
    }

    public TestName getTestName() { return name; }
    public Long getId() { return id; }
}
