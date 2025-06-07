# Otimização de Frota de Locadora com Programação Linear

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Este repositório contém a implementação em Java de um modelo de Programação Linear para otimizar a composição da frota de uma locadora de veículos. O objetivo é maximizar o lucro líquido anual, determinando a quantidade ideal de carros a serem adquiridos em diferentes categorias, sujeito a restrições de orçamento, demanda de mercado e capacidade física.

Este projeto foi desenvolvido como trabalho acadêmico para a disciplina de **Otimização de Sistemas** da **Universidade Estadual do Tocantins (UNITINS)**.

## 📜 Sobre o Projeto

O problema consiste em decidir quantos veículos de cada categoria (Econômico, Executivo, SUV e Van) uma locadora deve adquirir para maximizar seu retorno sobre o investimento. A decisão é baseada em diversos fatores, incluindo:
* Preço de aquisição de cada veículo.
* Custo anual de manutenção, impostos e seguros.
* Receita média anual gerada por cada categoria.
* Demanda máxima estimada pelo mercado.
* Limitações de orçamento para aquisição e de espaço físico para estacionamento.

Para resolver este problema complexo de alocação de recursos, foi utilizado um modelo de **Programação Linear**, resolvido através do **Algoritmo Simplex**. A implementação foi feita em Java, utilizando a biblioteca `Apache Commons Math` para a resolução do sistema.

## 🛠️ Tecnologias Utilizadas

* **Java (JDK 11+)**: Linguagem de programação principal.
* **Apache Commons Math**: Biblioteca utilizada para a implementação do solver Simplex.
* **Maven**: Ferramenta de automação e gerenciamento de dependências.

## 🎲 Modelo de Programação Linear

O modelo matemático implementado busca otimizar a seguinte estrutura:

#### Variáveis de Decisão
* <span class="math-inline">x\_1</span>: Quantidade de veículos da categoria **Econômico**.
* <span class="math-inline">x\_2</span>: Quantidade de veículos da categoria **Executivo**.
* <span class="math-inline">x\_3</span>: Quantidade de veículos da categoria **SUV**.
* <span class="math-inline">x\_4</span>: Quantidade de veículos da categoria **Van**.

#### Função-Objetivo
Maximizar o Lucro Líquido Anual (Z):
<span class="math-block">\\text\{Maximizar \} Z \= \\sum\_\{i\=1\}^\{4\} \(R\_i \- C\_i^\{\\text\{manut\}\} \- \\frac\{P\_i\}\{H\}\) \\cdot x\_i</span>
Onde:
- <span class="math-inline">R\_i</span>:
