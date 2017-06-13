package com.enlivenhq.teamcity;

import com.google.gson.annotations.Expose;

import java.util.List;

public abstract class SlackPayload {
    @Expose
    protected String text;
    @Expose
    protected String channel;
    @Expose
    protected String username;
    @Expose
    protected List<Attachment> attachments;
    protected List<Attachment> _attachments;


    public String getText() {
        return text;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getChannel() {
        return channel;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    protected class Attachment {
        @Expose
        protected String fallback;
        @Expose
        protected String pretext;
        @Expose
        protected String color;
        @Expose
        protected List<AttachmentField> fields;
    }

    protected class AttachmentField {
        public AttachmentField (String name, String val, boolean isShort) {
            title = name;
            value = val;
            this.isShort = isShort;
        }
        @Expose
        protected String title;
        @Expose
        protected String value;
        @Expose
        protected boolean isShort;
    }

    protected boolean useAttachments = true;
    public void setUseAttachments (boolean useAttachments) {
        this.useAttachments = useAttachments;
        if (!useAttachments) {
            _attachments = attachments;
            attachments = null;
        } else {
            attachments = _attachments;
        }
    }

    public boolean hasAttachments () {
        return attachments != null && attachments.size() > 0;
    }

    protected String escapeNewline(String s) {
        return s.replace("\n", "\\n");
    }

    protected String escape(String s) {
        return s
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
