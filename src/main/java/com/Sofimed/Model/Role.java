package com.Sofimed.Model;

public enum Role {
    ADMIN("Administrateur"),
    COMMERCIAL("Commercial"),
    CLIENT("Client");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}