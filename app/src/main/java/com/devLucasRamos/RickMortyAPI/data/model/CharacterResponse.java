package com.devLucasRamos.RickMortyAPI.data.model;

import java.util.List;

public class CharacterResponse {
    private List<com.devLucasRamos.RickMortyAPI.data.model.Character> results;
    private Info info;

    public List<Character> getResults() {
        return results;
    }

    public Info getInfo() {
        return info;
    }

    public static class Info {
        private int count;
        private int pages;
        private String next;
        private String prev;

        public int getCount() {
            return count;
        }

        public int getPages() {
            return pages;
        }

        public String getNext() {
            return next;
        }

        public String getPrev() {
            return prev;
        }
    }
}