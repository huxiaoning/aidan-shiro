package org.aidan.shiro.enums;

public enum LoginType {

    USER("UserRealm"), ADMIN("AdminRealm");

    private String type;

    LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
