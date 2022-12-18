package com.tujia.myssm.core.diff.danielbechler.diff.identity;

import com.tujia.myssm.core.diff.danielbechler.util.Objects;

/**
 * Default implementation that uses Object.equals.
 */
public class EqualsIdentityStrategy implements IdentityStrategy {
    private static final EqualsIdentityStrategy instance = new EqualsIdentityStrategy();

    public static EqualsIdentityStrategy getInstance() {
        return instance;
    }

    public boolean equals(final Object working, final Object base) {
        return Objects.isEqual(working, base);
    }
}
