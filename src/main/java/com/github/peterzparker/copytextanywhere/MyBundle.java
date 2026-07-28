package com.github.peterzparker.copytextanywhere;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class MyBundle {
    @NonNls
    private static final String BUNDLE = "messages.MyBundle";
    private static final InnerBundle INSTANCE = new InnerBundle();

    private MyBundle() {
    }

    @Nls
    public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    private static class InnerBundle extends DynamicBundle {
        InnerBundle() {
            super(BUNDLE);
        }
    }
}
