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
- [Propósito do Projeto](#propósito-do-projeto)
- [Funcionalidades Principais](#funcionalidades-principais)
- [Arquitetura Orientada a Objetos](#arquitetura-orientada-a-objetos)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Funcionamento do Sistema](#funcionamento-do-sistema)
- [Como Compilar e Executar](#como-compilar-e-executar)

## Autores

**Professor:**  
[Bruno Borges da Silva](https://github.com/silv4bufersa)

**Discentes:**
- [Alex Bruno Duarte](https://github.com/alexb7z)
- [Harley Lucas de Souza Batista](https://github.com/harleylsb)
- [Izadora Louyza Silva Figueiredo](https://github.com/izalouyza)
- [Leonardo Augusto Silva de Souza](https://github.com/LeonardAugusto)
- [Lívian Maria Lucena Gomes Pinheiro](https://github.com/livianlucena)
- [Marcelo Caat Amaral do Nascimento](https://github.com/MarceloCaat)
- [Maria Vitória Fernandes Rocha](https://github.com/tivitoriarocha)


## Propósito do Projeto

O projeto tem como finalidade simular um ambiente hospitalar básico, permitindo:

- Gerenciar usuários (médicos, pacientes).
- Realizar login com diferentes níveis de acesso.
- Agendar, visualizar e gerenciar consultas.
- Aplicar conceitos fundamentais de Programação Orientada a Objetos.

O foco principal é demonstrar a **arquitetura POO limpa, modular e extensível**, estruturada em pacotes bem definidos.

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

- Consulta
- Funcionario
- Medico
- Paciente
- Pessoa

### Interfaces

- Acessavel  
- Agendavel  
- Gerenciavel  
- Identificavel  
- Validavel  

### Gerenciadores

- GerenciadorConsulta
- GerenciadorMedico
- GerenciadorPaciente

### Exceções Personalizadas

- ConsultaException
- FuncionarioException
- GerenciadorConsultaException=
- HospitalException
- LoginException
- MedicoException
- PacienteException
- PessoaException
- UtilitariosException

### Utilitários

- Validações e funções auxiliares em `Utilitarios.java`

### Sistema Principal

- Hospital  
- Login  
- Main (ponto de entrada)  

---

## Estrutura de Pastas

```bash
GerenciamentoHospitalar/
├── src/
│   └── br/
│       └── com/
│           └── hospital/
│               ├── entidades/
│               │   ├── Consulta.java
│               │   ├── Funcionario.java
│               │   ├── Medico.java
│               │   ├── Paciente.java
│               │   └── Pessoa.java
│               ├── exceptions/
│               │   ├── ConsultaException.java
│               │   ├── FuncionarioException.java
│               │   ├── GerenciadorConsultaException.java
│               │   ├── HospitalException.java
│               │   ├── LoginException.java
│               │   ├── MedicoException.java
│               │   ├── PacienteException.java
│               │   ├── PessoaException.java
│               │   └── UtilitariosException.java
│               ├── gerenciadores/
│               │   ├── GerenciadorConsulta.java
│               │   ├── GerenciadorMedico.java
│               │   └── GerenciadorPaciente.java
│               ├── interfaces/
│               │   ├── Acessavel.java
│               │   ├── Agendavel.java
│               │   ├── Gerenciavel.java
│               │   ├── Identificavel.java
│               │   └── Validavel.java
│               ├── sistema/
│               │   ├── Hospital.java
│               │   └── Login.java
│               ├── utilitarios/
│               │   └── Utilitarios.java
│               └── Main.java
└── README.md
```

## Funcionamento do Sistema

O acesso ocorre via terminal:

```bash
====== SISTEMA HOSPITALAR ======
1 - Fazer login
0 - Sair
Opção:
```

Após o login, médicos e pacientes visualizam menus diferentes, alinhados aos seus níveis de permissão.

---

## Como Compilar e Executar

### 1. Certifique-se de ter o Java instalado  
Recomenda-se Java 17 ou superior.

### 2. Clone o repositório

```bash
git clone https://github.com/izalouyza/GerenciamentoHospitalar
```

### 3. Acesse a pasta do projeto
```bash
cd GerenciamentoHospitalar/src/br/com/hospital
```

### 4. Compile o programa
```bash
javac Main.java
```

### 5. Execute o programa
```bash
java Main
```
