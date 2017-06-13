package com.enlivenhq.teamcity;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SlackBuildPayload extends SlackPayload {
    public SlackBuildPayload(String project, String build, String branch, String statusText, String statusColor, String btId, long buildId, String serverUrl) {
        project = escape(project);
        build = escape(build);
        branch = escape(branch);
        statusText = escape(escapeNewline(statusText));
        btId = escape(btId);

        String escapedBranch = branch.length() > 0 ? " [" + branch + "]" : "";

        statusText = "<" + serverUrl + "/viewLog.html?buildId=" + buildId + "&buildTypeId=" + btId + "|" + statusText + ">";

        String statusEmoji = statusColor.equals("danger") ? ":x: " : statusColor.equals("warning") ? "" : ":white_check_mark: ";

        String payloadText = statusEmoji + project + escapedBranch + " #" + build + " " + statusText;
        this.text = payloadText;

        Attachment attachment = new Attachment();
        attachment.color = statusColor;
        attachment.pretext = "Build Status";
        attachment.fallback = payloadText;
        attachment.fields = new ArrayList<AttachmentField>();

        AttachmentField attachmentProject = new AttachmentField("Project", project, false);
        AttachmentField attachmentBuild = new AttachmentField("Build", build, true);
        AttachmentField attachmentStatus = new AttachmentField("Status", statusText, false);
        AttachmentField attachmentBranch;

        attachment.fields.add(attachmentProject);
        attachment.fields.add(attachmentBuild);
        if (branch.length() > 0) {
            attachmentBranch = new AttachmentField("Branch", branch, false);
            attachment.fields.add(attachmentBranch);
        }
        attachment.fields.add(attachmentStatus);

        this._attachments = new ArrayList<Attachment>();
        this._attachments.add(0, attachment);

        if (this.useAttachments) {
            attachments = _attachments;
        }
    }
}