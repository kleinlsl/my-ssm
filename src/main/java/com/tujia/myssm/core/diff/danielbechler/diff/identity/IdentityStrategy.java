package com.tujia.myssm.core.diff.danielbechler.diff.identity;

import com.tujia.myssm.core.diff.danielbechler.diff.differ.CollectionDiffer;

/**
 * Allows to configure the way objects identities are established when comparing
 * collections via {@linkplain CollectionDiffer}.
 */
public interface IdentityStrategy {
    /**
     * TODO Contract: {@linkplain IdentityStrategy#equals(Object working, Object base)} must always be <code>true</code>
     * when <code>working == base</code>
     *
     * @param working
     * @param base
     * @return
     */
    boolean equals(Object working, Object base);
}
