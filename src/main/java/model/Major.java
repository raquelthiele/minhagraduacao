package model;

/**
 * Classe do modelo que representa a graduação do BSI contendo as listas com os
 * códigos dos cursos obrigatórios e optativos.
 */
class Major {

    /**
     * Lista com os códigos de todos os cursos obrigatórios do BSI.
     */
    public static final String[] MANDATORY_COURSES = {
            "TIN0054", // ATIVIDADES CURRICULARES DE EXTENSÃO 1
            "TIN0055", // ATIVIDADES CURRICULARES DE EXTENSÃO 2
            "TIN0056", // ATIVIDADES CURRICULARES DE EXTENSÃO 3
            "TIN0057", // ATIVIDADES CURRICULARES DE EXTENSÃO 4
            "TIN0117", // ADMINISTRAÇÃO FINANCEIRA
            "TME0015", // ÁLGEBRA LINEAR
            "TIN0118", // ANÁLISE DE ALGORITMO
            "TIN0115", // ANÁLISE DE SISTEMAS
            "TIN0013", // ANÁLISE EMPRESARIAL E ADMIN
            "TIN0120", // BANCO DE DADOS I
            "TIN0169", // BANCO DE DADOS II
            "TME0112", // CÁLCULO DIFERENCIAL E INTEGRAL I
            "TME0113", // CÁLCULO DIFERENCIAL E INTEGRAL II
            "TIN0106", // DESENVOLVIMENTO DE PÁGINAS WEB
            "TIN0130", // EMPREENDEDORISMO
            "TME0115", // ESTATÍSTICA
            "TIN0114", // ESTRUTURAS DE DADOS I
            "TIN0168", // ESTRUTURAS DE DADOS II
            "TIN0109", // ESTRUTURAS DISCRETAS
            "TIN0112", // FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO
            "TIN0132", // GERÊNCIA DE PROJ. DE INFORMAT
            "TIN0010", // INTERAÇÃO HUMANO-COMPUTADOR
            "TIN0105", // INTRODUÇÃO À LÓGICA COMPUTAC
            "TIN0119", // LINGUAG. FORMAIS E AUTÔMATOS
            "TME0101", // MATEMÁTICA BÁSICA
            "TIN0108", // ORGANIZAÇÃO DE COMPUTADORES
            "TME0114", // PROBABILIDADE
            "TIN0122", // PROCESSOS DE SOFTWARE
            "TIN0121", // PROGRAMAÇÃO MODULAR
            "TIN0131", // PROJETO DE GRADUAÇÃO I
            "TIN0133", // PROJETO DE GRADUAÇÃO II
            "TIN0171", // PROJETO E CONSTRUÇÃO DE SISTEMAS
            "TIN0125", // PROJETO E CONSTRUÇÃO DE SISTEMAS COM SGBD
            "TIN0123", // REDES DE COMPUTADORES I
            "TIN0126", // REDES DE COMPUTADORES II
            "TIN0116", // SISTEMAS OPERACIONAIS
            "TIN0107", // TÉCNICAS DE PROGRAMAÇÃO I
            "TIN0011", // TÉCNICAS DE PROGRAMAÇÃO II
            "HTD0058" // TEORIAS E PRÁTICAS DISCURSIVAS
    };

    /**
     * Lista com os códigos de todos os cursos optativos do BSI.
     */
    public static final String[] OPTINAL_COURSES = {
            "TIN0135", // ADMINISTRAÇÃO DE BANCO DE DADOS
            "TIN0144", // ALGORITMOS PARA PROBLEMAS COMBINATÓRIOS
            "TIN0150", // AMBIENTE OPERACIONAL UNIX
            "TIN0146", // COMPILADORES
            "TIN0149", // COMPUTAÇÃO GRÁFICA
            "TIN0138", // COMUNICAÇÃO E SEGURANÇA DE DADOS
            "TIN0158", // DESENVOLVIMENTO DE SERVIDOR WEB
            "TIN0143", // FLUXOS EM REDES
            "TIN0147", // FUNDAMENTOS DE REPRESENTAÇÃO DE CONHECIMENTO E RACIOCÍNIO
            "TIN0136", // GERÊNCIA DE DADOS EM AMBIENTES DISTRIBUÍDOS E PARALELOS
            "TIN0160", // GESTÃO DE PROCESSOS DE NEGÓCIOS
            "TIN0128", // INFORMÁTICA NA EDUCAÇÃO
            "TIN0172", // INTELIGÊNCIA ARTIFICIAL
            "TIN0142", // PROGRAMAÇÃO LINEAR
            "TIN0159", // SISTEMAS COLABORATIVOS
            "TIN0148", // SISTEMAS MULTIMÍDIA
            "TIN0145", // TÓPICOS AVANÇADOS EM ALGORITMOS I
            "TIN0137", // TÓPICOS AVANÇADOS EM BANCO DE DADOS I
            "TIN0162", // TÓPICOS AVANÇADOS EM BANCO DE DADOS II
            "TIN0163", // TÓPICOS AVANÇADOS EM BANCO DE DADOS III
            "TIN0161", // TÓPICOS AVANÇADOS EM ENGENHARIA DE SOFTWARE I
            "TIN0166", // TÓPICOS AVANÇADOS EM ENGENHARIA DE SOFTWARE II
            "TIN0141", // TÓPICOS AVANÇADOS EM REDES DE COMPUTADORES I
            "TIN0164", // TÓPICOS AVANÇADOS EM REDES DE COMPUTADORES II
            "TIN0165" // TÓPICOS AVANÇADOS EM REDES DE COMPUTADORES III
    };
}
