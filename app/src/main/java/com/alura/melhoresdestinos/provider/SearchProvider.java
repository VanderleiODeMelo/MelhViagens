package com.alura.melhoresdestinos.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.alura.melhoresdestinos.provider.SearchProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;


    public SearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
