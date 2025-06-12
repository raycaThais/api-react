package org.serratec.h2.grupo2.enuns;


public enum Cargo {

    // Alta liderança
    PRESIDENTE,
    DIRETOR_EXECUTIVO,
    DIRETOR_FINANCEIRO,
    DIRETOR_COMERCIAL,
    SOCIO,
    ENGENHEIRO_DE_SOFTWARE, 
    DESENVOLVEDOR_FRONTEND, 
    DESENVOLVEDOR_BACKEND, 
    ENGENHEIRO_DE_DADOS,

    // Gerência
    GERENTE_ADMINISTRATIVO,
    GERENTE_FINANCEIRO,
    GERENTE_DE_PROJETOS,
    COORDENADOR_DE_TI,
    SUPERVISOR_DE_VENDAS,

    // Analistas / Consultores
    ANALISTA_DE_SISTEMAS,
    ANALISTA_FINANCEIRO,
    ANALISTA_DE_MARKETING,
    ANALISTA_DE_RECURSOS_HUMANOS,
    CONSULTOR,
    TECNICO_DE_SUPORTE,

    // Assistentes / Auxiliares / Operacionais
    ASSISTENTE_ADMINISTRATIVO,
    ASSISTENTE_FINANCEIRO,
    AUXILIAR_ADMINISTRATIVO,
    ESTAGIARIO,
    RECEPCIONISTA,
    RECURSOS_HUMANOS; 

    public static NivelAcesso getNivelAcesso(Cargo cargo) {
        switch (cargo) {
            // ACESSO TOTAL
            case PRESIDENTE:
            case DIRETOR_EXECUTIVO:
            case SOCIO:
            case DESENVOLVEDOR_BACKEND:
            case DESENVOLVEDOR_FRONTEND:
            case ENGENHEIRO_DE_SOFTWARE:
            case ENGENHEIRO_DE_DADOS:
                return NivelAcesso.TOTAL;

            // ACESSO ALTO
            case DIRETOR_FINANCEIRO:
            case DIRETOR_COMERCIAL:
            case GERENTE_ADMINISTRATIVO:
            case GERENTE_FINANCEIRO:
            case GERENTE_DE_PROJETOS:
            case COORDENADOR_DE_TI:
            case SUPERVISOR_DE_VENDAS:
                return NivelAcesso.ALTO;

            // ACESSO MÉDIO
            case ANALISTA_DE_SISTEMAS:
            case ANALISTA_FINANCEIRO:
            case ANALISTA_DE_MARKETING:
            case ANALISTA_DE_RECURSOS_HUMANOS:
            case CONSULTOR:
            case TECNICO_DE_SUPORTE:
                return NivelAcesso.MEDIO;

            // ACESSO BAIXO
            case ASSISTENTE_ADMINISTRATIVO:
            case ASSISTENTE_FINANCEIRO:
            case AUXILIAR_ADMINISTRATIVO:
            case ESTAGIARIO:
            case RECEPCIONISTA:
            case RECURSOS_HUMANOS:
            default:
                return NivelAcesso.BAIXO;
        }
    };
}
