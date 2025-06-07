# Otimização de Frota de Locadora com Programação Linear

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Este repositório contém a implementação em Java de um modelo de Programação Linear para otimizar a composição da frota de uma locadora de veículos. O objetivo é maximizar o lucro líquido anual, determinando a quantidade ideal de carros a serem adquiridos em diferentes categorias, sujeito a restrições de orçamento, demanda de mercado e capacidade física.

Este projeto foi desenvolvido como trabalho acadêmico para a disciplina de **Otimização de Sistemas** da **Universidade Estadual do Tocantins (UNITINS)**.

## 📜 Tabela de Conteúdos
* [Sobre o Projeto](#-sobre-o-projeto)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Modelo de Programação Linear](#-modelo-de-programação-linear)
* [Começando](#-começando)
* [Resultados](#-resultados)
* [Autores](#️-autores)

## 📜 Sobre o Projeto

[cite_start]O problema consiste em decidir quantos veículos de cada categoria (Econômico, Executivo, SUV e Van) uma locadora deve adquirir para maximizar seu retorno sobre o investimento. A decisão é baseada em diversos fatores, incluindo:
* [cite_start]Preço de aquisição de cada veículo.
* [cite_start]Custo anual de manutenção, impostos e seguros.
* [cite_start]Receita média anual gerada por cada categoria.
* [cite_start]Demanda máxima estimada pelo mercado.
* [cite_start]Limitações de orçamento para aquisição e de espaço físico para estacionamento.

[cite_start]Para resolver este problema complexo de alocação de recursos, foi utilizado um modelo de **Programação Linear** [cite: 6][cite_start], resolvido através do **Algoritmo Simplex**. A implementação foi feita em Java, utilizando a biblioteca `Apache Commons Math` para a resolução do sistema.

## 🛠️ Tecnologias Utilizadas

* [cite_start]**Java (JDK 11+)**: Linguagem de programação principal.
* **Apache Commons Math**: Biblioteca utilizada para a implementação do solver Simplex.
* **Maven**: Ferramenta de automação e gerenciamento de dependências.

## 🎲 Modelo de Programação Linear

O modelo matemático implementado busca otimizar a seguinte estrutura:

#### Variáveis de Decisão
* [cite_start]$x_1$: Quantidade de veículos da categoria **Econômico**.
* [cite_start]$x_2$: Quantidade de veículos da categoria **Executivo**.
* [cite_start]$x_3$: Quantidade de veículos da categoria **SUV**.
* [cite_start]$x_4$: Quantidade de veículos da categoria **Van**.

#### Função-Objetivo
[cite_start]Maximizar o Lucro Líquido Anual (Z):
$$
\text{Maximizar } Z = \sum_{i=1}^{4} (R_i - C_i^{\text{manut}} - \frac{P_i}{H}) \cdot x_i
$$
Onde:
- [cite_start]$R_i$: Receita média anual esperada para um veículo da categoria $i$.
- [cite_start]$C_i^{\text{manut}}$: Custo médio anual de manutenção, incluindo seguros, impostos e reparos para a categoria $i$.
- [cite_start]$P_i$: Preço de compra (valor de aquisição) do veículo da categoria $i$.
- [cite_start]$H$: Horizonte de amortização (em anos), período no qual o custo de aquisição do veículo é distribuído.

#### Restrições
O modelo está sujeito às seguintes restrições, que definem a região de soluções viáveis:

1.  [cite_start]**Restrição de Orçamento:** O valor total gasto na aquisição de novos veículos não pode exceder o orçamento máximo disponível (B).
    $$
    \sum_{i=1}^{4} P_i \cdot x_i \le B
    $$

2.  [cite_start]**Restrição de Demanda:** A quantidade de veículos adquirida de cada categoria não deve ser superior à demanda máxima anual estimada ($D_i$) para evitar ociosidade.
    $$
    x_i \le D_i, \quad \text{para } i = 1, 2, 3, 4
    $$

3.  [cite_start]**Restrição de Estacionamento:** A soma do espaço físico ocupado por todos os veículos adquiridos não pode ultrapassar a capacidade total de estacionamento (S).
    $$
    \sum_{i=1}^{4} s_i \cdot x_i \le S
    $$
    Onde $s_i$ é o espaço (em vagas) que um veículo da categoria $i$ ocupa.

4.  [cite_start]**Restrição de Não-Negatividade:** A quantidade de veículos a serem comprados não pode ser negativa.
    $$
    x_i \ge 0, \quad \text{para } i = 1, 2, 3, 4
    $$

## 🚀 Começando

Para executar este projeto localmente, siga os passos abaixo.

### Pré-requisitos
* **Java Development Kit (JDK)** - Versão 11 ou superior.
* **Apache Maven** - Para gerenciar as dependências do projeto.

### Instalação e Execução
1. Clone o repositório para a sua máquina local:
   ```sh
   git clone [https://github.com/seu-usuario/otimizacao-frota-locadora.git](https://github.com/seu-usuario/otimizacao-frota-locadora.git)
   ```
2. Navegue até o diretório do projeto:
   ```sh
   cd otimizacao-frota-locadora
   ```
3. Adicione a dependência do `Apache Commons Math` ao seu arquivo `pom.xml`:
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.apache.commons</groupId>
           <artifactId>commons-math3</artifactId>
           <version>3.6.1</version>
       </dependency>
   </dependencies>
   ```
4. Compile e execute o projeto usando o Maven:
   ```sh
   mvn compile exec:java -Dexec.mainClass="br.com.minhalocadora.lp.OtimizaFrotaCommonsMath"
   ```

## 📊 Resultados

A execução do código com os dados do problema apresentado no artigo acadêmico gera a seguinte saída, demonstrando a solução ótima e a análise de viabilidade da solução arredondada:

```
=========================================================
   RELATÓRIO DE OTIMIZAÇÃO DE FROTA - RESULTADO FINAL   
=========================================================

O lucro líquido anual máximo estimado é de R$ 60.000,00.

--- DETALHAMENTO DA SOLUÇÃO ÓTIMA (CONTÍNUA) ---
Para alcançar o lucro máximo, a quantidade de veículos a adquirir é:
  - Econômico   : 4.8000 unidades
  - Executivo   : 6.0000 unidades
  - SUV         : 0.0000 unidades
  - Van         : 0.0000 unidades

--- RECOMENDAÇÃO PRÁTICA (SOLUÇÃO ARREDONDADA) ---
Como não é possível comprar frações de carros, arredondamos os valores:
  - Comprar 5 carros da categoria Econômico.
  - Comprar 6 carros da categoria Executivo.
  - Comprar 0 carros da categoria SUV.
  - Comprar 0 carros da categoria Van.

O lucro anual com a frota arredondada seria de aproximadamente R$ 61.000,00.

--- ANÁLISE DE USO DOS RECURSOS (Solução Arredondada) ---
1. Orçamento:
  - Custo Total: R$ 785.000,00
  - Orçamento Disponível: R$ 1.200.000,00
  - Utilização do Orçamento: 65.42%
  - Status: VÁLIDO (dentro do orçamento)

2. Estacionamento:
  - Vagas Utilizadas: 12.20 vagas
  - Vagas Disponíveis: 12.00 vagas
  - Ocupação do Estacionamento: 101.67%
  - Status: INVÁLIDO (excede a capacidade!)
```

## ✍️ Autores

Este trabalho foi desenvolvido por:
* **Gabriel Mussatto** 
* **Kethelen Parra** 
* **Erick Meneses** 
