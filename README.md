<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=6A5ACD&height=200&section=header&text=Gerenciamento%20Hospitalar&fontSize=40&fontColor=ffffff" />
</p>

Repositório contendo o projeto desenvolvido durante a 3ª unidade da disciplina **Programação Orientada a Objetos (PEX0130)**.

O sistema tem como objetivo aplicar, na prática, os principais princípios de POO, como **herança**, **encapsulamento**, **polimorfismo**, **interfaces**, **exceções personalizadas** e **organização modular**, por meio da implementação de um **sistema simples de gerenciamento hospitalar**, capaz de controlar usuários, médicos, pacientes, consultas e acessos.

![Linguagem](https://img.shields.io/badge/Linguagem-Java-red)
![Último commit](https://img.shields.io/github/last-commit/izalouyza/GerenciamentoHospitalar)

---

## Sumário
- [Autores](#autores)
- [Documentação](#documentação)
- [Propósito do Projeto](#propósito-do-projeto)
- [Funcionalidades Principais](#funcionalidades-principais)
- [Arquitetura Orientada a Objetos](#arquitetura-orientada-a-objetos)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Como Compilar e Executar](#como-compilar-e-executar)

## Autores

**Professor:** [Bruno Borges da Silva](https://github.com/silv4bufersa)

<b>Discentes:</b><br>
<a href="https://github.com/alexb7z">Alex Bruno Duarte</a> - Matrícula: 2025022557<br>
<a href="https://github.com/harleylsb">Harley Lucas de Souza Batista</a> - Matrícula: 2025022526<br>
<a href="https://github.com/izalouyza">Izadora Louyza Silva Figueiredo</a> - Matrícula: 2024010176<br>
<a href="https://github.com/LeonardAugusto">Leonardo Augusto Silva de Souza</a> - Matrícula: 2023011257<br>
<a href="https://github.com/livianlucena">Lívian Maria Lucena Gomes Pinheiro</a> - Matrícula: 2024010084<br>
<a href="https://github.com/MarceloCaat">Marcelo Caat Amaral do Nascimento</a> - Matrícula: 2025011536<br>
<a href="https://github.com/tivitoriarocha">Maria Vitória Fernandes Rocha</a> - Matrícula: 2024010257

---

## Propósito do Projeto

O projeto tem como finalidade simular um ambiente hospitalar básico, permitindo:

- Gerenciar usuários (médicos e pacientes).
- Realizar login com diferentes níveis de acesso.
- Agendar, visualizar e administrar consultas.
- Aplicar conceitos fundamentais de Programação Orientada a Objetos.

O foco principal é demonstrar uma **arquitetura POO limpa, modular e extensível**, organizada em pacotes bem definidos.

---

## Documentação

Para detalhes aprofundados sobre a arquitetura, classes e descrição técnica das entidades, acesse o documento oficial na pasta `doc/`:

📄 **[Acessar Documentação do Projeto (PDF)](doc/Documentacao_Projeto.pdf)**

---


## Funcionalidades Principais

| Funcionalidade | Descrição |
|---------------|-----------|
| Cadastro de Pacientes | Registro de pacientes com validações básicas. |
| Cadastro de Médicos | Registro de médicos com CRM válido e especialização. |
| Login | Diferentes níveis de acesso para médicos e pacientes. |
| Agendamento de Consultas | Médicos podem criar e gerenciar consultas. |
| Visualização de Consultas | Pacientes visualizam suas próprias consultas. |
| Tratamento de Exceções | Exceções personalizadas garantem integridade do sistema. |

---

## Arquitetura Orientada a Objetos

### Entidades
- Pessoa (Classe Abstrata)
- Paciente
- Medico
- Funcionario
- Consulta

### Interfaces
- Agendavel
- Gerenciavel
- Validavel

### Enums
- NivelAcesso

### Gerenciadores
- GerenciadorConsulta
- GerenciadorMedico
- GerenciadorPaciente

### Sistema Principal
- Hospital
- Login
- UsuarioSistema
- Main

---

## Estrutura de Pastas

```bash
GerenciamentoHospitalar/
├── doc/
│   └── Documentacao_Projeto.pdf
├── src/
│   └── br/com/hospital/
│       ├── entidades/
│       ├── enums/         
│       ├── exceptions/
│       ├── gerenciadores/
│       ├── interfaces/
│       ├── sistema/
│       ├── utilitarios/
│       └── Main.java
└── README.md

```

## Como Compilar e Executar

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Git instalado.

### 1. Clonar o Repositório

Abra o terminal e digite:
```bash
git clone https://github.com/izalouyza/GerenciamentoHospitalar
```

### 2. Executar o Projeto
Navegue até a pasta `src` do projeto:
```
cd GerenciamentoHospitalar/src
```

Compile o código:

```
javac br/com/hospital/Main.java
```

Execute o sistema:

```
java br.com.hospital.Main
```

## 🔑 Dados para Teste (Login)
O sistema já inicia com dados carregados para facilitar a correção:

- **Admin:** user: `admin` | senha: `admin`
- **Secretaria:** user: `secretaria` | senha: `1234`
- **Médico:** user: `drcaval` | senha: `1234`
