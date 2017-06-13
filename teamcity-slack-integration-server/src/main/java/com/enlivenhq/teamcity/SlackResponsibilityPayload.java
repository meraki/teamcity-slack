package com.enlivenhq.teamcity;
import jetbrains.buildServer.web.util.WebUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import java.util.*;

public class SlackResponsibilityPayload extends SlackPayload {
    public SlackResponsibilityPayload(ProjectEntry project, Collection<TestEntry> testEntries, @Nullable String fromUser, String toUser, String byUser, String serverUrl) {
        String projectName = escape(project.getName());
        toUser = escape(toUser);
        byUser = escape(byUser);

        Boolean plural = testEntries.size() != 1;

        String statusText = testEntries.size() + " test" + (plural ? "s" : "") + " assigned to @" + toUser + " by " + byUser;
        if (fromUser != null) {
            statusText += " (formerly assigned to " + escape(fromUser) + ")";
        }
        statusText += ":";

        String baseProjectUrl = serverUrl + "/project.html?projectId=" + WebUtil.escapeUrlForQuotes(project.getId());
        List<String> testNameList = new ArrayList<String>();
        for (TestEntry e : testEntries) {
            testNameList.add(e.getTestName().getShortName());
        }
        String testNames = StringUtils.join(testNameList.toArray(), ", ");

        List<String> testLinks = new ArrayList<String>();
        for (TestEntry t : testEntries) {
            String name = escape(t.getTestName().getShortName());
            if (t.getId() != null) {
                String id = t.getId().toString();
                testLinks.add("<" + baseProjectUrl + "&tab=testDetails&testNameId=" + id + "|" + name + ">");
            } else {
                testLinks.add(name);
            }
        }
        String testAttachmentText = StringUtils.join(testLinks.toArray(), "\\n");

        String payloadText = projectName + ": " + statusText;
        this.text = payloadText;

        Attachment attachment = new Attachment();
        attachment.color = "warning";
        attachment.pretext = "Please investigate the following:";
        attachment.fallback = payloadText + "\\n" + testNames;
        attachment.fields = new ArrayList<AttachmentField>();

        AttachmentField attachmentProject = new AttachmentField("Project", projectName, false);
        AttachmentField attachmentTests = new AttachmentField("Tests", testAttachmentText, false);

        attachment.fields.add(attachmentProject);
        attachment.fields.add(attachmentTests);

        this._attachments = new ArrayList<Attachment>();
        this._attachments.add(0, attachment);

        if (this.useAttachments) {
            attachments = _attachments;
        }
    }
}