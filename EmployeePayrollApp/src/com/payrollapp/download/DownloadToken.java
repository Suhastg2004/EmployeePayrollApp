package com.payrollapp.download;

public class DownloadToken {

    private final long createdTime;
    private final long expiryMillis;

    public DownloadToken() {
        this(60_000L); // 1 minute
    }

    public DownloadToken(long expiryMillis) {
        if (expiryMillis <= 0) {
            throw new IllegalArgumentException("expiryMillis must be > 0");
        }
        this.createdTime = System.currentTimeMillis();
        this.expiryMillis = expiryMillis;
    }

    /**
     * @return true if token has expired; false otherwise
     */
    public boolean isExpired() {
        long now = System.currentTimeMillis();
        return (now - createdTime) > expiryMillis;
    }

    /**
     * @return remaining validity (ms), could be negative if expired
     */
    public long getRemainingMillis() {
        long remaining = expiryMillis - (System.currentTimeMillis() - createdTime);
        return remaining;
    }
}