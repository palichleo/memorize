package com.memorize.memorize.logic;

import com.memorize.memorize.logic.security.Encryption;
import com.memorize.memorize.logic.security.PathManager;

public class AddPassword {
    private String letter;
    private String site;
    private String password;
    private final PathManager pathManager = new PathManager();
    private final Encryption chiffrement = new Encryption();
    public AddPassword(String letter, String site, String password){
        this.letter = letter;
        this.site = site;
        this.password = password;
    }
    public void addPassword(){




    }
}
