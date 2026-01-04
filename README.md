# BPMN Compiler

Un moteur de compilation extensible capable de transformer des modèles de processus **BPMN 2.0 (XML)** en code source exécutable (Java), conçu selon les principes de l'**Architecture Hexagonale**.

## 🏗️ Architecture du Projet

Ce projet refuse l'approche monolithique classique (MVC couplé) au profit d'une séparation stricte des responsabilités, garantissant maintenabilité et extensibilité.

### Schéma des Couches
Le projet est organisé en trois cercles concentriques :

1.  **Core (`com.bpmncompiler.core`)** : Le Cœur Métier.
    *   Contient le modèle pur (AST : Abstract Syntax Tree).
    *   Définit les interfaces (Portes d'entrée/sortie).
    *   **Ne dépend d'aucun framework technique** (ni Jackson, ni Web).
2.  **Infrastructure (`com.bpmncompiler.infra`)** : Les Adaptateurs.
    *   **Parser** : Traduit le XML externe en AST interne via des DTOs.
    *   **Generator** : Implémente la génération de code concrète (Java).
3.  **API (`com.bpmncompiler.api`)** : L'Exposition.
    *   Un Controller REST simple qui expose le service via HTTP.

## 🧠 Design Patterns Utilisés

L'architecture repose sur des patterns classiques du "Gang of Four" pour résoudre des problèmes complexes :

### 1. Pattern Visitor (Séparation Structure/Opération)
*   **Problème** : Comment générer du code Java sans polluer les classes métier (`Task`, `StartEvent`) avec des `System.out.println` ?
*   **Solution** : Le métier définit une méthode `accept(Visitor)`. La logique de génération est externalisée dans une classe `JavaBpmnVisitor`.

### 2. Pattern Strategy (Principe Open/Closed)
*   **Problème** : Comment supporter de nouveaux langages (Python, C++) sans modifier le code existant ?
*   **Solution** : Une interface `CodeGeneratorStrategy` définit le contrat. Le système est **ouvert à l'extension** (nouvelles classes stratégies) mais **fermé à la modification**.

### 3. Pattern Factory (Injection Dynamique)
*   **Rôle** : La `GeneratorFactory` sélectionne automatiquement la bonne stratégie au runtime en fonction du paramètre demandé par le client.

### 4. Pattern Template Method (Pipeline)
*   **Rôle** : Le `CompilerService` définit le squelette immuable de la compilation : `Parse` -> `Validate` -> `Generate`.

## 🚀 Comment Lancer le Projet

### Pré-requis
*   Java 17+
*   Maven (inclus via le wrapper `mvnw`)

### Démarrer le Serveur
```bash
./mvnw spring-boot:run
```
L'API sera accessible sur `http://localhost:8080/api/compiler`.

### Tester la Compilation
Utilisez `curl` pour envoyer un fichier BPMN :

```bash
curl -X POST -F "file=@test_process.bpmn" http://localhost:8080/api/compiler
```

**Sortie attendue :**
```java
public class GeneratedProcess {
    public static void main(String[] args) {
        System.out.println("Process Started: Order Received");
        // ...
        System.out.println("Process Ended: Order Shipped");
    }
}
```

## 🛠️ Stack Technique
*   **Langage** : Java 17
*   **Framework** : Spring Boot 3.3.0
*   **Parsing XML** : Jackson Dataformat XML
*   **Build** : Maven
