package com.eagora.echosoft.eagora;

/**
 * Created by rosangela on 07/10/17.
 */

public class OpcoesDoUsuario {
        private String opcao;

        public OpcoesDoUsuario() {
        }

        public OpcoesDoUsuario(String opcao) {
            this.opcao = opcao;
        }

        public String getTitle() {
            return opcao;
        }

        public void setTitle(String name) {
            this.opcao = name;
        }


}
