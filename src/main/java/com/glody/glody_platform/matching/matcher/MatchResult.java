package com.glody.glody_platform.matching.matcher;

public class MatchResult {
    private final boolean matched;
    private final String reason;

    private MatchResult(boolean matched, String reason) {
        this.matched = matched;
        this.reason = reason;
    }

    public static MatchResult passed(String reason) {
        return new MatchResult(true, reason);
    }

    public static MatchResult failed(String reason) {
        return new MatchResult(false, reason);
    }

    public boolean isMatched() {
        return matched;
    }

    public String getReason() {
        return reason;
    }
}
