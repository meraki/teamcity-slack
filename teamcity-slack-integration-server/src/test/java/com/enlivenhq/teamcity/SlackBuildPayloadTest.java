package com.enlivenhq.teamcity;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SlackBuildPayloadTest {

    String project = "project<http://example.com|lol>";
    String build = "build<http://example.com|lol>";
    String branch = "<http://example.com|lol>";
    String statusText = "<status>";
    String statusTextMultiline = "<status\n>";
    String statusColor = "color";
    String btId = "btId<http://example.com|lol>";
    long buildId = 0;
    String serverUrl = "localhost";
    String channel = "#channel";
    String username = "bot";
    SlackBuildPayload slackBuildPayload;

    private String escape(String s) {
        return s
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        slackBuildPayload = null;
    }

    @Test
    public void testSlackPayloadDoesNotRequiresUserAndChannel() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload == null);
    }

    @Test
    public void testSlackPayloadWithoutAttachment() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        slackBuildPayload.setUseAttachments(false);
        assertFalse(slackBuildPayload.hasAttachments());
    }

    @Test
    public void testSlackPayloadUsesAttachmentByDefault() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertTrue(slackBuildPayload.hasAttachments());
    }

    @Test
    public void testSlackPayloadIsUpdatedWithUsername() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        slackBuildPayload.setUseAttachments(false);
        slackBuildPayload.setUsername(username);
        assertTrue(slackBuildPayload.getUsername().equals(username));
    }

    @org.testng.annotations.Test
    public void testSlackPayloadIsUpdatedWithChannel() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        slackBuildPayload.setUseAttachments(false);
        slackBuildPayload.setChannel(channel);
        assertTrue(slackBuildPayload.getChannel().equals(channel));
    }

    @Test
    public void testSlackPayloadProjectEscapeLtGt() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains(project));
        assertTrue(slackBuildPayload.getText().contains(escape(project)));
    }

    @Test
    public void testSlackPayloadBuildEscapeLtGt() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains(build));
        assertTrue(slackBuildPayload.getText().contains(escape(build)));
    }

    @Test
    public void testSlackPayloadBranchEscapeLtGt() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains(branch));
        assertTrue(slackBuildPayload.getText().contains(escape(branch)));
    }

    @Test
    public void testSlackPayloadStatusTextEscapeLtGt() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains(statusText));
        assertTrue(slackBuildPayload.getText().contains(escape(statusText)));
    }

    @Test
    public void testSlackPayloadBtIdEscapeLtGt() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusText, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains(btId));
        assertTrue(slackBuildPayload.getText().contains(escape(btId)));
    }

    @Test
    public void testSlackPayloadStatusEscapeNewline() {
        slackBuildPayload = new SlackBuildPayload(project, build, branch, statusTextMultiline, statusColor, btId, buildId, serverUrl);
        assertFalse(slackBuildPayload.getText().contains("\n"));
        assertTrue(slackBuildPayload.getText().contains("\\n"));
    }

}