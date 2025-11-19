/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.utils;

/**
 *
 * @author bill
 */
public class Constants {

    public enum GenreEnum {
        MALE("M"),
        FEMALE("F");

        private final String code;

        GenreEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum RoleEnum {
        ADMINISTRADOR("ADMINISTRADOR"),
        CLIENTE("CLIENTE");

        private final String name;

        RoleEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
