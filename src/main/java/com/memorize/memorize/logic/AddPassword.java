package com.memorize.memorize.logic;

import com.memorize.memorize.logic.security.Chiffrement;
import com.memorize.memorize.logic.security.PathManager;

public class AddPassword {
    private String letter;
    private String site;
    private String password;
    private final PathManager pathManager = new PathManager();
    private final Chiffrement chiffrement = new Chiffrement();
    public AddPassword(String letter, String site, String password){
        this.letter = letter;
        this.site = site;
        this.password = password;
    }
    public void addPassword(){




    }
}
